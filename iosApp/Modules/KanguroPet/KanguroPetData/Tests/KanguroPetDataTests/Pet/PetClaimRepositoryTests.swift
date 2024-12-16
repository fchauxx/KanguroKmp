import XCTest
import KanguroPetData
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

final class PetClaimRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }
    
    func testPostCreateClaimSuccessfully() {
        let (network, repository) = makeSUT()
        let remotePetClaim = TestPetClaimFactory.makeRemotePetClaim()
        let petClaim = TestPetClaimFactory.makePetClaim()
        let expect = XCTestExpectation(description: "test expectation")
        
        repository.createClaim(NewPetClaimParameters(description: petClaim.description!,
                                                     invoiceDate: petClaim.incidentDate!,
                                                     amount: petClaim.amount!,
                                                     petId: petClaim.pet!.id,
                                                     type: .Accident, pledgeOfHonorId: 30,
                                                     reimbursementProcess: .UserReimbursement, documentIds: [1,2,3])) { result in
            switch result {
                case .success(let receivedPetClaim):
                    XCTAssertEqual(receivedPetClaim, petClaim)
                case .failure(let error):
                    XCTFail("It should receive a valid pet")
                }
                expect.fulfill()
            }
            guard let completion: (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void else {
                XCTFail("Completion should exist")
                expect.fulfill()
                return
            }
            completion(.success(remotePetClaim))
            wait(for: [expect], timeout: 2)
    }

    func test_createPetVetDirectPaymentClaim_WhenUserSelectsVetThatExistsOnDatabaseAndAPISucceeds_ShouldReturnNewPetClaim() {
        let (network, repository) = makeSUT()
        let remotePetClaim = TestPetClaimFactory.makeRemotePetClaim()
        let petClaim = TestPetClaimFactory.makePetClaim()
        let expect = XCTestExpectation(description: "test expectation")

        repository.createPetVetDirectPaymentClaim(TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParameters()) { result in
            switch result {
                case .success(let receivedPetClaim):
                    XCTAssertEqual(receivedPetClaim, petClaim)
                case .failure(let error):
                    XCTFail("It should receive a valid claim")
                }
                expect.fulfill()
            }
            guard let completion: (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void else {
                XCTFail("Completion should exist")
                expect.fulfill()
                return
            }
            completion(.success(remotePetClaim))
            wait(for: [expect], timeout: 2)
    }

    func test_createPetVetDirectPaymentClaim_WhenUserSetsNewVetEmailThatDoNotExistOnDatabaseAndAPISucceeds_ShouldReturnNewPetClaim() {
        let (network, repository) = makeSUT()
        let remotePetClaim = TestPetClaimFactory.makeRemotePetClaim()
        let petClaim = TestPetClaimFactory.makePetClaim()
        let expect = XCTestExpectation(description: "test expectation")

        repository.createPetVetDirectPaymentClaim(TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParameters(veterinarianId: nil)) { result in
            switch result {
                case .success(let receivedPetClaim):
                    XCTAssertEqual(receivedPetClaim, petClaim)
                case .failure(let error):
                    XCTFail("It should receive a valid claim")
                }
                expect.fulfill()
            }
            guard let completion: (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void else {
                XCTFail("Completion should exist")
                expect.fulfill()
                return
            }
            completion(.success(remotePetClaim))
            wait(for: [expect], timeout: 2)
    }

    func test_createPetVetDirectPaymentClaim_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .serverError, errorMessage: "error message")

        repository.createPetVetDirectPaymentClaim(TestPetVetDirectPaymentParametersFactory.makePetVetDirectPaymentParameters()) { result in
                switch result {
                case .success:
                    XCTFail("It should fail")
                case .failure(let error):
                    XCTAssertEqual(error.errorType, expectedError.errorType)
                    XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
                }
                expect.fulfill()
            }
            guard let completion: (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemotePetClaim, NetworkRequestError>) -> Void else {
                XCTFail("Completion should exist")
                expect.fulfill()
                return
            }
            completion(.failure(.init(statusCode: 500, error: "error message", data: nil, isTokenError: nil)))
            wait(for: [expect], timeout: 3)
    }

    func test_createPetVetDirectPaymentClaim_WhenObjectIsWithInvalidData_ShouldReturnErrorInvalidRequest() {
        let (_, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .invalidRequest, errorMessage: "Invalid Request")
        repository.createPetVetDirectPaymentClaim(PetVetDirectPaymentParameters(petId: 99,
                                                                                type: .Accident,
                                                                                invoiceDate: Date(),
                                                                                amount: nil,
                                                                                veterinarianId: 10,
                                                                                pledgeOfHonor: "pledge",
                                                                                pledgeOfHonorExtension: "pledge-extension")) { result in
                switch result {
                case .success:
                    XCTFail("It should fail")
                case .failure(let error):
                    XCTAssertEqual(error.errorType, expectedError.errorType)
                    XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
                }
                expect.fulfill()
            }
            wait(for: [expect], timeout: 3)
    }
    
    func testGetFormattedSuccessfully() {
        let dateString = "10/07/2023"
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/dd/yyyy"
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")
        let date = dateFormatter.date(from: dateString)
        let dateFormatted = date?.getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: TimeZone(abbreviation: "UTC")!)
        XCTAssertEqual(dateFormatted, "2023-10-07T00:00:00")
    }

    // MARK: - Helper methods
    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, PetClaimRepository) {
        let network = NetworkMock()
        let petRepository: PetClaimRepository = PetClaimRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(petRepository, file: file, line: line)
        return (network, petRepository)
    }
}
