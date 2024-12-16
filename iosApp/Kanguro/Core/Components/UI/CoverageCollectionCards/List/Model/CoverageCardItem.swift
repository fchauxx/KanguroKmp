import KanguroPetDomain
import Foundation

struct CoverageCardItem {
    let uuid = UUID()

    let policy: PetPolicy?
    let shouldShowAirVetCard: Bool

    init(policy: PetPolicy? = nil, shouldShowAirVetCard: Bool = false) {
        self.policy = policy
        self.shouldShowAirVetCard = shouldShowAirVetCard
    }
}

extension CoverageCardItem: Hashable {
    static func ==(lhs: CoverageCardItem, rhs: CoverageCardItem) -> Bool {
        return lhs.uuid == rhs.uuid
    }

    func hash(into hasher: inout Hasher) {
        hasher.combine(uuid)
    }
}
