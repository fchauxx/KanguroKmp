import UIKit
import MobileCoreServices
import PhotosUI
import KanguroUserDomain
import FirebaseCrashlytics
import KanguroSharedDomain
import KanguroDesignSystem
import SwiftUI

enum ChatbotButtonType {
    
    case dual
    case stack
}

enum LocalChatbotError: Error {
    
    case tableViewCellForRowOutOfRange
}

class ChatbotView: BaseView, NibOwnerLoadable {
    
    // MARK: - Dependencies
    var viewModel: ChatbotViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var tableView: ChatTableView!
    @IBOutlet var stackButtonsView: StackButtonsView!
    @IBOutlet var dualButtonsView: DualButtonsView!
    @IBOutlet var inputTextFieldView: CustomTextFieldView!
    @IBOutlet var inputContainerView: UIView!
    
    // MARK: - Stored Properties
    var imagePicker = UIImagePickerController()
    let delayFactor = 0.4
    private var loadingView: LoadingCellView!
    private var shouldShowLoadingView = true
    private var isEditableCell = false
    private var isEditingCell = false
    
    // MARK: - Computed Properties
    var isLoading: Bool {
        return viewModel.state == .loading
    }
    
    // MARK: - Actions
    var didTapAnswerButtonsAction: NextStepClosure = { _ in }
    var didTapFinishAction: SimpleClosure = {}
    var didFinishPetInformationAction: SimpleClosure = {}
    var didFinishClaimButtonAction: SimpleClosure = {}
    var didFinishCommunicationButtonAction: SimpleClosure = {}
    var didFinishClaim: StringClosure = { _ in }
    var didTapStopClaimAction: SimpleClosure = {}
    var didTapStopDuplicatedClaimAction: SimpleClosure = {}
    var didTapIndexAction: IntClosure = { _ in }
    var didTapSignatureAction: SimpleClosure = {}
    var didTapOTPAction: SimpleClosure = {}
    var didTapBankAccountAction: SimpleClosure = {}
    var requestFailedAction: SimpleClosure = {}
    var didUpdateCommunicationStep: SimpleClosure = {}
    var didTapMapAction: SimpleClosure = {}
    var didTapVaccinesAndExamsAction: SimpleClosure = {}
    var didTapEditImageAction: ImageClosure = { _ in }
    
    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - View State
extension ChatbotView {
    
    func changed(state: ChatbotViewState) {
        
        shouldShowLoadingView = true
        
        switch state {
        case .started:
            viewModel.starterSetup()
        case .dataChanged:
            if viewModel.data.isCommunicationType {
                didUpdateCommunicationStep()
            }
        case .removedImage:
            hideLoadingView()
            if viewModel.filesPath.isEmpty { setupUploadStackButtons() }
        case .loading:
            hideAllInputs()
        case .newMessageAdded:
            reloadTableView()
            if viewModel.didFinishMessages {
                hideLoadingView()
                setupButtons()
            }
        case .finishedPetInfo:
            didFinishPetInformationAction()
        case .cleanedMessages:
            cleanRows()
        case .requestFailed:
            showActionAlert(message: viewModel.requestError,
                            action: requestFailedAction)
            hideAllInputs()
        case .requestSucceeded:
            if viewModel.shouldGetPetInfo { viewModel.getPetInfo() }
            if viewModel.data.isChatStepEmpty {
                hideLoadingView()
                setupButtons()
            }
            reloadSection()
            hideLoadingView()
        case .imageFormatNotSupported:
            showCustomSimpleAlert(popUpData: KanguroDesignSystem.PopUpData(title: "alert.notSupportedImage.title".localized,
                                                                           description: "alert.notSupportedImage.description".localized))
        default:
            break
        }
    }
}

// MARK: - Setup
extension ChatbotView {
    
    func setupObserver() {
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    func setupButtons() {
        guard let step = viewModel.data.chatInteractionStep else { return }
        
        switch step.type {
        case .TextInput, .DateInput, .NumberInput, .CurrencyInput:
            showInputTextFieldView(type: step.type)
        case .UploadPicture, .UploadPetPicture:
            setupUploadStackButtons()
        case .Finish:
            viewModel.data.isStarterSetup ? hideAllInputs() : setupDefaultButtons()
        default:
            setupDefaultButtons()
        }
    }
    
    private func setupButtonsOrientation(_ orientation: ButtonOrientation) {
        setChatButtonsHidden(orientation != .Horizontal, type: .dual)
        setChatButtonsHidden(orientation != .Vertical, type: .stack)
    }
    
    func addButtons(_ orientation: ButtonOrientation) {
        guard let actions = viewModel.data.chatInteractionStep?.actions else { return }
        if !actions.isEmpty {
            for index in 0..<actions.count {
                addButtonsByActions(index: index, orientation: orientation)
            }
        }
        setupButtonsOrientation(orientation)
    }
    
    func addButtonsByActions(index: Int, orientation: ButtonOrientation) {
        guard let actions = viewModel.data.chatInteractionStep?.actions else { return }
        let currentAction = actions[index]
        let title = currentAction.label
        let isMainAction = currentAction.isMainAction ?? false
        let defaultButtonData = ChatbotButtonData(title: title,
                                                  nextStepData: getNextStepData(index: index),
                                                  isMainAction: isMainAction)
        
        let defaultNextStepData = NextStepParameters(sessionId: viewModel.data.chatInteractionStepSessionId,
                                                     value: viewModel.data.chatInteractionStep?.actions[index].value,
                                                     action: currentAction.action)
        
        switch currentAction.action {
        case .Signature:
            let data = ChatbotButtonData(title: title,
                                         action: didTapSignatureAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .LocalAction:
            let data = ChatbotButtonData(title: title,
                                         indexAction: didTapIndexAction,
                                         isMainAction: isMainAction)
            addStackButton(data)
        case .Finish:
            let data = ChatbotButtonData(title: title,
                                         nextStepData: defaultNextStepData,
                                         action: didTapFinishAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .FinishPetInformation:
            let data = ChatbotButtonData(title: title,
                                         nextStepData: defaultNextStepData,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .FinishAndRedirect:
            let data = ChatbotButtonData(title: title,
                                         action: didFinishClaimButtonAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .FinishCommunication:
            let data = ChatbotButtonData(title: title,
                                         action: didFinishCommunicationButtonAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .StopClaim:
            let data = ChatbotButtonData(title: title,
                                         action: didTapStopClaimAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .StopDuplicatedClaim:
            let data = ChatbotButtonData(title: title,
                                         action: didTapStopDuplicatedClaimAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .UploadFile, .UploadImage, .UploadMultipleFiles:
            let data = ChatbotButtonData(title: title,
                                         action: setupUploadStackButtons,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .EditInput:
            let data = ChatbotButtonData(title: title,
                                         action: editUserInput,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .UserCustomInput:
            let data = ChatbotButtonData(title: title,
                                         action: manageCustomUserInput,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .Reimbursement:
            let data = ChatbotButtonData(title: title,
                                         action: didTapBankAccountAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .FinishFilesUpload:
            let data = ChatbotButtonData(title: title,
                                         action: viewModel.postFiles,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .VaccinesAndExamsSelect:
            let data = ChatbotButtonData(title: title,
                                         action: didTapVaccinesAndExamsAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        case .OtpValidation:
            let data = ChatbotButtonData(title: title,
                                         action: didTapOTPAction,
                                         isMainAction: isMainAction)
            addDualButton(data)
        default:
            if orientation == .Horizontal {
                addDualButton(defaultButtonData)
            } else {
                addStackButton(defaultButtonData)
            }
        }
    }
    
    func getNextStepData(index: Int) -> NextStepParameters {
        let value = viewModel.data.chatInteractionStep?.actions[index].value
        let action = viewModel.data.chatInteractionStep?.actions[index].action ?? .none
        return NextStepParameters(sessionId: viewModel.data.chatInteractionStepSessionId, value: value, action: action)
    }
    
    func setupTextField(type: TextFieldType) {
        inputTextFieldView.set(type: type,
                               actions: TextFieldActions(trailingButtonAction: sendInputText))
    }
    
    func setupActions() {
        didTapAnswerButtonsAction = { [weak self] nextStepData in
            guard let self = self,
                  let data = nextStepData else { return }
            self.viewModel.addUserAnswer(data)
            self.viewModel.nextStep(data)
        }
        didFinishClaimButtonAction = { [weak self] in
            guard let self = self,
                  let claimId = self.viewModel.claimId else { return }
            self.didFinishClaim(claimId)
        }
    }
    
    func setupDefaultButtons() {
        stackButtonsView.clean()
        dualButtonsView.clean()
        addButtons(viewModel.data.chatInteractionStep?.orientation ?? .Horizontal)
    }
    
    private func setupLoadingView() {
        if loadingView == nil {
            loadingView = LoadingCellView(frame: CGRect(x: 0, y: 0, width: tableView.frame.size.width, height: 80))
        }
        loadingView.startLoader()
    }
}

// MARK: - Private Methods
extension ChatbotView {
    
    func addDualButton(_ buttonData: ChatbotButtonData) {
        let style: ChatButtonItemStyle = buttonData.isMainAction ? .bolded : .default
        dualButtonsView.addButton(data: ChatButtonItemData(title: buttonData.title,
                                                           style: style,
                                                           action: buttonData.action,
                                                           nextStepAction: didTapAnswerButtonsAction),
                                  nextStepData: buttonData.nextStepData)
    }
    
    func addStackButton(_ buttonData: ChatbotButtonData) {
        stackButtonsView.addStackButton(stackButtonData: StackButtonData(title: buttonData.title,
                                                                         action: buttonData.action,
                                                                         nextStepAction: didTapAnswerButtonsAction,
                                                                         indexAction: buttonData.indexAction),
                                        nextStepData: buttonData.nextStepData)
    }
    
    private func editUserInput() {
        showInputTextFieldView(type: viewModel.currentStepInputType)
        isEditingCell = true
        reloadSection()
        hideLoadingView()
    }
    
    private func sendInputText() {
        guard let text = inputTextFieldView.textField.text else { return }
        if isEditableCell {
            removeLastMessage()
            isEditingCell = false
        }
        isEditableCell = true
        viewModel.setupCustomInputConfirmStep(content: text)
        setInputTextFieldViewHidden(true, chatType: viewModel.data.chatType)
        inputTextFieldView.textField.resignFirstResponder()
    }
    
    private func removeLastMessage() {
        viewModel.loadedMessages.removeLast()
        removeTableViewRow(tableView.numberOfRows(inSection: 0))
    }
    
    private func manageCustomUserInput() {
        guard let text = inputTextFieldView.textField.text else { return }
        isEditableCell = false
        viewModel.customNextStepCall(content: text, action: .UserCustomInput)
        inputTextFieldView.textField.text = ""
        setInputTextFieldViewHidden(true, chatType: viewModel.data.chatType)
        inputTextFieldView.textField.resignFirstResponder()
    }
    
    func showInputTextFieldView(type: ChatbotInteractionType) {
        setInputTextFieldViewHidden(false, chatType: type)
        setChatButtonsHidden(true, type: .dual)
        setChatButtonsHidden(true, type: .stack)
    }
    
    private func reloadTableView() {
        let row = viewModel.loadedMessages.count - 1
        if row != tableView.numberOfRows(inSection: 0) { return }
        tableView.beginUpdates()
        tableView.insertRows(at: [IndexPath(row: row, section: 0)], with: .bottom)
        tableView.endUpdates()
        DispatchQueue.main.asyncAfter(deadline: .now()) { [weak self] in
            guard let self else { return }
            self.tableView.scrollToBottom()
        }
    }
    
    private func removeTableViewRow(_ row: Int) {
        UIView.performWithoutAnimation { [weak self] in
            guard let self else { return }
            self.tableView.beginUpdates()
            self.tableView.deleteRows(at: [IndexPath(row: row, section: 0)], with: .automatic)
            self.tableView.reloadSections([0], with: .none)
            self.tableView.endUpdates()
        }
    }
    
    private func reloadSection() {
        UIView.performWithoutAnimation { [weak self] in
            guard let self else { return }
            self.tableView.reloadSections([0], with: .none)
        }
    }
    
    func setChatButtonsHidden(_ hidden: Bool, type: ChatbotButtonType) {
        let chatbotButton = getCurrentButtonView(type: type)
        
        if !hidden {
            animateChatbotButtons(type)
        } else {
            switch type {
            case .stack:
                stackButtonsView.clean()
            default:
                break
            }
            chatbotButton.isHidden = true
        }
    }
    
    private func getCurrentButtonView(type: ChatbotButtonType) -> BaseView {
        switch type {
        case .stack:
            return stackButtonsView
        case .dual:
            return dualButtonsView
        }
    }
    
    func hideAllInputs() {
        setChatButtonsHidden(true, type: .stack)
        setChatButtonsHidden(true, type: .dual)
        setInputTextFieldViewHidden(true)
        inputTextFieldView.textField.endEditing(true)
    }
    
    func animateChatbotButtons(_ type: ChatbotButtonType) {
        let chatbotButton = getCurrentButtonView(type: type)
        chatbotButton.alpha = 0
        DispatchQueue.main.asyncAfter(deadline: .now()) { [weak self] in
            guard self != nil else { return }
            UIView.animate(withDuration: 0.4) { [weak self] in
                guard self != nil else { return }
                chatbotButton.isHidden = false
                chatbotButton.alpha = 1
            }
        }
    }
    
    private func showInputTextFieldAnimated() {
        inputContainerView.alpha = 0
        DispatchQueue.main.asyncAfter(deadline: .now()) { [weak self] in
            guard self != nil else { return }
            UIView.animate(withDuration: 0.4) { [weak self] in
                guard let self = self else { return }
                self.inputContainerView.isHidden = false
                self.inputContainerView.alpha = 1
                self.inputTextFieldView.textField.becomeFirstResponder()
            }
        }
    }
    
    func setInputTextFieldViewHidden(_ hidden: Bool, chatType: ChatbotInteractionType = .TextInput) {
        if hidden == inputContainerView.isHidden { return }
        
        if !hidden {
            var type: TextFieldType?
            switch chatType {
            case .DateInput:
                type = .calendarChatbot
            case .TextInput:
                type = .chatbot
            case .NumberInput:
                type = .cellphone
            case .CurrencyInput:
                type = .currencyChatbot
            default:
                break
            }
            viewModel.currentStepInputType = chatType
            setupTextField(type: type ?? .chatbot)
            showInputTextFieldAnimated()
        }
        inputContainerView.isHidden = hidden
    }
    
    func reloadAfterRequest() {
        setupButtons()
        reloadTableView()
    }
    
    private func hideLoadingView() {
        if viewModel.state == .removedImage {
            loadingView.alpha = 0
        } else {
            loadingView.alpha = 1
            UIView.animate(withDuration: 1) { [weak self] in
                guard let self else { return }
                self.loadingView.alpha = 0
            }
        }
    }
    
    private func cleanRows() {
        let numberOfRows = tableView.numberOfRows(inSection: 0)
        tableView.beginUpdates()
        for index in 0..<numberOfRows {
            let path = IndexPath(row: index, section: 0)
            tableView.deleteRows(at: [path], with: .automatic)
        }
        tableView.endUpdates()
    }
    
    private func setCentralChatbotWillAppear(shouldAppear: Bool) {
        if let parentViewController = parentViewController as? CentralChatbotViewController {
            parentViewController.shouldSetupChatbot = shouldAppear
        }
    }
    
    private func getEditingCellStatus(currentMessage: ChatMessage) -> EditingCellStatus {
        let isLastMessage = (viewModel.loadedMessages.last == currentMessage)
        if (isEditingCell && isLastMessage) {
            return .isEditing
        } else if (isEditableCell && isLastMessage) {
            return .isEditable
        } else {
            return .basic
        }
    }
}

// MARK: - Public Methods
extension ChatbotView {
    
    func setup() {
        setupObserver()
        setupLoadingView()
        tableView.setup()
        setupImagePicker()
        setupActions()
    }
    
    func updateSignature(signature: UIImage) {
        viewModel.manageSignature(image: signature)
    }
    
    func updateBankAccount(account: BankAccount) {
        viewModel.manageBankAccount(account: account)
    }
    
    func updatePreventiveItems(names: String) {
        viewModel.appendUserNewMessage(content: names)
        viewModel.customNextStepCall(content: names,
                                     action: .VaccinesAndExamsSelect)
    }
    
    func handleSucceededOTPCode() {
        let action: ChatbotAction = .Finish
        viewModel.customNextStepCall(content: action.rawValue.lowercased(), action: action)
    }
    
    func update(stopClaimAction: @escaping SimpleClosure) {
        self.didTapStopClaimAction = stopClaimAction
    }
    
    func update(stopDuplicatedClaimAction: @escaping SimpleClosure) {
        self.didTapStopDuplicatedClaimAction = stopDuplicatedClaimAction
    }
    
    func update(finishPetInformationAction: @escaping SimpleClosure) {
        self.didFinishPetInformationAction = finishPetInformationAction
    }
    
    func update(mapAction: @escaping SimpleClosure) {
        self.didTapMapAction = mapAction
    }
}

// MARK: - Setup File Handlers
extension ChatbotView {
    
    func showGallery() {
        if (viewModel.data.chatInteractionStep?.type != .UploadPetPicture) {
            setupMultipleFilesSelectionGallery()
        } else {
            selectPicture()
        }
    }
    
    func setupMultipleFilesSelectionGallery() {
        var config = PHPickerConfiguration(photoLibrary: PHPhotoLibrary.shared())
        config.selectionLimit = 10
        config.filter = .images
        let pickerController = PHPickerViewController(configuration: config)
        pickerController.delegate = self
        self.parentViewController?.present(pickerController, animated: true)
    }
    
    func setupUploadStackButtons() {
        stackButtonsView.clean()
        setChatButtonsHidden(true, type: .dual)
        setChatButtonsHidden(false, type: .stack)
        addUploadButtons()
    }
    
    func addUploadButtons() {
        guard let step = viewModel.data.chatInteractionStep else { return }
        stackButtonsView.addStackButton(stackButtonData: StackButtonData(title: "addInfo.takePicture.button".localized,
                                                                         type: .file,
                                                                         image: UIImage(named: "ic-camera"),
                                                                         action: takePicture))
        stackButtonsView.addStackButton(stackButtonData: StackButtonData(title: "addInfo.selectPicture.button".localized,
                                                                         type: .file,
                                                                         image: UIImage(named: "ic-gallery"),
                                                                         action: showGallery))
        if step.type != .UploadPetPicture {
            stackButtonsView.addStackButton(stackButtonData: StackButtonData(title: "addInfo.selectFile.button".localized,
                                                                             type: .file,
                                                                             image: UIImage(named: "ic-file"),
                                                                             action: selectFile))
        }
        
        if step.type == .ButtonList && !viewModel.filesPath.isEmpty  {
            stackButtonsView.addStackButton(stackButtonData: StackButtonData(title: "addInfo.close.button".localized,
                                                                             type: .standard,
                                                                             action: closeUploadStackButtons))
        }
    }
    
    func closeUploadStackButtons() {
        setChatButtonsHidden(true, type: .stack)
        setChatButtonsHidden(false, type: .dual)
    }
    
    private func setupImagePicker() {
        imagePicker.delegate = self
        imagePicker.allowsEditing = false
    }
    
    private func selectFile() {
        let importMenu = UIDocumentPickerViewController(forOpeningContentTypes: [UTType.pdf],
                                                        asCopy: true)
        importMenu.delegate = self
        importMenu.modalPresentationStyle = .formSheet
        self.parentViewController?.present(importMenu, animated: true, completion: nil)
    }
    
    private func selectPicture() {
        imagePicker.sourceType = .photoLibrary
        parentViewController?.present(imagePicker, animated: true)
    }
    
    func takePicture() {
        setCentralChatbotWillAppear(shouldAppear: false)
        imagePicker.sourceType = .camera
        parentViewController?.present(imagePicker, animated: true)
    }
}

//MARK: - UITableViewDelegate & UITableViewDataSource
extension ChatbotView: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        let count = viewModel.loadedMessages.count
        return count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        if indexPath.row < viewModel.loadedMessages.count {
            let currentMessage = viewModel.loadedMessages[indexPath.row]
            
            switch currentMessage.format {
            case .Text:
                guard let cell = tableView.dequeueReusableCell(withIdentifier: TextCell.identifier) as? TextCell else { return UITableViewCell() }
                
                cell.setup(message: currentMessage,
                           isFirstMessage: viewModel.isFirstMessage(index: indexPath.row),
                           editingStatus: getEditingCellStatus(currentMessage: currentMessage))
                cell.animate(delay: delayFactor)
                
                return cell
                
            case .Summary:
                guard let cell = tableView.dequeueReusableCell(withIdentifier: SummaryCell.identifier) as? SummaryCell,
                      let summary = viewModel.contentAsSummary(content: currentMessage.content) else { return UITableViewCell() }
                summary.summaryData.forEach { cell.addSummaryCard(summaryData: $0) }
                cell.animate(delay: delayFactor)
                return cell
                
            case .Image, .PetImage:
                guard let cell = tableView.dequeueReusableCell(withIdentifier: ImageCell.identifier) as? ImageCell else { return UITableViewCell() }
                let currentImage = viewModel.getConvertedImage(encodedImage: currentMessage.content)
                cell.didTapRemoveAction = { [weak self] in
                    guard let self else { return }
                    self.viewModel.removeImage(index: indexPath.row)
                    self.removeTableViewRow(indexPath.row)
                    self.shouldShowLoadingView = false
                }
                cell.setup(image: currentImage,
                           isPetImage: currentMessage.format == .PetImage,
                           isDeletable: viewModel.isDeletableImage(index: indexPath.row) && currentMessage.format != .PetImage)
                cell.animate(delay: delayFactor)
                return cell
                
            case .Map:
                guard let cell = tableView.dequeueReusableCell(withIdentifier: MapCell.identifier) as? MapCell else { return UITableViewCell() }
                cell.setup(didTapMapAction: didTapMapAction)
                cell.animate(delay: delayFactor)
                return cell
            }
        } else {
            Crashlytics.crashlytics().record(error: LocalChatbotError.tableViewCellForRowOutOfRange)
            return UITableViewCell()
        }
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        return shouldShowLoadingView ? loadingView : nil
    }
    
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableView.automaticDimension
    }
}

// MARK: - UIImagePickerControllerDelegate
extension ChatbotView: UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {
        guard let image = info[.originalImage] as? UIImage ?? info[.editedImage] as? UIImage else {
            viewModel.state = .imageFormatNotSupported
            picker.dismiss(animated: true)
            return
        }

        if picker.sourceType == .camera || (info[.imageURL] as? NSURL).map(image.isImageFormatSupported) == true {
            viewModel.manageImages(images: [image])
        } else {
            viewModel.state = .imageFormatNotSupported
        }

        picker.dismiss(animated: true)
    }
}

// MARK: - PHPickerViewControllerDelegate
extension ChatbotView: PHPickerViewControllerDelegate {
    
    func picker(_ picker: PHPickerViewController, didFinishPicking results: [PHPickerResult]) {
        var chosenImages: [UIImage] = []
        results.forEach { result in
            DispatchQueue.main.async { [weak self] in
                guard let self else { return }
                result.itemProvider.loadObject(ofClass: UIImage.self) { imageObject, error in
                    guard let image = imageObject as? UIImage, error == nil else {
                        self.viewModel.state = .imageFormatNotSupported
                        return
                    }
                    result.itemProvider.loadFileRepresentation(forTypeIdentifier: UTType.image.identifier) { url, error in
                        if let path = url as? NSURL, image.isImageFormatSupported(imagePath: path) {
                            chosenImages.append(image)
                            if chosenImages.count == results.count {
                                self.viewModel.manageImages(images: chosenImages)
                            }
                        } else {
                            self.viewModel.state = .imageFormatNotSupported
                        }
                    }
                }
            }
        }
        picker.dismiss(animated: true, completion: .none)
    }
}

// MARK: - UIDocumentPickerViewController
extension ChatbotView: UIDocumentPickerDelegate {
    
    public func documentPicker(_ controller: UIDocumentPickerViewController,
                               didPickDocumentsAt urls: [URL]) {
        guard let myURL = urls.first else { return }
        viewModel.manageFile(file: myURL)
    }
    
    public func documentMenu(_ documentMenu: UIDocumentPickerViewController,
                             didPickDocumentPicker documentPicker: UIDocumentPickerViewController) {
        documentPicker.delegate = self
        documentMenu.present(documentPicker, animated: true, completion: nil)
    }
    
    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        controller.dismiss(animated: true, completion: nil)
    }
}
