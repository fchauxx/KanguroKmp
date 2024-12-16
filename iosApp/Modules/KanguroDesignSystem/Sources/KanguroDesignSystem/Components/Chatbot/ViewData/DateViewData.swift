import Foundation

public class DateViewData: ObservableObject {
    public var selected: Date

    public init(selected: Date = Date()) {
        self.selected = selected
    }
}
