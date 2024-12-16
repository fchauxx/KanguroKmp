import Foundation

public protocol ProfileRepositoryProtocol {
    
    func updateProfile(parameters: ProfileParameters,
                       completion: @escaping ((Result<Void, RequestError>) -> Void))
}
