import Foundation
import KanguroSharedDomain

public final class GetVeterinarians: GetVeterinariansUseCaseProtocol {

    private let vetRepo: VeterinariansRepositoryProtocol

    public init(vetRepo: VeterinariansRepositoryProtocol) {
        self.vetRepo = vetRepo
    }

    public func execute(
        completion: @escaping ((Result<[Veterinarian], RequestError>) -> Void)
    ) {
        vetRepo.getVeterinarians { result in
            completion(result)
        }
    }
}
