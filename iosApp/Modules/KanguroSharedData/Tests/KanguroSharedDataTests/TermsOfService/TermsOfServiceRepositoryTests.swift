import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class TermsOfServiceRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getTermsService_WhenAPIIsSuccessfull_ShouldReturnAValidKeycloak() {
        let (network, repository) = makeSUT()
        let remoteTermsOfService = anyData()
        let termsOfService = anyData()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getTermsOfService(parameters: TermsOfServiceParameters(preferencialLanguage: "language_test")) { result in
            switch result {
            case .success(let receivedData):
                XCTAssertEqual(receivedData, termsOfService)
            case .failure(let error):
                XCTFail("It should receive a valid attachment")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteTermsOfService))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getTermsService_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.getTermsOfService(parameters: TermsOfServiceParameters(preferencialLanguage: "language_test")) { result in
            switch result {
            case .success:
                XCTFail("It should return error")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void else {
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
    ) -> (NetworkMock, TermsOfServiceRepository) {
        let network = NetworkMock()
        let termsOfServiceRepository: TermsOfServiceRepository = TermsOfServiceRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(termsOfServiceRepository, file: file, line: line)
        return (network, termsOfServiceRepository)
    }
}
