import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class KanguroParameterRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getInformationTopics_WhenAPIIsSuccessfull_ShouldChangeAppLanguage() {
        let (network, repository) = makeSUT()
        let remoteInformerData = TestInformerDataFactory.makeRemoteInformerData(key: 1)
        let remoteInformerData2 = TestInformerDataFactory.makeRemoteInformerData(key: 2)
        let informerData = TestInformerDataFactory.makeInformerData(key: 1)
        let informerData2 = TestInformerDataFactory.makeInformerData(key: 2)
        let expect = XCTestExpectation(description: "test expectation")
        repository.getInformationTopics(parameters: KanguroParameterModuleParameters(key: "test_key")) { result in
            switch result {
            case .success(let receivedInformerDataList):
                XCTAssertEqual(receivedInformerDataList, [informerData, informerData2])
            case .failure(let error):
                XCTFail("It should return success")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemoteInformerData], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemoteInformerData], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remoteInformerData, remoteInformerData2]))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getInformationTopics_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.getInformationTopics(parameters: KanguroParameterModuleParameters(key: "test_key")) { result in
            switch result {
            case .success:
                XCTFail("It should return error")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemoteInformerData], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemoteInformerData], NetworkRequestError>) -> Void else {
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
    ) -> (NetworkMock, KanguroParameterRepository) {
        let network = NetworkMock()
        let kanguroParameterRepository: KanguroParameterRepository = KanguroParameterRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(kanguroParameterRepository, file: file, line: line)
        return (network, kanguroParameterRepository)
    }
}
