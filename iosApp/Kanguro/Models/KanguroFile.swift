import Foundation

enum Filename: String, Codable {
    case termOfService = "term_of_service"
    
    var title: String {
        return self.rawValue
    }
}

enum FileExtension: String, Codable {
    case pdf = "pdf"
    case jpg = "jpg"
    
    var fileType: String {
        return self.rawValue
    }
}

struct KanguroFile {
    var name: Filename
    var ext: FileExtension
}
