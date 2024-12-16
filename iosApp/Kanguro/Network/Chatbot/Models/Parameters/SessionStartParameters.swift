import Foundation

struct SessionStartParameters: Codable {
    
    var petId: Int?
    let type: SessionType
}
