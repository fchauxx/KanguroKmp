import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class BankRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getBanks_WhenAPIIsSuccessfull_ShouldReturnAListOfBanks() {
        let (network, repository) = makeSUT()
        let remoteBankOptions = TestBankOptionFactory.makeRemoteBankOptionsResponse()
        let bankOptions = TestBankOptionFactory.makeBankOptionsResponse()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getBanks { result in
            switch result {
            case .success(let receivedKeycloak):
                XCTAssertEqual(receivedKeycloak, bankOptions)
            case .failure(let error):
                XCTFail("It should return success")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemoteBankOption], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemoteBankOption], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteBankOptions))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getBanks_WhenAPIFails_ShouldReturnError() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")

        repository.getBanks { result in
            switch result {
            case .success:
                XCTFail("It should return error")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<[RemoteBankOption], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteBankOption], NetworkRequestError>) -> Void else {
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
    ) -> (NetworkMock, BankRepository) {
        let network = NetworkMock()
        let bankRepository: BankRepository = BankRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(bankRepository, file: file, line: line)
        return (network, bankRepository)
    }
}
