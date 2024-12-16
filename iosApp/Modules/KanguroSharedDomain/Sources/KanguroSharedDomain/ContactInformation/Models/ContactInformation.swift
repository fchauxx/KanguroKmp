import Foundation

public struct ContactInformation: Equatable {

    // MARK: - Stored Properties
    public var type: ContactInformationType
    public var action: String
    public var data: ContactInformationData

    public init(
        type: ContactInformationType,
        action: String,
        data: ContactInformationData
    ) {
        self.type = type
        self.action = action
        self.data = data
    }
}
