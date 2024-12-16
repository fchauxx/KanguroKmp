import Foundation
@testable import Kanguro
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

class RemindersServiceMock {
    
    enum Methods {
        case getReminders
    }
    
    var requestShouldFail: Bool = false
    var calledMethods: [Methods] = []
}

extension RemindersServiceMock: GetRemindersUseCaseProtocol {
    func execute(completion: @escaping ((Result<[Reminder], RequestError>) -> Void)) {
        calledMethods.append(.getReminders)
        if requestShouldFail {
            completion(.failure(RequestError(errorType: .serverError, errorMessage: "serverError.default".localized)))
        } else {
            completion(.success([Reminder(petId: 555, type: .MedicalHistory)]))
        }
    }
}
