import XCTest
import KanguroUserData
import KanguroUserDomain
import KanguroSharedDomain
import KanguroStorageDomain

final class LocalUserRepositoryTests: XCTestCase {

    func testInitShouldBeEmpty() {
        let (storage, _) = makeSUT()
        XCTAssert(storage.persistance.isEmpty)
    }

    func testGetLocalUserAsyncSuccessfully() {
        let (storage, localUserRepo) = makeSUT()
        let codableUser = TestUserFactory.makeRemote(id: "SENNA")
        storage.save(value: codableUser, key: "user")
        let expect = XCTestExpectation(description: "Test expectation")
        localUserRepo.getUser { result in
            guard let user = try? result.get() else {
                XCTFail("Could not retrieve local user")
                return
            }
            XCTAssertEqual(user, TestUserFactory.make(id: "SENNA"))
            expect.fulfill()
        }
        wait(for: [expect], timeout: 3)
    }

    func testGetLocalUserSyncSuccessfully() {
        let (storage, localUserRepo) = makeSUT()
        let codableUser = TestUserFactory.makeRemote(id: "SENNA")
        storage.save(value: codableUser, key: "user")
        let savedUser: User? = try? localUserRepo.getUser().get()
        XCTAssertEqual(savedUser, TestUserFactory.make(id: "SENNA"))
    }

    func testUpdateLocalUserSuccessfully() {
        let expect = XCTestExpectation(description: "Test expectation")
        let (storage, localUserRepo) = makeSUT()
        let codableUser = TestUserFactory.makeRemote(id: "SENNA")
        storage.save(value: codableUser, key: "user")
        var savedUser: User? = try? localUserRepo.getUser().get()
        XCTAssertEqual(savedUser, TestUserFactory.make(id: "SENNA"))
        savedUser?.email = "ayrton.senna@gmail.com"
        localUserRepo.updateUser(savedUser!) { result in
            guard let _ = try? result.get() else {
                XCTFail("Could not update local user")
                expect.fulfill()
                return
            }
            let savedUpdatedUser: User? = try? localUserRepo.getUser().get()
            XCTAssertEqual(savedUser, savedUpdatedUser)
            expect.fulfill()
        }
        wait(for: [expect], timeout: 3)
    }

    // MARK: Helper methods

    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (StorageMock, LocalUserRepository) {
        let storageMock = StorageMock()
        storageMock.cleanAll()
        let localUserRepository = LocalUserRepository(storage: storageMock)
        trackForMemoryLeaks(storageMock, file: file, line: line)
        trackForMemoryLeaks(localUserRepository, file: file, line: line)
        return (storageMock, localUserRepository)
    }
}
