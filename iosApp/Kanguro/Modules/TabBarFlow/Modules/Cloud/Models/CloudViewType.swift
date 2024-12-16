//
//  CloudViewType.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 03/04/23.
//

import Foundation

enum CloudViewType: Equatable {
    
    case base
    case petPolicies
    case petPolicyDocumentsOptions
    case petClaimAndInvoicesList
    case petFiles
    
    // MARK: - Computed Properties
    var breadcrumb: String {
        switch self {
        case .petPolicyDocumentsOptions, .petFiles:
            return "/ \("cloud.breadcrumb.policy.label".localized.uppercased())"
        case .petClaimAndInvoicesList:
            return "/ \("cloud.breadcrumb.policy.label".localized.uppercased()) / \("cloud.breadcrumb.claimDocuments.label".localized.uppercased())"
        default:
            return ""
        }
    }
    
    var title: String {
        switch self {
        case .base:
            return "common.back".localized
        case .petPolicies:
            return "cloud.title.label".localized
        case .petPolicyDocumentsOptions:
            return "cloud.backActionPolicies.label".localized
        case .petClaimAndInvoicesList:
            return "cloud.backActionCloud.label".localized
        case .petFiles:
            return "common.back".localized
        }
    }
}
