import Foundation

public struct RemotePayment: Codable {

    public var totalFees: Double?
    public var totalPayment: Double?
    public var invoiceInterval: RemoteInvoiceType?
    public var totalDiscounts: Double?
    public var totalPaymentWithoutFees: Double?
    public var firstPayment: Double?
    public var recurringPayment: Double?

    public init(totalFees: Double? = nil,
                totalPayment: Double? = nil,
                invoiceInterval: RemoteInvoiceType? = nil,
                totalDiscounts: Double? = nil,
                totalPaymentWithoutFees: Double? = nil,
                firstPayment: Double? = nil,
                recurringPayment: Double? = nil) {
        self.totalFees = totalFees
        self.totalPayment = totalPayment
        self.invoiceInterval = invoiceInterval
        self.totalDiscounts = totalDiscounts
        self.totalPaymentWithoutFees = totalPaymentWithoutFees
        self.firstPayment = firstPayment
        self.recurringPayment = recurringPayment
    }
}
