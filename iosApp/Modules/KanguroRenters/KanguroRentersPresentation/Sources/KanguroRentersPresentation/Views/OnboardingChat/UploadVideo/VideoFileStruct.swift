import SwiftUI

public struct Movie: Transferable, Identifiable, Equatable {
    let url: URL
    let fileName: String
    public let id = UUID()
    
    public static var transferRepresentation: some TransferRepresentation {
        FileRepresentation(contentType: .movie) { movie in
            SentTransferredFile(movie.url)
        } importing: { received in
            do {
                let copy = URL.documentsDirectory.appendingPathComponent(received.file.lastPathComponent)
                
                if FileManager.default.fileExists(atPath: copy.path) {
                    try FileManager.default.removeItem(at: copy)
                }
                
                try FileManager.default.copyItem(at: received.file, to: copy)
                
                return Self.init(url: copy, fileName: copy.lastPathComponent)
            } catch {
                print("Error importing file. - \(error.localizedDescription)")
                return Self.init(url: received.file, fileName: received.file.lastPathComponent)
            }
        }
    }

}

