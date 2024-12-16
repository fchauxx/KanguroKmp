//
//  Integer+Extensions.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 09/02/23.
//

import Foundation

// MARK: - Public Methods
extension IntegerLiteralType {
    
    var getBytesToKBytesFormatted: String {
        return self >= 1024 ? "\(self / 1024)kB" : "\(self)B"
    }
}
