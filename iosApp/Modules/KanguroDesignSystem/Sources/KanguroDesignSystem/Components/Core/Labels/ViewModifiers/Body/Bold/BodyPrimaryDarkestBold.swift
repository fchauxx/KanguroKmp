//
//  BodyPrimaryDarkestBold.swift
//
//
//  Created by Mateus Vagner on 26/04/24.
//

import SwiftUI

struct BodyPrimaryDarkestBold: ViewModifier {

    func body(content: Content) -> some View {
        content
            .font(.lato(.latoBold, size: 16))
            .foregroundColor(.primaryDarkest)
    }
}
