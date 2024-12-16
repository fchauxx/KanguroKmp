import UIKit

enum PreventiveItem: String, Codable {
    
    case WellnessExam
    case HeartwormTest
    case Vaccines
    case FecalorParasiteExam
    case Bloodwork
    case Heartworm
    case RoutineDentalCare
    
    // MARK: - Computed Properties
    var value: String {
        switch self {
        case .WellnessExam:
            return "wellness_exam"
        case .HeartwormTest:
            return "heartworm_test"
        case .Vaccines:
            return "vaccines"
        case .FecalorParasiteExam:
            return "fecal_or_parasite_exam"
        case .Bloodwork:
            return "bloodwork"
        case .Heartworm:
            return "heartworm_flea_tick"
        case .RoutineDentalCare:
            return "routine_dental_care"
        }
    }
}
