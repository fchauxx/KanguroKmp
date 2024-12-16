import XCTest
import KanguroPetData
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

final class VeterinariansRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func test_getVeterinarians_WhenAPISucceeds_ShouldReturnListOfVets() {
        let (network, repository) = makeSUT()
        let remoteVet = TestVeterinarianFactory.makeRemoteVeterinarian(id: 1)
        let remoteVet2 = TestVeterinarianFactory.makeRemoteVeterinarian(id: 2)
        let vet = TestVeterinarianFactory.makeVeterinarian(id: 1)
        let vet2 = TestVeterinarianFactory.makeVeterinarian(id: 2)
        let expect = XCTestExpectation(description: "test expectation")

        repository.getVeterinarians { result in
            switch result {
            case .success(let receivedListOfVets):
                XCTAssertEqual(receivedListOfVets, [vet, vet2])
            case .failure(let error):
                XCTFail("It should receive a valid pet")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<[RemoteVeterinarian], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteVeterinarian], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remoteVet, remoteVet2]))
        wait(for: [expect], timeout: 2)
    }

    func test_getVeterinarians_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .serverError, errorMessage: "error message")

        repository.getVeterinarians { result in
                switch result {
                case .success:
                    XCTFail("It should fail")
                case .failure(let error):
                    XCTAssertEqual(error.errorType, expectedError.errorType)
                    XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
                }
                expect.fulfill()
            }
            guard let completion: (RequestResponse<[RemoteVeterinarian], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteVeterinarian], NetworkRequestError>) -> Void else {
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
    ) -> (NetworkMock, VeterinariansRepository) {
        let network = NetworkMock()
        let vetRepository: VeterinariansRepository = VeterinariansRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(vetRepository, file: file, line: line)
        return (network, vetRepository)
    }
}
