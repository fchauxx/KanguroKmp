import Foundation

public enum Filename: String, Codable {
    case termOfService = "term_of_service"
    
    public var title: String {
        return self.rawValue
    }
}

public enum FileExtension: String, Codable {
    case pdf = "pdf"
    case jpg = "jpg"
    
    public var fileType: String {
        return self.rawValue
    }
}

public struct KanguroFile {
    public var name: Filename
    public var ext: FileExtension
    
    public init(name: Filename, ext: FileExtension) {
        self.name = name
        self.ext = ext
    }
}
