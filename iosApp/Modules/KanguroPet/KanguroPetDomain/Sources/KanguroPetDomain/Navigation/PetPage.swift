import SwiftUI

public enum PetPage: Identifiable, Hashable {
    
    case petUpselling
    case getQuote
    case airvetInstruction

    // MARK: - Computed Properties
    public var id: String {
        switch self {
        case .getQuote:
            return "getQuote"
        case .petUpselling:
            return "petUpselling"
        case .airvetInstruction:
            return "airvetInstruction"
        }
    }
    private var canGoBackList: [PetPage] {
        []
    }
}
