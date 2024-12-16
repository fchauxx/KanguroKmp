import Foundation

extension String {
    var date: Date? {
        var date: Date?
        let types: [DateFormatTypes] = DateFormatTypes.allCases
        let dateFormatter = DateFormatter.formatter
        dateFormatter.timeZone = TimeZone(abbreviation: "UTC")

        for type in types {
            dateFormatter.dateFormat = type.description
            date = dateFormatter.date(from: self)
            if let date {
                return date
            }
        }
        return nil
    }
}

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

extension DateFormatter {

    static let formatter = DateFormatter()
}
