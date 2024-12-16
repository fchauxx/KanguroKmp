import SwiftUI
import KanguroSharedDomain

@available(iOS 16, *)
public struct ChatDatePicker: View {

    // MARK: - Wrapped Properties
    @EnvironmentObject var date: DateViewData
    @State var calendarId: UUID = UUID()

    // MARK: - Stored Properties
    private let fontColor: Color
    private let backgroundColor: Color
    private let datePickerRange: DatePickerRange
    @Environment(\.appLanguageValue) var language

    var lang: String {
        language.rawValue
    }

    // MARK: - Computed Properties
    private var dateProxy: Binding<Date> {
        Binding<Date>(
            get: { self.date.selected },
            set: {
                self.date.selected = $0
                calendarId = UUID()
            }
        )
    }

    // MARK: - Actions
    var didSelectedDate: StringClosure

    // MARK: - Initializers
    public init(
        fontColor: Color,
        backgroundColor: Color,
        datePickerRange: DatePickerRange,
        didSelectedDate: @escaping StringClosure
    ) {
        self.backgroundColor = backgroundColor
        self.datePickerRange = datePickerRange
        self.fontColor = fontColor
        self.didSelectedDate = didSelectedDate
    }

    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack {
                switch datePickerRange {
                case .anyDate:
                    DatePicker(
                        "chatbot.date.selection".localized(lang),
                        selection: dateProxy,
                        displayedComponents: [.date]
                    )
                    .id(calendarId)
                    .padding([.vertical],InsetSpacing.xs)
                    .padding([.horizontal], InsetSpacing.md)
                    .font(.lato(.latoRegular, size: 18))
                    .foregroundColor(fontColor)
                    .datePickerStyle(.compact)
                    .accessibilityAddTraits([.isButton])
                    .accessibilityValue(date.selected.accessibleText)
                    .accessibilityElement(children: .combine)

                case .pastDate:
                    DatePicker(
                        "chatbot.date.selection".localized(lang),
                        selection: dateProxy,
                        in: ...Date(),
                        displayedComponents: [.date]
                    )
                    .id(calendarId)
                    .padding([.vertical],InsetSpacing.xs)
                    .padding([.horizontal], InsetSpacing.md)
                    .font(.lato(.latoRegular, size: 18))
                    .foregroundColor(fontColor)
                    .datePickerStyle(.compact)
                    .accessibilityAddTraits([.isButton])
                    .accessibilityValue(date.selected.accessibleText)
                    .accessibilityElement(children: .combine)
                case .futureDate:
                    DatePicker(
                        "chatbot.date.selection".localized(lang),
                        selection: dateProxy,
                        in: Date()...,
                        displayedComponents: [.date]
                    )
                    .id(calendarId)
                    .padding([.vertical],InsetSpacing.xs)
                    .padding([.horizontal], InsetSpacing.md)
                    .font(.lato(.latoRegular, size: 18))
                    .foregroundColor(fontColor)
                    .datePickerStyle(.compact)
                    .accessibilityAddTraits([.isButton])
                    .accessibilityValue(date.selected.accessibleText)
                    .accessibilityElement(children: .combine)
                }
                Spacer()
                Button {
                    let date = self.date.selected.getFormatted(format: "yyyy-MM-dd", timezone: TimeZone(identifier: "UTC")!)
                    didSelectedDate(date)
                } label: {
                    Image.sendIcon
                        .resizable()
                        .frame(width: IconSize.sm,
                               height: IconSize.sm)
                        .padding(InsetSpacing.nano)
                }
            }
        }
        .background(backgroundColor)
    }
}

// MARK: - Preview
@available(iOS 16, *)
struct ChatDatePicker_Previews: PreviewProvider {

    static var previews: some View {

        ChatDatePicker(
            fontColor: .secondaryDarkest,
            backgroundColor: .neutralBackground,
            datePickerRange: .anyDate, didSelectedDate: {_ in }).environmentObject(DateViewData())
            .previewDevice(.init(rawValue: "iPhone 8"))
            .previewDisplayName("iPhone 8")

        ChatDatePicker(
            fontColor: .secondaryDarkest,
            backgroundColor: .neutralBackground,
            datePickerRange: .anyDate, didSelectedDate: {_ in }).environmentObject(DateViewData())
            .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
            .previewDisplayName("iPhone 14 Pro Max")

        ChatDatePicker(
            fontColor: .secondaryDarkest,
            backgroundColor: .neutralBackground,
            datePickerRange: .anyDate, didSelectedDate: {_ in }).environmentObject(DateViewData())
            .previewDevice(.init(rawValue: "iPhone 14 Pro Max"))
            .previewDisplayName("iPhone 14 Pro Max")
    }
}
