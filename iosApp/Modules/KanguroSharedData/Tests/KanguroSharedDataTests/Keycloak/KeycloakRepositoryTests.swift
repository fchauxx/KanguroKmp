import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class KeycloakRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getKeycloak_WhenAPIIsSuccessfull_ShouldReturnAValidKeycloak() {
        let (network, repository) = makeSUT()
        let remoteKeycloak = TestKeycloakFactory.makeRemoteKeycloakResponse()
        let keycloak = TestKeycloakFactory.makeKeycloakResponse()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getKeycloak { result in
            switch result {
            case .success(let receivedKeycloak):
                XCTAssertEqual(receivedKeycloak, keycloak)
            case .failure(let error):
                XCTFail("It should return success")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteKeycloakResponse, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteKeycloakResponse, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteKeycloak))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getKeycloak_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.getKeycloak { result in
            switch result {
            case .success:
                XCTFail("It should return error")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteKeycloakResponse, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteKeycloakResponse, NetworkRequestError>) -> Void else {
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
    ) -> (NetworkMock, KeycloakRepository) {
        let network = NetworkMock()
        let keycloakRepository: KeycloakRepository = KeycloakRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(keycloakRepository, file: file, line: line)
        return (network, keycloakRepository)
    }
}
