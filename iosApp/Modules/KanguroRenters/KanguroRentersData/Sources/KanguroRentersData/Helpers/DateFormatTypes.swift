import Foundation

enum DateFormatTypes: String, CaseIterable {

    case isoDateTimeMilliSec
    case birthDateFormat
    case regular
    case localDate
    case isoDate

    // MARK: - Computed Properties
    var description : String {

        switch self {
        case .isoDateTimeMilliSec: return "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ"
        case .birthDateFormat: return "yyyy-MM-dd'T'HH:mm:ss"
        case .regular: return "yyyy'-'MM'-'dd'T'HH':'mm':'ssZZZ"
        case .localDate: return "yyyy-MM-dd"
        case .isoDate: return "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        }
    }
}
