import Foundation

public struct RemotePersonalProperty: Codable {
    
    let minimum: Double
    let maximum: Double
    
    public init(minimum: Double, maximum: Double) {
        self.minimum = minimum
        self.maximum = maximum
    }
}
