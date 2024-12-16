import Foundation

extension String {
    var getPrefixBeforeDot: String? {
        return self.components(separatedBy: ".").first
    }

    var getFileExtension: String? {
        return self.components(separatedBy: ".").last
    }

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
    
    var apiVersionNumber: Int {
        return Int(self.getPrefixBeforeDot ?? "0") ?? 0
    }
}
