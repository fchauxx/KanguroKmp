import Foundation

struct ProfileData {
    
    var firstName: String?
    var lastName: String?
    var email: String?
    var phoneNumber: String?
    
    var fullName: String {
        return (firstName ?? "") + (lastName ?? "")
    }
}
