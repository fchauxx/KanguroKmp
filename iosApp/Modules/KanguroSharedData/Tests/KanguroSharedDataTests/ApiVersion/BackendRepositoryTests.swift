import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class BackendRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getBackendVersion_WhenApiIsSuccessful_ShouldReturnValidBackendVersion() {
        let (network, repository) = makeSUT()
        let remoteBackendVersion = TestBackendFactory.makeRemoteApiVersion()
        let mobileBackendVersion = TestBackendFactory.makeMobileApiVersion()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getBackendVersion(maxVersion: mobileBackendVersion.version) { result in
            switch result {
            case .success:
                XCTAssertEqual(mobileBackendVersion.version, "7.0.0")
            case .failure(let error):
                XCTFail("It should receive a valid version")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<RemoteApiVersion, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<RemoteApiVersion, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteBackendVersion))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getBackendVersion_WhenApiFailsDueToInvalidVersion_ShouldReturnInvalidVersion() {
        let (network, repository) = makeSUT()
        let remoteBackendVersion = TestBackendFactory.makeRemoteApiVersion()
        let mobileBackendVersion = TestBackendFactory.makeOldMobileApiVersion()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getBackendVersion(maxVersion: mobileBackendVersion.version) { result in
            switch result {
            case .success:
                XCTFail("It should receive a invalid version")
            case .failure(let error):
                XCTAssertEqual(error, .invalidVersion)
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<RemoteApiVersion, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<RemoteApiVersion, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteBackendVersion))
        wait(for: [expect], timeout: 2)
    }
    
    // MARK: - Helper methods
    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, BackendVersionRepository) {
        let network = NetworkMock()
        let backendRepository: BackendVersionRepository = BackendVersionRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(backendRepository, file: file, line: line)
        return (network, backendRepository)
    }
}
