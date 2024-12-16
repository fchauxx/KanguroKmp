import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class PasswordRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_updatePassword_WhenAPIIsSuccessfull_ShouldSendUserEmailToResetPassword() {
        let (network, repository) = makeSUT()
        let password = TestPasswordFactory.makePassword()
        let expect = XCTestExpectation(description: "test expectation")
        repository.updatePassword(parameters: PasswordParameters(email: "teste@teste.com",
                                                                 currentPassword: "currentPwd",
                                                                 newPassword: "currentPwd")) { result in
            switch result {
            case .success:
                XCTAssertEqual(password.email, "teste@teste.com")
                XCTAssertEqual(password.currentPassword, "currentPwd")
                XCTAssertEqual(password.newPassword, "newPwd")
            case .failure(let error):
                XCTFail("It should return success")
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 2)
    }
    
    func test_updatePassword_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.updatePassword(parameters: PasswordParameters(email: "teste@teste.com",
                                                                 currentPassword: "currentPwd",
                                                                 newPassword: "currentPwd")) { result in
            switch result {
            case .success:
                XCTFail("It should return error")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.failure(NetworkRequestError(statusCode: 401, error: "error message")))
        wait(for: [expect], timeout: 2)
    }
    
    // MARK: - Helper methods
    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, PasswordRepository) {
        let network = NetworkMock()
        let passwordRepository: PasswordRepository = PasswordRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(passwordRepository, file: file, line: line)
        return (network, passwordRepository)
    }
}
