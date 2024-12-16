import XCTest
import KanguroPetData
import KanguroSharedDomain
import KanguroNetworkDomain
import KanguroPetDomain

final class PetRepositoryTests: XCTestCase {

    func testInitShouldNotCallNetwork() {
        let (network, _) = makeSUT()
        XCTAssertEqual(network.completions.count, 0)
    }

    func testGetPetSuccessfully() {
        let (network, repository) = makeSUT()
        let remotePet = TestPetFactory.makeRemotePet()
        let pet = TestPetFactory.makePet()
        let expect = XCTestExpectation(description: "test expectation")
        repository.getPet(GetPetParameters(id: 1)) { result in
            switch result {
            case .success(let receivedPet):
                XCTAssertEqual(receivedPet, pet)
            case .failure(let error):
                XCTFail("It should receive a valid pet")
            }
            expect.fulfill()
        }
        guard let completion: (RequestResponse<RemotePet, NetworkRequestError>) -> Void = network.completions.last as? (RequestResponse<RemotePet, NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success(remotePet))
        wait(for: [expect], timeout: 2)
    }

    func testGetPetsSuccessfully() {
        let (network, repository) = makeSUT()
        let remotePet = TestPetFactory.makeRemotePet(id: 1)
        let remotePet2 = TestPetFactory.makeRemotePet(id: 2)
        let pet = TestPetFactory.makePet(id: 1)
        let pet2 = TestPetFactory.makePet(id: 2)
        let expect = XCTestExpectation(description: "test expectation")
        repository.getPets { result in
            switch result {
            case .success(let receivedPets):
                XCTAssertEqual(receivedPets, [pet, pet2])
            case .failure(let error):
                XCTFail("It should receive valid pets")
            }
            expect.fulfill()
        }
        guard let completion: (KanguroNetworkDomain.RequestResponse<[RemotePet], NetworkRequestError>) -> Void = network.completions.last as? (KanguroNetworkDomain.RequestResponse<[RemotePet], NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success([remotePet, remotePet2]))
        wait(for: [expect], timeout: 2)
    }

    func testUpdatePetPictureSuccessfully() {
        let (network, repository) = makeSUT()
        let expect = XCTestExpectation(description: "test expectation")
        let parameters = UpdatePetPictureParameters(petId: 1, petPictureBase64: PetPictureBase64(fileInBase64: "iVBORw0KGgoAAAANSUhEUgAAABgAAAAYCAYAAADgdz34AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAAApgAAAKYB3X3/OAAAABl0RVh0U29mdHdhcmUAd3d3Lmlua3NjYXBlLm9yZ5vuPBoAAANCSURBVEiJtZZPbBtFFMZ/M7ubXdtdb1xSFyeilBapySVU8h8OoFaooFSqiihIVIpQBKci6KEg9Q6H9kovIHoCIVQJJCKE1ENFjnAgcaSGC6rEnxBwA04Tx43t2FnvDAfjkNibxgHxnWb2e/u992bee7tCa00YFsffekFY+nUzFtjW0LrvjRXrCDIAaPLlW0nHL0SsZtVoaF98mLrx3pdhOqLtYPHChahZcYYO7KvPFxvRl5XPp1sN3adWiD1ZAqD6XYK1b/dvE5IWryTt2udLFedwc1+9kLp+vbbpoDh+6TklxBeAi9TL0taeWpdmZzQDry0AcO+jQ12RyohqqoYoo8RDwJrU+qXkjWtfi8Xxt58BdQuwQs9qC/afLwCw8tnQbqYAPsgxE1S6F3EAIXux2oQFKm0ihMsOF71dHYx+f3NND68ghCu1YIoePPQN1pGRABkJ6Bus96CutRZMydTl+TvuiRW1m3n0eDl0vRPcEysqdXn+jsQPsrHMquGeXEaY4Yk4wxWcY5V/9scqOMOVUFthatyTy8QyqwZ+kDURKoMWxNKr2EeqVKcTNOajqKoBgOE28U4tdQl5p5bwCw7BWquaZSzAPlwjlithJtp3pTImSqQRrb2Z8PHGigD4RZuNX6JYj6wj7O4TFLbCO/Mn/m8R+h6rYSUb3ekokRY6f/YukArN979jcW+V/S8g0eT/N3VN3kTqWbQ428m9/8k0P/1aIhF36PccEl6EhOcAUCrXKZXXWS3XKd2vc/TRBG9O5ELC17MmWubD2nKhUKZa26Ba2+D3P+4/MNCFwg59oWVeYhkzgN/JDR8deKBoD7Y+ljEjGZ0sosXVTvbc6RHirr2reNy1OXd6pJsQ+gqjk8VWFYmHrwBzW/n+uMPFiRwHB2I7ih8ciHFxIkd/3Omk5tCDV1t+2nNu5sxxpDFNx+huNhVT3/zMDz8usXC3ddaHBj1GHj/As08fwTS7Kt1HBTmyN29vdwAw+/wbwLVOJ3uAD1wi/dUH7Qei66PfyuRj4Ik9is+hglfbkbfR3cnZm7chlUWLdwmprtCohX4HUtlOcQjLYCu+fzGJH2QRKvP3UNz8bWk1qMxjGTOMThZ3kvgLI5AzFfo379UAAAAASUVORK5CYII=", fileExtension: ".jpg"))
        repository.updatePetPicture(parameters) { result in
            switch result {
            case .success: break
            case .failure(let error):
                XCTFail("It should succeed")
            }
            expect.fulfill()
        }
        guard let completion: (
            RequestEmptyResponse<NetworkRequestError>) -> Void = network.completions.last as? (RequestEmptyResponse<NetworkRequestError>) -> Void else {
            XCTFail("Completion should exist")
            expect.fulfill()
            return
        }
        completion(.success)
        wait(for: [expect], timeout: 2)
    }


    // MARK: - Helper methods

    func makeSUT(
        file: StaticString = #filePath,
        line: UInt = #line
    ) -> (NetworkMock, PetRepository) {
        let network = NetworkMock()
        let petRepository: PetRepository = PetRepository(network: network)
        trackForMemoryLeaks(network, file: file, line: line)
        trackForMemoryLeaks(petRepository, file: file, line: line)
        return (network, petRepository)
    }

 

}
