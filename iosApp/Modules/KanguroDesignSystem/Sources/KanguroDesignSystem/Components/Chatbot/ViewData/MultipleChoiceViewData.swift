import Foundation

public struct MultipleChoiceViewData: Identifiable, Equatable {
    public static func == (lhs: MultipleChoiceViewData, rhs: MultipleChoiceViewData) -> Bool {
        lhs.id == rhs.id
    }

    public var id: String { self.choice }
    public var choice: String
    public var icon: ImageIcons?
    public var isDefaultChoice: Bool
    public var action: () -> Void

    public init(choice: String, icon: ImageIcons? = nil, isDefaultChoice: Bool = false, action: @escaping () -> Void) {
        self.choice = choice
        self.icon = icon
        self.isDefaultChoice = isDefaultChoice
        self.action = action
    }
}
