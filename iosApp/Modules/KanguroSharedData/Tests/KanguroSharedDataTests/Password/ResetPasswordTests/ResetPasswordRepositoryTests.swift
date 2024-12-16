import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class ResetPasswordRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_resetPassword_WhenAPIIsSuccessfull_ShouldSendUserEmailToResetPassword() {
        let (network, repository) = makeSUT()
        let resetPassword = TestResetPasswordFactory.makeResetPassword()
        let expect = XCTestExpectation(description: "test expectation")
        repository.createResetPassword(parameters: ResetPasswordParameters(email: "teste@teste.com")) { result in
            switch result {
            case .success:
                XCTAssertEqual(resetPassword.email, "teste@teste.com")
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
    
    func test_resetPassword_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.createResetPassword(parameters: ResetPasswordParameters(email: "teste@teste.com")) { result in
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
    ) -> (NetworkMock, ResetPasswordRepository) {
        let network = NetworkMock()
        let resetPasswordRepository: ResetPasswordRepository = ResetPasswordRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(resetPasswordRepository, file: file, line: line)
        return (network, resetPasswordRepository)
    }
}
