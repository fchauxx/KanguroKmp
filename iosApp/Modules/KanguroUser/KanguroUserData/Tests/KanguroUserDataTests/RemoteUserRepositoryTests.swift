import XCTest
import KanguroNetworkData
import KanguroUserData
import KanguroUserDomain
import KanguroSharedDomain
import KanguroNetworkDomain

final class RemoteUserRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func testLoginShouldReturnAnUser() {
        let (network, remoteUserRepository) = makeSUT()
        let remoteUser = TestUserFactory.makeRemote(id: "PEDRO")
        let expectedUser = TestUserFactory.make(id: "PEDRO")
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.login(parameters: UserLoginParameters(email: "joe@joe.com", password: "1234")) { result in
            guard let user = try? result.get() else {
                XCTFail("Could not return a valid user")
                expect.fulfill()
                return
            }
            XCTAssertEqual(user, expectedUser)
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteUser, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteUser, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteUser))
        wait(for: [expect], timeout: 3)
    }

    func testRefreshTokenShouldReturnValidToken() {
        let (network, remoteUserRepository) = makeSUT()
        let remoteToken = TestTokenFactory.makeRemote()
        let expectedToken = TestTokenFactory.make()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.updateRefreshToken(RefreshTokenParameters(refreshToken: "1234")) { result in
            guard let token = try? result.get() else {
                XCTFail("Could not return a valid token")
                expect.fulfill()
                return
            }
            XCTAssertEqual(token, expectedToken)
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteToken, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteToken, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteToken))
        wait(for: [expect], timeout: 3)
    }

    func testAskToDeleteAccountSuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.createAccountDeletionOrder(true) { result in
            guard let _ = try? result.get() else {
                XCTFail("Request should not fail")
                expect.fulfill()
                return
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 3)
    }

    func testGetBankAccountSuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let remoteBankAccount = TestBankAccountFactory.makeRemote()
        let expectedBankAccount = TestBankAccountFactory.make()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.getBankAccount { result in
            guard let bankAccount = try? result.get() else {
                XCTFail("Could not return a valid bank Account")
                expect.fulfill()
                return
            }
            XCTAssertEqual(bankAccount, expectedBankAccount)
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteBankAccount, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteBankAccount, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteBankAccount))
        wait(for: [expect], timeout: 3)
    }

    func testUpdateBankAccountSuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let expectedBankAccount = TestBankAccountFactory.make()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.updateBankAccount(expectedBankAccount) { result in
            guard let _ = try? result.get() else {
                XCTFail("Could not update BankAccount")
                expect.fulfill()
                return
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 3)
    }

    func testUpdateFirebaseTokenSuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.updateFirebaseToken(FirebaseTokenParameters(firebaseToken: "1234", uuid: "5678")) { result in
            guard let _ = try? result.get() else {
                XCTFail("Could not update Firebase Token")
                expect.fulfill()
                return
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 3)
    }

    func testCreateOtpSendRequestSuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.createOtpSendRequest { result in
            guard let _ = try? result.get() else {
                XCTFail("Could not create OTP Send Request")
                expect.fulfill()
                return
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 3)
    }

    func testGetOtpValidationWithTrueResponse() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.getOtpValidation(
            CodeValidationDataParameters(
                email: "john@williams.com",
                code:
                    "SENNA"
            )) { result in
                switch result {
                case .success:
                    break
                case .failure(let error):
                    XCTFail("It should succeed")
                }
                expect.fulfill()
        }
        guard let completion: (RequestResponse<Bool, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<Bool, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(true))
        wait(for: [expect], timeout: 3)
    }

    func testGetOtpValidationWithFalseResponse() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.getOtpValidation(
            CodeValidationDataParameters(
                email: "john@williams.com",
                code:
                    "SENNA"
            )) { result in
                switch result {
                case .success:
                    XCTFail("False response should failure")
                case .failure(let error):
                    break
                }
                expect.fulfill()
        }
        guard let completion: (RequestResponse<Bool, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<Bool, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(false))
        wait(for: [expect], timeout: 3)
    }

    func testGetIsUserAccessOkWithTrueForIsBlockedResponse() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.getIsUserAccessOk(userId: "SENNA") { result in
                switch result {
                case .success:
                    XCTFail("False response should failure")
                case .failure(let error):
                    break
                }
                expect.fulfill()
        }
        guard let completion: (RequestResponse<Bool, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<Bool, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(true))
        wait(for: [expect], timeout: 3)
    }

    func testGetIsUserAccessOkWithFalseForIsBlockedResponse() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.getIsUserAccessOk(userId: "SENNA") { result in
                switch result {
                case .success:
                    break
                case .failure(let error):
                    XCTFail("False response should success")
                }
                expect.fulfill()
        }
        guard let completion: (RequestResponse<Bool, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<Bool, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(false))
        wait(for: [expect], timeout: 3)
    }

    func testPatchCharitySuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.patchCharity(UserDonationCause(userId: "1234", charityId: 5678, title: "SENNA", cause: .Animals)) { result in
            switch result {
            case .success:
                break
            case .failure(let error):
                XCTFail("False response should failure")
            }
            expect.fulfill()
        }
        guard let completion: (RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 3)
    }

    func testGetRemoteUserAsyncSuccessfully() {
        let (network, remoteUserRepository) = makeSUT()
        let remoteUser = TestUserFactory.makeRemote(id: "PEDRO")
        let expectedUser = TestUserFactory.make(id: "PEDRO")
        let expect = XCTestExpectation(description: "test expectation")
        remoteUserRepository.getUser { result in
            switch result {
            case .success(let user):
                XCTAssertEqual(user, expectedUser)
            case .failure(let error):
                XCTFail("Could not get user")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteUser, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteUser, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteUser))
        wait(for: [expect], timeout: 3)
    }

    // MARK: Helper methods

    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, RemoteUserRepository) {
        let network = NetworkMock()
        let remoteUserRepository = RemoteUserRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(remoteUserRepository, file: file, line: line)
        return (network, remoteUserRepository)
    }
}
