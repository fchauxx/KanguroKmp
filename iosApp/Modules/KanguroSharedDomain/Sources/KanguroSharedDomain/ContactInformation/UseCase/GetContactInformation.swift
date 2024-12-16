import Foundation

public final class GetContactInformation: GetContactInformationUseCaseProtocol {

    private let contactInformationRepo: ContactInformationRepositoryProtocol

    public init(contactInformationRepo: ContactInformationRepositoryProtocol) {
        self.contactInformationRepo = contactInformationRepo
    }

    public func execute(completion: @escaping ((Result<[ContactInformation], RequestError>) -> Void)) {
        contactInformationRepo.getContactInformation { result in
            completion(result)
        }
    }
}
