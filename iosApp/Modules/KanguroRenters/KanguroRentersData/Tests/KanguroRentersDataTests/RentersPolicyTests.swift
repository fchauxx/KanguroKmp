import XCTest
import KanguroRentersData
import KanguroRentersDomain
import KanguroNetworkDomain
import KanguroSharedDomain

final class RentersPolicyTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }
    
    func test_getRenterPolicy_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .serverError, errorMessage: "error message")

        repository.getRenterPolicy(id: "abc") { result in
            switch result {
            case .success:
                XCTFail("It should fail")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteRenterPolicy, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteRenterPolicy, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.failure(.init(statusCode: 500, error: "error message", data: nil, isTokenError: nil)))
        wait(for: [expect], timeout: 3)
    }

    func test_getRenterPolicy_WhenAPISucceeds_ShouldReturnRenterPolicyWithProvidedId() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")

        let remoteRenterPolicy = TestRenterPolicyFactory.makeRemoteRenterPolicy()
        let renterPolicy = TestRenterPolicyFactory.makeRenterPolicy()

        repository.getRenterPolicy(id: "abc") { result in
            switch result {
            case .success(let policy):
                XCTAssertEqual(policy.dwellingType, renterPolicy?.dwellingType)
                XCTAssertEqual(policy.address?.city, renterPolicy?.address?.city)
            case .failure(let error):
                XCTFail("It should receive valid renter policy")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteRenterPolicy, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteRenterPolicy, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteRenterPolicy))
        wait(for: [expect], timeout: 3)
    }

    // MARK: - Helper methods
    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, RentersPolicyRepository) {
        let network = NetworkMock()
        let rentersPolicyRepository: RentersPolicyRepository = RentersPolicyRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(rentersPolicyRepository, file: file, line: line)
        return (network, rentersPolicyRepository)
    }
}
