import Foundation

public protocol GetContactInformationUseCaseProtocol {

    func execute(completion: @escaping((Result<[ContactInformation], RequestError>) -> Void))
}
