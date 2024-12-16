import Foundation

public protocol ContactInformationRepositoryProtocol {
    func getContactInformation(
        completion: @escaping ((Result<[ContactInformation], RequestError>) -> Void)
    )
}
