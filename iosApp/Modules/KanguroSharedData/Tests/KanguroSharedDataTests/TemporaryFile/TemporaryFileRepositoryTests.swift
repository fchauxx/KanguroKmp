import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class TemporaryFileRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }
    
    func test_createTemporaryFile_WhenAPISucceeds_ShouldReturnTempFileIntId() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")

        let data = Data()
        let tempFile1 = TestTemporaryFileFactory.makeTemporaryFile()
        let remoteTempFile1 = TestTemporaryFileFactory.makeRemoteTemporaryFile()

        repository.createTemporaryFile(tempFile: data) { result in
            guard let _ = try? result.get() else {
                XCTFail("Could not create Temporary File")
                expect.fulfill()
                return
            }
            switch result {
            case .success(let tempFile):
                XCTAssertEqual(tempFile.id, tempFile1.id)
                XCTAssertEqual(tempFile.url, tempFile1.url)
            case .failure(let error):
                XCTFail("It should receive valid temporary file id")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteTemporaryFile, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteTemporaryFile, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteTempFile1))
        wait(for: [expect], timeout: 3)
    }

    func test_createTemporaryFile_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .serverError, errorMessage: "error message")

        let data = Data()
        repository.createTemporaryFile(tempFile: data) { result in
            switch result {
            case .success:
                XCTFail("It should fail")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteTemporaryFile, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteTemporaryFile, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.failure(.init(statusCode: 500, error: "error message", data: nil, isTokenError: nil)))
        wait(for: [expect], timeout: 3)
    }

    // MARK: - Helper methods
    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, TemporaryFileRepository) {
        let network = NetworkMock()
        let temporaryFileRepository: TemporaryFileRepository = TemporaryFileRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(temporaryFileRepository, file: file, line: line)
        return (network, temporaryFileRepository)
    }
}
