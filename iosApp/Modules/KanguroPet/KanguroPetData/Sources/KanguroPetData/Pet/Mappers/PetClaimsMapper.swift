import Foundation
import KanguroSharedDomain
import KanguroPetDomain
import KanguroNetworkDomain

public struct PetClaimsMapper: ModelMapper {
    public typealias T = [PetClaim]

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: [RemotePetClaim] = input as? [RemotePetClaim] else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        let claims: [PetClaim] = try input.map {
            try PetClaimMapper.map($0)
        }
        guard let result: T = claims as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}

public struct PetClaimMapper: ModelMapper {
    public typealias T = PetClaim

    public static func map<T>(_ input: some Codable) throws -> T {
        guard let input: RemotePetClaim = input as? RemotePetClaim else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        var pet: Pet? = nil
        var type: PetClaimType? = nil
        var status: ClaimStatus? = nil
        var reimbursementProcess: ReimbursementProcessType? = nil
        var claimStatusDescription: ClaimStatusDescription? = nil

        if let remotePet = input.pet {
            pet = try PetMapper.map(remotePet)
        }
        if let remoteType = input.type {
            type = PetClaimType(rawValue: remoteType.rawValue)
        }
        if let remoteStatus = input.status {
            status = ClaimStatus(value: remoteStatus)
        }
        if let remoteReimbursementProcess = input.reimbursementProcess {
            reimbursementProcess = ReimbursementProcessType(rawValue: remoteReimbursementProcess.rawValue)
        }

        if let remoteClaimStatusDescription = input.statusDescription {
            claimStatusDescription = try ClaimStatusDescriptionMapper.map(remoteClaimStatusDescription)
        }

        let claim = PetClaim(
            id: input.id,
            pet: pet,
            type: type,
            status: status,
            decision: input.decision,
            createdAt: input.createdAt?.date,
            updatedAt: input.updatedAt?.date,
            incidentDate: input.invoiceDate?.date,
            description: input.description,
            prefixId: input.prefixId,
            amount: input.amount,
            amountPaid: input.amountPaid,
            amountTransferred: input.amountTransferred,
            deductibleContributionAmount: input.deductibleContributionAmount,
            chatbotSessionsIds: input.chatbotSessionsIds,
            isPendingCommunication: input.hasPendingCommunications ?? false,
            reimbursementProcess: reimbursementProcess,
            fileUrl: input.fileUrl,
            statusDescription: claimStatusDescription
        )
        guard let result: T = claim as? T else {
            throw RequestError(errorType: .couldNotMap, errorMessage: "Could not map")
        }
        return result
    }
}
