import Foundation

enum DateFormatTypes: String, CaseIterable {
    
    case isoDateTimeMilliSec
    case birthDateFormat
    case regular
    case localDate
    case secondaryLocalDate
    case isoDate
    
    // MARK: - Computed Properties
    var description : String {
        
        switch self {
        case .isoDateTimeMilliSec: return "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ"
        case .birthDateFormat: return "yyyy-MM-dd'T'HH:mm:ss"
        case .regular: return "yyyy'-'MM'-'dd'T'HH':'mm':'ssZZZ"
        case .localDate: return "yyyy-MM-dd"
        case .secondaryLocalDate: return "MM/dd/yyyy"
        case .isoDate: return "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        }
    }
}

// MARK: - Computed Properties
extension Date {
    
    var day: Int { return self.get(.day) }
    var month: Int { return self.get(.month) }
    var year: Int { return self.get(.year) }
    var monthName: String { return DateFormatter.formatter.monthSymbols[self.get(.month) - 1] }
    var fullNamedDate: String { return getFormatted(format: "EEEE dd MMM") }
    var USADate: String { return getFormatted(format: "MMM dd, yyyy") }
    var USADate_UTC: String { return getFormatted(format: "MMM dd, yyyy", timezone: TimeZone(abbreviation: "UTC")!) }
    var MMddyyy: String { return getFormatted(format: "MM/dd/yyyy") }
    var MMddyyy_UTC: String { return getFormatted(format: "MM/dd/yyyy", timezone: TimeZone(abbreviation: "UTC")!) }
    var ddyy: String { return getFormatted(format: "dd, yy") }
    
    var isoString: String {
        let formatter = DateFormatter.formatter
        formatter.locale = Locale(identifier: "en_US")
        formatter.timeZone = TimeZone(secondsFromGMT: 0)
        formatter.dateFormat = "yyyy-MM-dd'T'HH:mm:ss"
        return formatter.string(from: self)
    }
    var ageText: String {
        let yearsAge = self.yearsBetweenDate(Date())
        let monthsAge = self.monthsBetweenDate(Date())
        
        //TODO: Update using .stringdict
        let yearText = (yearsAge == 1) ? "commom.year".localized : "commom.years".localized
        let monthText = (monthsAge == 1) ? "commom.month".localized : "commom.months".localized
        
        let yearsText = "\(yearsAge) " + yearText
        let monthsText = "\(monthsAge) " + monthText
        
        return (yearsAge > 0) ? yearsText : monthsText
    }
}

// MARK: - Private Methods
extension Date {
    
    private func get(_ components: Calendar.Component..., calendar: Calendar = Calendar.current) -> DateComponents {
        return calendar.dateComponents(Set(components), from: self)
    }
    
    private func get(_ component: Calendar.Component, calendar: Calendar = Calendar.current) -> Int {
        return calendar.component(component, from: self)
    }
    
    func dayBetweenDate(_ secondDate: Date) -> Int {
        let calendar = Calendar.current
        let currentDateDay = calendar.startOfDay(for: self)
        let secondDateDay = calendar.startOfDay(for: secondDate)
        return calendar.dateComponents([.day], from: secondDateDay, to: currentDateDay).day ?? 0
    }
    
    func monthsBetweenDate(_ secondDate: Date) -> Int {
        let calendar = Calendar.current
        let currentDateDay = calendar.startOfDay(for: self)
        let secondDateDay = calendar.startOfDay(for: secondDate)
        return calendar.dateComponents([.month], from: currentDateDay, to: secondDateDay).month ?? 0
    }
        
    func yearsBetweenDate(_ secondDate: Date) -> Int {
        let calendar = Calendar.current
        let currentDateDay = calendar.startOfDay(for: self)
        let secondDateDay = calendar.startOfDay(for: secondDate)
        return calendar.dateComponents([.year], from: currentDateDay, to: secondDateDay).year ?? 0
    }
}

// MARK: - Formatted
extension Date {
    
    func getFormatted(format: String, timezone: TimeZone = .current) -> String {
        let formatter = DateFormatter.formatter
        formatter.timeZone = timezone
        formatter.dateFormat = format
        return formatter.string(from: self)
    }
}

