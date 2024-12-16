import XCTest
import KanguroRentersData
import KanguroRentersDomain
import KanguroNetworkDomain
import KanguroSharedDomain
import KanguroSharedData

@testable import KanguroRentersData

final class RentersScheduledItemTests: XCTestCase {
    
    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }
    
    func test_createScheduledItem_WhenAPISucceeds_ShouldReturnListOfScheduledItems() {
        let (network, repository) = makeSUT()
        let remoteScheduledItem1 = TestScheduledItemFactory.makeRemoteScheduledItem(id: "id")
        let scheduledItem1 = TestScheduledItemFactory.makeScheduledItem(id: "id")
        let expect = XCTestExpectation(description: "test expectation")
        repository.createScheduledItem(item: ScheduledItemParameters(name: "Playstation 9",
                                                                     type: ScheduledItemCategory(category: .Electronics, label: "Playstation 9"),
                                                                     valuation: 4000),
                                       policyId: "id") { result in
            guard let _ = try? result.get() else {
                XCTFail("Could not create Scheduled Item")
                expect.fulfill()
                return
            }
            switch result {
            case .success(let createdScheduledItem):
                XCTAssertEqual(createdScheduledItem.name, scheduledItem1.name)
                XCTAssertEqual(createdScheduledItem.category?.category, scheduledItem1.category?.category)
            case .failure(let error):
                XCTFail("It should receive valid Scheduled Items")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteScheduledItem, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteScheduledItem, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteScheduledItem1))
        wait(for: [expect], timeout: 3)
    }
    
    func test_createScheduledItem_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .serverError, errorMessage: "error message")
        repository.createScheduledItem(item: ScheduledItemParameters(name: "Playstation 9",
                                                                     type: ScheduledItemCategory(category: .Electronics, label: "Playstation 9"),
                                                                     valuation: 4000),
                                       policyId: "id") { result in
            switch result {
            case .success:
                XCTFail("It should fail")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemoteScheduledItem, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemoteScheduledItem, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.failure(.init(statusCode: 500, error: "error message", data: nil, isTokenError: nil)))
        wait(for: [expect], timeout: 3)
    }
    
    func test_getScheduledItems_WhenAPISucceeds_ShouldReturnListOfScheduledItems() {
        let (network, repository) = makeSUT()
        let remoteScheduledItem1 = TestScheduledItemFactory.makeRemoteScheduledItem(id: "id")
        let remoteScheduledItem2 = TestScheduledItemFactory.makeRemoteScheduledItem(id: "id2")
        let scheduledItem1 = TestScheduledItemFactory.makeScheduledItem(id: "id")
        let scheduledItem2 = TestScheduledItemFactory.makeScheduledItem(id: "id2")
        let expect = XCTestExpectation(description: "test expectation")
        repository.getScheduledItems(policyId: "policyId") { result in
            switch result {
            case .success(let receivedScheduledItems):
                XCTAssertEqual(receivedScheduledItems, [scheduledItem1, scheduledItem2])
            case .failure(let error):
                XCTFail("It should receive valid Scheduled Items")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<[RemoteScheduledItem], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteScheduledItem], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remoteScheduledItem1, remoteScheduledItem2]))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getScheduledItems_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")
        
        repository.getScheduledItems(policyId: "policyId") { result in
            switch result {
            case .success:
                XCTFail("It should fail")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<[RemoteScheduledItem], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteScheduledItem], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.failure(.init(statusCode: 401, error: "error message", data: nil, isTokenError: nil)))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getScheduledItemsCategories_WhenAPISucceeds_ShouldReturnListOfCategories() {
        let (network, repository) = makeSUT()
        let remoteScheduledItemCategory1 = TestScheduledItemCategoriesFactory.makeRemoteScheduledItemCategory(id: "id1", label: "label1")
        let remoteScheduledItemCategory2 = TestScheduledItemCategoriesFactory.makeRemoteScheduledItemCategory(id: "id2", label: "label2")
        let scheduledItemCategory1 = TestScheduledItemCategoriesFactory.makeScheduledItemCategory(id: "id1", label: "label1")
        let scheduledItemCategory2 = TestScheduledItemCategoriesFactory.makeScheduledItemCategory(id: "id2", label: "label2")
        let expect = XCTestExpectation(description: "test expectation")
        repository.getScheduledItemsCategories { result in
            switch result {
            case .success(let receivedCategories):
                XCTAssertEqual(receivedCategories[0].category, scheduledItemCategory1.category)
                XCTAssertEqual(receivedCategories[1].category, scheduledItemCategory2.category)
                XCTAssertEqual(receivedCategories[0].label, scheduledItemCategory1.label)
                XCTAssertEqual(receivedCategories[1].label, scheduledItemCategory2.label)
            case .failure(let error):
                XCTFail("It should receive valid Categories")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<[RemoteScheduledItemCategory], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteScheduledItemCategory], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remoteScheduledItemCategory1, remoteScheduledItemCategory2]))
        wait(for: [expect], timeout: 2)
    }
    
    func test_getScheduledItemsCategories_WhenAPIFails_ShouldReturnErrorWithReason() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let expectedError = RequestError(errorType: .notAllowed, errorMessage: "error message")
        
        repository.getScheduledItemsCategories { result in
            switch result {
            case .success:
                XCTFail("It should fail")
            case .failure(let error):
                XCTAssertEqual(error.errorType, expectedError.errorType)
                XCTAssertEqual(error.errorMessage, expectedError.errorMessage)
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<[RemoteScheduledItemCategory], NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<[RemoteScheduledItemCategory], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.failure(.init(statusCode: 401, error: "error message", data: nil, isTokenError: nil)))
        wait(for: [expect], timeout: 2)
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
