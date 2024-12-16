import Foundation

extension Date {

    var accessibleText: String {
        DateFormatter.localizedString(from: self, dateStyle: .long, timeStyle: .none)
    }

    public func getFormatted(format: String, timezone: TimeZone = .current) -> String {
        let formatter = DateFormatter.formatter
        formatter.timeZone = timezone
        formatter.dateFormat = format
        return formatter.string(from: self)
    }
}
