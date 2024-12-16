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
