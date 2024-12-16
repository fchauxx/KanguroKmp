//
//  LocalChatbotModule.swift
//  Kanguro
//
//  ** DISCLAIMER **
//
//  This module was created because it was not possible to make any further changes on the backend side for the
//  chatbot feature without a significant refactoring. Despite the high demand for these changes, it was decided
//  (after extensive discussion) to move the chatbot to the mobile platform. However, due to time constraints,
//  the implementation on mobile was NOT optimal as well.
//
//  This class functions similarly to the backend. In the ChatbotViewModel, we essentially replace their service
//  with our own local service. If there is a client requirement that necessitates significant changes, it is
//  highly recommended to consider a major refactoring or even starting from scratch.
//

import UIKit
import Combine
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroStorageDomain
import KanguroNetworkDomain
import KanguroPetDomain
import KanguroPetData

public final class LocalChatbotModule  {
    
    // MARK: - Dependencies
    let keychain: SecureStorage
    let getPetsService: GetPetsUseCaseProtocol
    let getPoliciesService: GetPoliciesUseCaseProtocol
    let getCoverages: GetCoveragesUseCaseProtocol
    let getRemindersService: GetRemindersUseCaseProtocol
    var createClaimService: CreatePetClaimUseCaseProtocol
    var createDocumentsService: CreatePetDocumentsUseCaseProtocol
    
    // MARK: - Stored Properties
    weak var delegate: ChatbotDelegate?
    var chatInteractionData: ChatInteractionData?
    var mainDispatcher: Dispatcher
    var type: SessionType
    var petInfo: ChatPetInfo?
    var pets: [Pet] = []
    var chosenPetPolicy: Policy? = nil
    var hasMedicalReminders: Bool? = false
    var hasCoveragesAvailable: Bool? = false
    var answer: [ChatInteractionId : String] = [:]
    var pledgeOfHonorId: [Int]?
    var documentIds: [Int] = []
    var reimbursementProcess: RemoteReimbursementProcessType? = .UserReimbursement
    var newClaim: NewPetClaimParameters?
    
    // MARK: - Computed Properties
    var starterStepId: ChatInteractionId {
        switch type {
        case .NewClaim:
            return .SelectPet
        default:
            return .SelectPet
        }
    }
    var isPolicyOnWaitingPeriod: Bool {
        guard let waitingPeriod = chosenPetPolicy?.waitingPeriodRemainingDays else { return false }
        return waitingPeriod > 0
    }
    var isPolicyNotActive: Bool {
        guard let policyStatus = chosenPetPolicy?.status else { return false }
        return policyStatus != .ACTIVE
    }
    var isPolicyStillWithCoveragesAvailable: Bool {
        return hasCoveragesAvailable ?? false
    }
    var isPolicyWithMedicalReminder: Bool {
        return hasMedicalReminders ?? false
    }
    var isPolicyWithNoAnnualLimit: Bool {
        guard let annualLimit = chosenPetPolicy?.sumInsured?.remainingValue  else { return true }
        return annualLimit == 0
    }
    var isPetLessThanSixMonthsOld: Bool {
        guard let petBirthDate = pets.first(where: { $0.id == chosenPetPolicy?.petId })?.birthDate else { return false }
        return petBirthDate.monthsBetweenDate(Date()) <= 6
    }
    var claimTypeConvertedToEnglishIfNeeded: String? {
        if answer[.SelectClaimType] == "Accidente" {
            return "Accident"
        } else if answer[.SelectClaimType] == "Enfermedad" {
            return "Illness"
        } else if answer[.SelectClaimType] == "Otro" {
            return "Other"
        } else {
            return answer[.SelectClaimType]
        }
    }
    var genericErrorMessage: ChatInteractionStep {
        return ChatInteractionStep(type: .ButtonList,
                                   orientation: nil,
                                   actions: [
                                    ChatAction(order: 1,
                                               label: "chatbot.action.finish.label".localized,
                                               value: "StopClaim",
                                               action: .StopClaim,
                                               isMainAction: false,
                                               userResponseMessage: "chatbot.action.finish.label".localized)
                                   ],
                                   messages: [
                                    ChatMessage(
                                        format: .Text,
                                        content: "chatbot.newClaim.genericError.message".localized,
                                        order: 0,
                                        sender: nil
                                    ),
                                    ChatMessage(
                                        format: .Text,
                                        content: "chatbot.newClaim.denialContactUs.message".localized,
                                        order: 1,
                                        sender: nil
                                    )
                                   ],
                                   sessionId: "")
    }
    
    // MARK: - Initializers
    init(
        delegate: ChatbotDelegate,
        mainDispatcher: Dispatcher,
        type: SessionType,
        keychain: SecureStorage,
        getPetsService: GetPetsUseCaseProtocol,
        getPoliciesService: GetPoliciesUseCaseProtocol,
        getCoverages: GetCoveragesUseCaseProtocol,
        getRemindersService: GetRemindersUseCaseProtocol,
        createClaimService: CreatePetClaimUseCaseProtocol,
        createDocumentsService: CreatePetDocumentsUseCaseProtocol
    ) {
        self.delegate = delegate
        self.mainDispatcher = mainDispatcher
        self.type = type
        self.keychain = keychain
        self.getPetsService = getPetsService
        self.getPoliciesService = getPoliciesService
        self.getCoverages = getCoverages
        self.getRemindersService = getRemindersService
        self.createClaimService = createClaimService
        self.createDocumentsService = createDocumentsService
    }
}

extension LocalChatbotModule: ChatbotModuleProtocol {
    
    func getSession(parameters: GetSessionParameters,
                    completion: @escaping ((RequestResponse<[ChatSessionResponse], NetworkRequestError>) -> Void)) {
        // WAS NOT NEEDED TO IMPLEMENT FOR LOCAL CHATBOT.
    }
    
    func postStartSession(parameters: SessionStartParameters,
                          completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        
        switch parameters.type {
        case .NewClaim:
            getPetsService.execute { [weak self] response in
                guard let self else { return }
                switch response {
                case .failure(let error):
                    completion(.failure(NetworkRequestError(statusCode: 500, 
                                                            error: error.errorMessage)))
                case .success(let pets):
                    cleanAllStates()
                    self.pets = pets
                    self.chatInteractionData = ChatInteractionData(pets: pets, 
                                                                   currentId: starterStepId)
                    guard let nextStep = self.chatInteractionData?.getInitialMessages(chatbotType: .NewClaim) else {
                        completion(.failure(NetworkRequestError(statusCode: 404, 
                                                                error: "not found")))
                        return
                    }
                    completion(.success(nextStep.chatInteractionStep))
                }
            }
        default: break
        }
    }
    
    func postNextStep(parameters: NextStepParameters,
                      completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        
        guard let chatInteractionData else {
            completion(.failure(NetworkRequestError(statusCode: 404, 
                                                    error: "not found")))
            return
        }

        // Save the user answer and handle the answer of the current step
        // to procceed to the next step of the Chatbot.
        saveUserAnswer(parameters)

        switch chatInteractionData.currentId {
        case .SelectPet:
            handlePetSelection(queue: MainDispatcher(), parameters) { response in
                switch response {
                case .customError(let error), .failure(let error):
                    completion(.failure(error))
                case .success(let nextStep):
                    completion(.success(nextStep))
                }
            }
        case .PledgeOfHonour:
            handlePledgeOfHonor(parameters: parameters) { response in
                switch response {
                case .customError(let error), .failure(let error):
                    completion(.failure(error))
                case .success(let nextStep):
                    completion(.success(nextStep))
                }
            }
        case .SelectClaimType:
            handleClaimTypeSelection(parameters: parameters) { response in
                switch response {
                case .customError(let error), .failure(let error):
                    completion(.failure(error))
                case .success(let nextStep):
                    completion(.success(nextStep))
                }
            }
        case .AttachDocuments, .PetHealthStatePicture:
            handleAttachments(parameters: parameters) { response in
                switch response {
                case .customError(let error), .failure(let error):
                    completion(.failure(error))
                case .success(let nextStep):
                    completion(.success(nextStep))
                }
            }
        case .Summary:
            handleNewClaim { response in
                switch response {
                case .customError(let error), .failure(let error):
                    completion(.failure(error))
                case .success(let nextStep):
                    completion(.success(nextStep))
                }
            }
        default:
            mainDispatcher.asyncAfter(target: .main, deadline: .now() + 1) { [weak self] in
                guard let self else { return }
                completion(.success(self.getNextStep()))
            }
        }
    }
    
    func getPetInfo(parameters: GetPetIdParameters,
                    completion: @escaping ((RequestResponse<ChatPetInfo, NetworkRequestError>) -> Void)) {
        guard let petInfo else {
            return completion(.failure(NetworkRequestError(error: "No pet info detected.")))
        }
        completion(.success(petInfo))
    }
    
    func getNextStep(stepId: ChatInteractionId? = nil) -> ChatInteractionStep {
        if let stepId {
            chatInteractionData?.currentId = stepId
        } else {
            chatInteractionData?.updateStep()
        }
        return chatInteractionData?.nextChatInteraction.chatInteractionStep ?? genericErrorMessage
    }
}

// MARK: - New Claim Chatbot Handlers
extension LocalChatbotModule {
    
    func handlePetSelection(queue: Dispatcher,
                            _ nextStepParameters: NextStepParameters,
                            completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)
    ) {
        guard let chatInteractionData,
              let chosenPet = chatInteractionData.pets.first(where: {
                  $0.id == Int(nextStepParameters.value ?? "0")
              }) else {
            completion(.failure(NetworkRequestError(statusCode: 404, error: "not found")))
            return
        }
        chatInteractionData.chosenPet = chosenPet
        answer[.SelectPet] = chosenPet.name
        
        let group = DispatchGroup()

        // Here we retrieve the pet policy selected. And check if also have reminders.
        queue.async(target: .global, group: group) { [weak self] in
            guard let self else { return }
            var isSuccess = true
            var requestError: NetworkRequestError?
            
            group.enter()
            self.getPetPolicies(nextStepParameters: nextStepParameters) { response in
                switch response {
                case .failure(let error):
                    isSuccess = false
                    requestError = NetworkRequestError(statusCode: 500, error: error.errorMessage)
                default: break
                }
                group.leave()
            }
            
            
            group.enter()
            self.getPetReminders { response in
                switch response {
                case .customError(let error), .failure(let error):
                    isSuccess = false
                    requestError = error
                default: break
                }
                group.leave()
            }
            
            group.notify(queue: .main) {
                if isSuccess {
                    if let policyBadConditionNextStep = self.checkIfPolicyIsNotActiveOrHasReminders() {
                        completion(.success(policyBadConditionNextStep))
                    }  else {
                        completion(.success(self.getNextStep()))
                    }
                } else {
                    completion(.failure(requestError!))
                }
            }
        }
    }
    
    func handlePledgeOfHonor(parameters: NextStepParameters, 
                             completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {

        guard let value = parameters.value else {
            completion(.failure(NetworkRequestError(statusCode: 404, error: "not found")))
            return
        }
        postUploadAttachments(attachments: value, fileType: .PledgeOfHonor) { [weak self] response in
            guard let self else { return }
            switch response {
            case .customError(let error), .failure(let error):
                completion(.failure(error))
            case .success(let id):
                self.pledgeOfHonorId = id
                completion(.success(self.getNextStep()))
            }
        }
    }
    
    func handleClaimTypeSelection(parameters: NextStepParameters, 
                                  completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        if parameters.action == .Accident || parameters.action == .Illness {
            mainDispatcher.asyncAfter(target: .main, 
                                      deadline: .now() + 1) { [weak self] in
                guard let self else { return }
                if let policyBadConditionNextStep = self.checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit() {
                    completion(.success(policyBadConditionNextStep))
                } else {
                    completion(.success(self.getNextStep()))
                }
            }
        } else {
            getCoverages { [weak self] response in
                guard let self else { return }
                guard let chatInteractionData = self.chatInteractionData else {
                    completion(.failure(NetworkRequestError(statusCode: 404, 
                                                            error: "not found")))
                    return
                }
                switch response {
                case .failure(let error):
                    completion(.failure(NetworkRequestError(statusCode: 500, 
                                                            error: error.errorMessage)))
                case .success(let hasCoverages):
                    if hasCoverages {
                        completion(.success(self.getNextStep(stepId: .Preventive)))
                    } else {
                        chatInteractionData.currentId = .PWNoMoreBenefits
                        completion(.success(chatInteractionData.nextChatInteraction.chatInteractionStep))
                    }
                }
            }
        }
    }
    
    func handleAttachments(parameters: NextStepParameters, 
                           completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {

        guard let chatInteractionData,
              let value = parameters.value else {
            completion(.failure(NetworkRequestError(statusCode: 404, error: "not found")))
            return
        }
        let fileType: ClaimFileInputType = chatInteractionData.currentId == .AttachDocuments ? .ReceiptDocument : .RecentPetPicture
        postUploadAttachments(attachments: value, fileType: fileType) { [weak self] response in
            guard let self else { return }
            switch response {
            case .customError(let error), .failure(let error):
                completion(.failure(error))
            case .success(let id):
                self.documentIds.append(contentsOf: id)
                completion(.success(self.getNextStep()))
            }
        }
    }
    
    func handleNewClaim(completion: @escaping ((RequestResponse<ChatInteractionStep, NetworkRequestError>) -> Void)) {
        guard let newClaim else { return completion(.success(genericErrorMessage)) }
        postClaims(newClaim: newClaim) { [weak self] response in
            guard let self else { return }
            switch response {
            case .customError(let error), .failure(let error):
                error.statusCode == 409 ? completion(.success(self.getNextStep(stepId: .DateAndAmountDuplicated))) : completion(.failure(error))
            case .success(let newClaim):
                delegate?.didReceivedClaim(claimId: newClaim.id)
                completion(.success(self.getNextStep()))
            }
        }
    }
}

// MARK: - Public Methods
extension LocalChatbotModule {
    
    func checkIfPolicyIsNotActiveOrHasReminders() -> ChatInteractionStep? {
        
        guard let chatInteractionData else { return genericErrorMessage }
        
        if isPolicyNotActive {
            chatInteractionData.currentId = .PetPolicyNotActive
            return chatInteractionData.nextChatInteraction.chatInteractionStep
        } else if isPolicyWithMedicalReminder {
            chatInteractionData.currentId = .PetPendingMedicalHistory
            return chatInteractionData.nextChatInteraction.chatInteractionStep
        } else {
            return nil
        }
    }
    
    func checkIfPolicyIsOnWaitingPeriodOrHasNoAnnualLimit() -> ChatInteractionStep? {
        
        guard let chatInteractionData else { return genericErrorMessage }
        
        if isPolicyWithNoAnnualLimit {
            chatInteractionData.currentId = .NoAnnualLimit
            return chatInteractionData.nextChatInteraction.chatInteractionStep
        } else if isPolicyOnWaitingPeriod {
            
            if isPetLessThanSixMonthsOld {
                return nil
            }
            
            chatInteractionData.currentId = .PetPolicyOnWaitingPeriod
            return chatInteractionData.nextChatInteraction.chatInteractionStep
            
        } else {
            return nil
        }
    }
    
    func saveUserAnswer(_ parameters: NextStepParameters) {
        guard let currentId = chatInteractionData?.currentId,
              let value = parameters.value else { return }
        
        switch currentId {
        case .Preventive:
            answer[.TypeDescription] = value
        case .AttachDocuments, .PetHealthStatePicture:
            let attachmentsCount = value.parseJSONAndReturnElementCount
            answer[currentId] = attachmentsCount.description
        case .SelectBankAccount:
            answer[currentId] = value
            createSummary()
            generateClaim()
        default:
            answer[currentId] = value
        }
    }
    
    func createSummary() {
        guard let chatInteractionData,
              let inputPet = answer[.SelectPet],
              let _ = answer[.PledgeOfHonour],
              let inputClaim = answer[.SelectClaimType],
              let inputDate = answer[.SelectDate],
              let inputAttachments = answer[.AttachDocuments],
              let inputPetHealthStateAttachments = answer[.PetHealthStatePicture],
              let inputAmount = answer[.TypeTotalAmount]?.convertToFloat,
              let inputPayment = answer[.SelectBankAccount] else { return }
        
        var totalAttachments = 0
        if let docAttachments = Int(inputAttachments), let petStateAttachments = Int(inputPetHealthStateAttachments) {
            totalAttachments = docAttachments + petStateAttachments
        }
        let summary = ChatSummary(pet: inputPet,
                                  claim: inputClaim,
                                  date: inputDate.date ?? Date(),
                                  attachments: totalAttachments,
                                  amount: inputAmount,
                                  paymentMethod: inputPayment, claimId: "")
        chatInteractionData.updateSummary(summary: summary)
    }
    
    func generateClaim() {
        guard let desc = answer[.TypeDescription],
              let date = answer[.SelectDate],
              let amount = answer[.TypeTotalAmount]?.convertToFloat,
              let petId = chosenPetPolicy?.petId,
              let type = claimTypeConvertedToEnglishIfNeeded,
              let pledgeId = pledgeOfHonorId?.first,
              let reimbursement = reimbursementProcess else { return }
        
        if let claimType = PetClaimType(rawValue: type),
           let date = date.date,
           let reimbursement = ReimbursementProcessType(rawValue: reimbursement.rawValue) {
            newClaim = NewPetClaimParameters(description: desc,
                                             invoiceDate: date,
                                             amount: amount,
                                             petId: petId,
                                             type: claimType,
                                             pledgeOfHonorId: pledgeId,
                                             reimbursementProcess:  reimbursement,
                                             documentIds: documentIds)
        }
    }
    
    func cleanAllStates() {
        hasMedicalReminders = false
        hasCoveragesAvailable = false
        answer.removeAll()
        documentIds.removeAll()
        newClaim = nil
        petInfo = nil
        pets = []
        chosenPetPolicy = nil
        pledgeOfHonorId = nil
        reimbursementProcess = .UserReimbursement
    }
}

// MARK: - Network
extension LocalChatbotModule {
    
    private func getPetPolicies(nextStepParameters: NextStepParameters,
                                completion: @escaping (Result<Void,RequestError>) -> Void) {
        getPoliciesService.execute { [weak self] response in
            guard let self else { return}
            switch response {
            case .failure(let error):
                completion(.failure(error))
            case .success(let policies):
                guard let chatInteractionData = self.chatInteractionData,
                      let petId = chatInteractionData.chosenPet?.id,
                      let policy = policies.first(where: {$0.petId == petId}) else {
                    completion(.failure(RequestError(errorType: .notFound, 
                                                     errorMessage: "Selected Pet policy data error")))
                    return
                }
                self.chosenPetPolicy = policy
                if let status = chosenPetPolicy?.preventive {
                    chatInteractionData.updatePreventiveStatus(status)
                }
                self.petInfo = ChatPetInfo(petId: petId, policyId: policy.id)
                completion(.success(()))
            }
        }
    }
    
    private func getPetReminders(completion: @escaping ((RequestEmptyResponse<NetworkRequestError>) -> Void)) {
        
        getRemindersService.execute { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                completion(.failure(NetworkRequestError(statusCode: 500, 
                                                        error: error.errorMessage)))
            case .success(let reminders):
                guard let chatInteractionData = self.chatInteractionData else {
                    completion(.failure(NetworkRequestError(statusCode: 404, 
                                                            error: "not found")))
                    return
                }
                self.hasMedicalReminders = reminders.contains {
                    ($0.type == .MedicalHistory) && (chatInteractionData.chosenPet?.id == $0.petId)
                }
                completion(.success)
            }
        }
    }
    
    private func getCoverages(completion: @escaping ((Result<Bool,RequestError>) -> Void)) {
        
        guard let policyId = chosenPetPolicy?.id,
              let policyOfferId = chosenPetPolicy?.policyOfferId,
              let reimbursement = chosenPetPolicy?.reimbursment?.amount else {
            completion(.failure(RequestError(errorType: .notFound, 
                                             errorMessage: "Infos required to locate coverages not found")))
            return
        }
        
        let parameters = PolicyCoverageParameters(id: policyId, 
                                                  offerId: policyOfferId,
                                                  reimbursement: reimbursement)
        getCoverages.execute(parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                completion(.failure(error))
            case .success(let coverages):
                let hasCoveragesAvailable = coverages.contains { coverage in
                    coverage.isCompleted == false
                }
                self.hasCoveragesAvailable = hasCoveragesAvailable
                completion(.success(hasCoveragesAvailable))
            }
        }
    }
    
    private func postUploadAttachments(attachments: String,
                                       fileType: ClaimFileInputType,
                                       completion: @escaping ((RequestResponse<[Int], NetworkRequestError>) -> Void)) {
        let uploadPetAttachmentParameter = UploadPetAttachmentParameters(files: attachments, 
                                                                         fileInputType: fileType)
        createDocumentsService.execute(uploadPetAttachmentParameter) { response in
            switch response {
            case .failure(let error):
                completion(.failure(NetworkRequestError(statusCode: 500, 
                                                        error: error.errorMessage)))
            case .success(let id):
                completion(.success(id))
            }
        }
    }
    
    private func postClaims(newClaim: NewPetClaimParameters,
                            completion: @escaping ((RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void)) {
        createClaimService.execute(parameters: newClaim) { response in
            switch response {
            case .failure(let error):
                if error.errorType == .conflict {
                    completion(.failure(NetworkRequestError(statusCode: 409)))
                } else {
                    completion(.failure(NetworkRequestError(statusCode: 500, 
                                                            error: error.errorMessage)))
                }
            case .success(let newClaim):
                let responseHandler = NewClaimMapper(newClaim).map()
                completion(.success(responseHandler))
            }
        }
    }
}
