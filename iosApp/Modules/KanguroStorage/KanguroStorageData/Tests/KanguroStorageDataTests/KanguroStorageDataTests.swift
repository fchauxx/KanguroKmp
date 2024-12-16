import XCTest
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroStorageData

enum TestError: Error {
    case couldNotMakeSUT
    case notFound
}

final class KanguroStorageImplementationTests: XCTestCase {

    func testGetStringFromCleanedStorage() {
        do {
            let storage = try makeSUT()
            guard let langValue: String = storage.get(key: "preferredLanguage") as String? else {
                return
            }
            print(langValue)
            XCTFail("Should not find langValue")
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testGetBoolFromCleanedStorage() {
        do {
            let storage = try makeSUT()
            guard let didResetKeychain: Bool = storage.get(key: "didResetKeychain") as Bool? else {
                return
            }
            print(didResetKeychain)
            XCTFail("Should not find didResetKeychain")
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testGetIntFromCleanedStorage() {
        do {
            let storage = try makeSUT()
            guard let reviewProcessCompletedCount: Int = storage.get(key: "didResetKeychain") as Int? else {
                return
            }
            print(reviewProcessCompletedCount)
            XCTFail("Should not find reviewProcessCompletedCount")
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testGetCodableFromCleanedStorage() {
        do {
            let storage = try makeSUT()
            guard let person: StorageTestCodable = storage.get(key: "person") as StorageTestCodable? else {
                return
            }
            print(person)
            XCTFail("Should not find person")
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testSaveStringIntoCleanStorage() {
        do {
            let storage = try makeSUT()
            let preferredLanguage = "en"
            storage.save(value: preferredLanguage, key: "preferredLanguage")
            guard let langValue: String = storage.get(key: "preferredLanguage") as String? else {
                throw TestError.notFound
            }
            let language = Language(rawValue: langValue)
            XCTAssertEqual(language, Language.English)
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testSaveIntIntoCleanStorage() {
        do {
            let storage = try makeSUT()
            let reviewProcessCompletedCount = 10
            storage.save(value: reviewProcessCompletedCount, key: "reviewProcessCompletedCount")
            guard let intValue: Int = storage.get(key: "reviewProcessCompletedCount") as Int? else {
                throw TestError.notFound
            }
            XCTAssertEqual(intValue, 10)
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testSaveCodableIntoCleanStorage() {
        do {
            let storage = try makeSUT()
            let codableInput = makeTestCodable()
            storage.save(value: codableInput, key: "codableInput")
            guard let codableValue: StorageTestCodable = storage.get(key:  "codableInput") as StorageTestCodable? else {
                throw TestError.notFound
            }
            XCTAssertEqual(codableValue, codableInput)
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testRemoveFromCleanStorage() {
        do {
            let storage = try makeSUT()
            storage.remove(key: "preferredLanguage")
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testRemoveCodableFromStorage() {
        do {
            let storage = try makeSUT()
            let codableInput = makeTestCodable()
            storage.save(value: codableInput, key: "codableInput")
            guard let codableValue: StorageTestCodable = storage.get(key:  "codableInput") as StorageTestCodable? else {
                throw TestError.notFound
            }
            XCTAssertEqual(codableValue, codableInput)
            storage.remove(key: "codableInput")
            guard let newCodableValue: StorageTestCodable = storage.get(key:  "codableInput") as StorageTestCodable? else {
                return
            }
            print(newCodableValue)
            XCTFail("Codable value should has been removed")
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    func testUpdateIntValue() {
        do {
            let storage = try makeSUT()
            let reviewProcessCompletedCount = 10
            storage.save(value: reviewProcessCompletedCount, key: "reviewProcessCompletedCount")
            guard let intValue: Int = storage.get(key: "reviewProcessCompletedCount") as Int? else {
                throw TestError.notFound
            }
            XCTAssertEqual(intValue, 10)
            storage.save(value: 20, key: "reviewProcessCompletedCount")
            guard let newIntValue: Int = storage.get(key: "reviewProcessCompletedCount") as Int? else {
                throw TestError.notFound
            }
            XCTAssertEqual(newIntValue, 20)
        } catch(let error) {
            XCTFail(error.localizedDescription)
        }
    }

    // MARK: - Helper methods

    func makeSUT() throws -> Storage {

        guard let userdDefaults = UserDefaults(suiteName: "KanguroStorageImplTests") else {
            throw TestError.couldNotMakeSUT
        }
        let storage = StorageImplementation(userdDefaults)
        storage.cleanAll()
        return storage
    }

    func makeTestCodable() -> StorageTestCodable {
        StorageTestCodable(name: "John", gender: .male, address: TestAddress(street: "Rua Direita", number: 10))
    }

    struct StorageTestCodable: Codable, Equatable {
        var name: String
        var gender: TestGender
        var address: TestAddress
    }

    enum TestGender: String, Codable {
        case male
        case female
    }

    struct TestAddress: Codable, Equatable {
        var street: String
        var number: Int
    }
}
