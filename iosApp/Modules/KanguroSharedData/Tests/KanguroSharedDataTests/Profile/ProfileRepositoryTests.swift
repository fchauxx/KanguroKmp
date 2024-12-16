import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class ProfileRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getKeycloak_WhenAPIIsSuccessfull_ShouldReturnAValidKeycloak() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        repository.updateProfile(parameters: ProfileParameters(givenName: "Teste", surname: "Teste", phone: "9999999")) { result in
            switch result {
            case .success:
                break
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
    
    func test_getKeycloak_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.updateProfile(parameters: ProfileParameters(givenName: "Teste", surname: "Teste", phone: "9999999")) { result in
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
    ) -> (NetworkMock, ProfileRepository) {
        let network = NetworkMock()
        let profileRepository: ProfileRepository = ProfileRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(profileRepository, file: file, line: line)
        return (network, profileRepository)
    }
}
