import Foundation
import XCTest
import KanguroSharedData
import KanguroSharedDomain
import KanguroNetworkDomain

final class PolicyRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func testGetPolicySuccessfully() {
        let (network, repository) = makeSUT()
        let remotePolicy = TestPolicyFactory.makeRemotePolicy()
        let policy = TestPolicyFactory.makePolicy()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getPolicy(PolicyParameters(id: "MyPolicy")) { result in
            switch result {
            case .success(let receivedPolicy):
                XCTAssertEqual(receivedPolicy, policy)
            case .failure(let error):
                XCTFail("It should receive a valid policy")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<RemotePolicy, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<RemotePolicy, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remotePolicy))
        wait(for: [expect], timeout: 2)
    }

    func testGetPoliciesSuccessfully() {
        let (network, repository) = makeSUT()
        let remotePolicy = TestPolicyFactory.makeRemotePolicy(id: "1")
        let remotePolicy2 = TestPolicyFactory.makeRemotePolicy(id: "2")
        let policy = TestPolicyFactory.makePolicy(id: "1")
        let policy2 = TestPolicyFactory.makePolicy(id: "2")
        let expect = XCTestExpectation(description: "test expectation")
        repository.getPolicies { result in
            switch result {
            case .success(let receivedPolicies):
                XCTAssertEqual(receivedPolicies, [policy, policy2])
            case .failure(let error):
                XCTFail("It should receive valid policies")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemotePolicy], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemotePolicy], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remotePolicy, remotePolicy2]))
        wait(for: [expect], timeout: 2)
    }

    func testGetPolicyDocumentsSuccessfully() {
        let (network, repository) = makeSUT()
        let remoteDocument = TestPolicyDocumentDataFactory.makeRemotePolicyDocumentData(id: 1)
        let remoteDocument2 = TestPolicyDocumentDataFactory.makeRemotePolicyDocumentData(id: 2)
        let document = TestPolicyDocumentDataFactory.makePolicyDocumentData(id: 1)
        let document2 = TestPolicyDocumentDataFactory.makePolicyDocumentData(id: 2)
        let expect = XCTestExpectation(description: "test expectation")
        repository.getPolicyDocuments(PolicyParameters(id: "myDocuments")) { result in
            switch result {
            case .success(let receivedDocuments):
                XCTAssertEqual(receivedDocuments, [document, document2])
            case .failure(let error):
                XCTFail("It should receive valid documents")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemotePolicyDocumentData], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemotePolicyDocumentData], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remoteDocument, remoteDocument2]))
        wait(for: [expect], timeout: 2)
    }

    func testGetCoverages() {
        let (network, repository) = makeSUT()
        let remoteCoverage = TestCoverageDataFactory.makeRemoteCoverageData(name: "1")
        let remoteCoverage2 = TestCoverageDataFactory.makeRemoteCoverageData(name: "2")
        let coverage = TestCoverageDataFactory.makeCoverageData(name: "1")
        let coverage2 = TestCoverageDataFactory.makeCoverageData(name: "2")
        let expect = XCTestExpectation(description: "test expectation")
        repository.getCoverages(PolicyCoverageParameters(id: "1", reimbursement: Double(1))) { result in
            switch result {
            case .success(let receivedCoverages):
                XCTAssertEqual(receivedCoverages, [coverage, coverage2])
            case .failure(let error):
                XCTFail("It should receive valid coverages")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemoteCoverageData], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemoteCoverageData], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remoteCoverage, remoteCoverage2]))
        wait(for: [expect], timeout: 2)
    }

    func testGetPolicyAttachment() {
        let (network, repository) = makeSUT()
        let remoteAttachment = anyData()
        let attachment = anyData()
        let expect = XCTestExpectation(description: "test expectation")
        let parameters = PolicyAttachmentParameters(policyId: "1", attachment: PolicyAttachment(id: 1, name: "name", fileSize: 100))
        repository.getPolicyAttachment(parameters) { result in
            switch result {
            case .success(let receivedData):
                XCTAssertEqual(receivedData, attachment)
            case .failure(let error):
                XCTFail("It should receive a valid attachment")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteAttachment))
        wait(for: [expect], timeout: 2)
    }

    func testGetPolicyDocument() {
        let (network, repository) = makeSUT()
        let remoteDocument = anyData()
        let document = anyData()
        let expect = XCTestExpectation(description: "test expectation")
        let parameters = PolicyDocumentParameters(policyId: "1", documentId: 1, filename: "name")
        repository.getPolicyDocument(parameters) { result in
            switch result {
            case .success(let receivedData):
                print(receivedData)
                XCTAssertEqual(receivedData, document)
            case .failure(let error):
                XCTFail("It should receive a valid document")
            }
            expect.fulfill()
        }

        guard let completion: (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<Data, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remoteDocument))
        wait(for: [expect], timeout: 2)
    }


    // MARK: - Helper methods

    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, PolicyRepository) {
        let network = NetworkMock()
        let policyRepository: PolicyRepository = PolicyRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(policyRepository, file: file, line: line)
        return (network, policyRepository)
    }

    
}
