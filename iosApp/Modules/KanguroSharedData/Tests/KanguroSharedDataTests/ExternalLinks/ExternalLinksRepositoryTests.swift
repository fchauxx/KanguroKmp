import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class ExternalLinksRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_redirectToPartnerWebsite_WhenApiIsSuccessful_ShouldReturnValidURL() {
        let (network, repository) = makeSUT()
        let remoteExternalLink = TestExternalLinkFactory.makeRemoteExternalLink()
        let externalLink = TestExternalLinkFactory.makeExternalLink()
        let expect = XCTestExpectation(description: "test expectation")
        repository.redirectToPartnerWebsite(partnerName: "test", parameters: UserIdParameters(id: "test")) { result in
            switch result {
            case .success:
                XCTAssertEqual(externalLink.redirectTo, "www.roampets.com")
            case .failure(let error):
                XCTFail("It should receive a valid version")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<RemoteURLRedirect, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<RemoteURLRedirect, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteExternalLink))
        wait(for: [expect], timeout: 2)
    }
    
    func test_redirectToPartnerWebsite_WhenApiFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let remoteExternalLink = TestExternalLinkFactory.makeRemoteExternalLink()
        let externalLink = TestExternalLinkFactory.makeExternalLink()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.redirectToPartnerWebsite(partnerName: "test", parameters: UserIdParameters(id: "test")) { result in
            switch result {
            case .success:
                XCTFail("It should receive an error")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<RemoteURLRedirect, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<RemoteURLRedirect, NetworkRequestError>) -> Void else {
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
    ) -> (NetworkMock, ExternalLinksRepository) {
        let network = NetworkMock()
        let externalLinksRepository: ExternalLinksRepository = ExternalLinksRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(externalLinksRepository, file: file, line: line)
        return (network, externalLinksRepository)
    }
}
