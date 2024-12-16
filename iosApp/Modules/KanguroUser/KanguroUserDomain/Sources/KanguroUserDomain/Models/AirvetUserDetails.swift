//
//  AirvetUserDetails.swift
//  Kanguro
//
//  Created by Felipe Chaux on 16/09/24.
//
 public struct AirvetUserDetails {
     public var firstName: String
     public var lastName: String
     public var email: String
     public var insuranceId: String
    
 public init(firstName: String, lastName: String, email: String, insuranceId: String) {
            self.firstName = firstName
            self.lastName = lastName
            self.email = email
            self.insuranceId = insuranceId
        }
}
