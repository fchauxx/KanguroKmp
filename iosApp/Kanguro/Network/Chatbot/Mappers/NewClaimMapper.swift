import Foundation
import KanguroPetData
import KanguroPetDomain
import KanguroSharedData
import KanguroNetworkDomain

class NewClaimMapper {

    private var newClaim: PetClaim

    init(_ newClaim: PetClaim) {
        self.newClaim = newClaim
    }

    func map() -> RemotePetClaim {

        var remotePet: RemotePet?
        var remoteClaimType: RemoteClaimType?
        var remoteClaimStatus: String?
        if let pet = newClaim.pet {
            var remotePetGender: RemoteGender?
            var remoteType: RemotePetType?
            var remoteCloudDocumentPolicies: [RemoteCloudDocumentPolicy]?

            if let petGender = pet.gender {
                remotePetGender = RemoteGender(rawValue: petGender.rawValue)
            }
            if let petType = pet.type {
                remoteType = RemotePetType(rawValue: petType.rawValue)
            }
            if let petCloudDocumentPolicies = pet.cloudDocumentPolicies {
                remoteCloudDocumentPolicies = petCloudDocumentPolicies.map {

                    var remoteItemAttachments: [RemotePolicyAttachment]?
                    var remotePolicyDocuments: [RemotePolicyDocumentData]?
                    var remoteClaimDocuments: [RemoteClaimDocument]?
                    if let attachments = $0.policyAttachments {
                        remoteItemAttachments = attachments.map { attachment in
                            RemotePolicyAttachment(id: attachment.id, name: attachment.name, fileSize: attachment.fileSize)
                        }
                    }
                    if let policyDocs = $0.policyDocuments {
                        remotePolicyDocuments = policyDocs.map { doc in
                            RemotePolicyDocumentData(id: doc.id, name: doc.name, filename: doc.filename)
                        }
                    }
                    if let claimDocs = $0.claimDocuments {
                        remoteClaimDocuments = claimDocs.map { doc in
                            var remoteClaimDocs: [RemoteAttachment]?
                            if let docs = doc.claimDocuments {
                                remoteClaimDocs = docs.map { attachment in
                                    RemoteAttachment(id: attachment.id, fileName: attachment.fileName, fileSize: attachment.fileSize)
                                }
                            }

                            return RemoteClaimDocument(
                                claimPrefixId: doc.claimPrefixId,
                                claimId: doc.claimId,
                                claimDocuments: remoteClaimDocs

                            )
                        }
                    }

                    return RemoteCloudDocumentPolicy(
                        id: $0.id,
                        ciId: $0.ciId,
                        policyStartDate: $0.policyStartDate?.USADate,
                        policyAttachments: remoteItemAttachments,
                        policyDocuments: remotePolicyDocuments,
                        claimDocuments: remoteClaimDocuments
                    )
                }
            }

            remotePet = RemotePet(
                id: pet.id,
                name: pet.name,
                birthDate: pet.birthDate?.USADate_UTC,
                isBirthDateApproximated: pet.isBirthDateApproximated,
                gender: remotePetGender,
                type: remoteType,
                size: pet.size,
                isNeutered: pet.isNeutered,
                preExistingConditions: pet.preExistingConditions,
                petBreedId: pet.petBreedId,
                breed: pet.breed,
                zipCode: pet.zipCode,
                territoryId: pet.territoryId,
                additionalInfoSessionId: pet.additionalInfoSessionId,
                hasAdditionalInfo: pet.hasAdditionalInfo,
                petPictureUrl: pet.petPictureUrl,
                cloudDocumentPolicies: remoteCloudDocumentPolicies
            )
        }

        if let claimType = newClaim.type {
            remoteClaimType = RemoteClaimType(rawValue: claimType.rawValue)
        }
        if let claimStatus = newClaim.status {
            remoteClaimStatus = claimStatus.rawValue
        }

        return RemotePetClaim(
            id: newClaim.id,
            pet: remotePet,
            type: remoteClaimType,
            status: remoteClaimStatus,
            decision: newClaim.decision,
            createdAt: newClaim.createdAt?.USADate,
            updatedAt: newClaim.updatedAt?.USADate,
            invoiceDate: newClaim.updatedAt?.USADate,
            description: newClaim.description,
            prefixId: newClaim.prefixId,
            amount: newClaim.amount,
            amountPaid: newClaim.amountPaid,
            amountTransferred: newClaim.amountTransferred,
            deductibleContributionAmount: newClaim.deductibleContributionAmount,
            chatbotSessionsIds: newClaim.chatbotSessionsIds,
            hasPendingCommunications: newClaim.isPendingCommunication
        )
    }
}
