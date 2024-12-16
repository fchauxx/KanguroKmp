//
//  ChatbotDelegateProtocol.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 21/07/23.
//

import Foundation

public protocol ChatbotDelegate: AnyObject {
    
    func didReceivedClaim(claimId: String)
}
