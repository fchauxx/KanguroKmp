import SwiftUI
import KanguroDesignSystem
import KanguroSharedDomain

struct PaymentSectionView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language
    
    // MARK: - Stored Properties
    public var payment: Payment?
    
    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }

    var paymentValueText: String? {
        switch payment?.invoiceInterval {
        case .YEARLY:
            return payment?.firstPayment?.getCurrencyFormatted()
        case .QUARTERLY, .MONTHLY:
            return payment?.recurringPayment?.getCurrencyFormatted()
        case nil:
            return nil
        }
    }

    var paymentLabelText: String {
        switch payment?.invoiceInterval {
        case .YEARLY:
            "renters.policy.payment.yearly.label"
        case .QUARTERLY:
            "renters.policy.payment.quarterly.label"
        case .MONTHLY:
            "renters.policy.payment.monthly.label"
        case nil:
            "renters.policy.payment.monthly.label"
        }
    }

    var totalFeesText: String {
        guard let totalFees = payment?.totalFees else { return "" }
        return totalFees.getCurrencyFormatted()
    }

    // MARK: - Actions
    var didTapBillingPreferencesAction: SimpleClosure

    public init(payment: Payment?,
                didTapBillingPreferencesAction: @escaping SimpleClosure) {
        self.payment = payment
        self.didTapBillingPreferencesAction = didTapBillingPreferencesAction
    }
    
    var body: some View {
        ZStack {
            SectionInformationView(
                headerView: SectionHeaderView(
                    icon: Image.paymentSectionIcon,
                    title: "renters.policy.payment.section.title.label".localized(lang)),
                contentViewList: [
                    SectionContentView(
                        content: AnyView(
                            VStack(alignment: .leading, spacing: StackSpacing.xxxs) {
                                HStack {
                                    Text(paymentLabelText.localized(lang))
                                        .title3SecondaryDarkRegular()
                                    Spacer()
                                    Text(paymentValueText ?? "$ -")
                                        .headlineTertiaryDarkestRegular()
                                }
                            })
                    ),
                    SectionContentView(
                        content:
                            AnyView(ActionCardButton(title: "renters.policy.billingPreferences.action.label".localized(lang),
                                                     icon: Image.creditCardIcon,
                                                     style: .primary,
                                                     didTapAction: {
                                                         didTapBillingPreferencesAction()
                                                     }))
                    )
                    
                ]
            )
        }
    }
}

struct PaymentSection_Previews: PreviewProvider {
    static var previews: some View {
        PaymentSectionView(payment: Payment(totalFees: 75,
                                            totalPayment: 30,
                                            invoiceInterval: .MONTHLY,
                                            totalDiscounts: 20,
                                            totalPaymentWithoutFees: 35,
                                            firstPayment: 40,
                                            recurringPayment: 45), 
                           didTapBillingPreferencesAction: {})
    }
}
