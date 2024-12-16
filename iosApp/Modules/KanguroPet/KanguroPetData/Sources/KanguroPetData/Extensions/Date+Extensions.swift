import Foundation

extension Date {
    
    public var UTCFormat: String? {
        guard let utcTimezone = TimeZone(abbreviation: "UTC") else { return nil }
        return getFormatted(format: "yyyy-MM-dd'T'HH:mm:ss", timezone: utcTimezone)
    }
    
    public func getFormatted(format: String, timezone: TimeZone = .current) -> String {
        let formatter = DateFormatter.formatter
        formatter.timeZone = timezone
        formatter.dateFormat = format
        return formatter.string(from: self)
    }
}
