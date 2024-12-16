import Foundation
import KanguroSharedData
import KanguroSharedDomain
import KanguroPetDomain
import KanguroPetData

struct TestPetClaimFactory {
    
    static func makeRemotePetClaim(
        id: String = "id",
        pet: RemotePet = TestPetFactory.makeRemotePet(),
        type: RemoteClaimType? = .Accident,
        status: RemoteClaimStatus? = .Approved,
        decision: String? = "decision",
        createdAt: String? = "2020-08-28T15:07:02+00:00",
        updatedAt: String? = "2020-08-28T15:07:02+00:00",
        invoiceDate: String? = "2020-08-28T15:07:02+00:00",
        description: String? = "description",
        prefixId: String? = "prefixID",
        amount: Double? = 400,
        amountPaid: Double = 400,
        amountTransferred: Double? = 400,
        deductibleContributionAmount: Double? = 500,
        chatbotSessionsIds: [String]? = ["id1", "id2"],
        hasPendingCommunications: Bool? = true,
        reimbursementProcess: RemoteReimbursementProcessType? = .VeterinarianReimbursement,
        fileUrl: String? = ""
    ) -> RemotePetClaim {
        RemotePetClaim(id: id,
                       pet: pet,
                       type: type,
                       status: status,
                       decision: decision,
                       createdAt: createdAt,
                       updatedAt: updatedAt,
                       invoiceDate: invoiceDate,
                       description: description,
                       prefixId: prefixId,
                       amount: amount,
                       amountPaid: amountPaid,
                       amountTransferred: amountTransferred,
                       deductibleContributionAmount: deductibleContributionAmount,
                       chatbotSessionsIds: chatbotSessionsIds,
                       hasPendingCommunications: hasPendingCommunications,
                       reimbursementProcess: reimbursementProcess,
                       fileUrl: fileUrl)
    }
    
    static func makePetClaim(
        id: String = "id",
        pet: Pet = TestPetFactory.makePet(),
        type: PetClaimType? = .Accident,
        status: ClaimStatus? = .Approved,
        decision: String? = "decision",
        createdAt: Date? = Date(timeIntervalSince1970: 1598627222),
        updatedAt: Date? = Date(timeIntervalSince1970: 1598627222),
        invoiceDate: Date? = Date(timeIntervalSince1970: 1598627222),
        description: String? = "description",
        prefixId: String? = "prefixID",
        amount: Double? = 400,
        amountPaid: Double = 400,
        amountTransferred: Double? = 400,
        deductibleContributionAmount: Double? = 500,
        chatbotSessionsIds: [String]? = ["id1", "id2"],
        hasPendingCommunications: Bool? = true,
        reimbursementProcess: ReimbursementProcessType? = .VeterinarianReimbursement,
        fileUrl: String? = ""
    ) -> PetClaim {
        PetClaim(id: id,
                 pet: pet,
                 type: type,
                 status: status,
                 decision: decision,
                 createdAt: createdAt,
                 updatedAt: updatedAt,
                 incidentDate: invoiceDate,
                 description: description,
                 prefixId: prefixId,
                 amount: amount,
                 amountPaid: amountPaid,
                 amountTransferred: amountTransferred,
                 deductibleContributionAmount: deductibleContributionAmount,
                 chatbotSessionsIds: chatbotSessionsIds,
                 isPendingCommunication: hasPendingCommunications ?? true,
                 reimbursementProcess: reimbursementProcess,
                 fileUrl: fileUrl)
    }
}
