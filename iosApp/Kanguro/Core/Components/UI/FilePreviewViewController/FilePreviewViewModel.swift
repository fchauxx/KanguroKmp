//
//  FilePreviewViewModel.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 09/06/23.
//

import UIKit

class FilePreviewViewModel {
    
    // Stored Properties
    var data: Data?
    
    init(data: Data?) {
        self.data = data
    }
}

// MARK: Public Methods
extension FilePreviewViewModel {
    
    func getFileMimeType() -> FileExtension? {
        guard let data else { return nil }
        var mimeType: UInt8 = 0
        data.copyBytes(to: &mimeType, count: 1)
        return (mimeType == 0x25) ? .pdf : .jpg
    }
}
