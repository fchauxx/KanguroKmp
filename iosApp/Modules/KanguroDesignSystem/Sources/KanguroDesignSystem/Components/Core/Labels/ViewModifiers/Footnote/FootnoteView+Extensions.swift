import SwiftUI

// Footnote - Regular
public extension View {

    func footnoteNeutralMediumRegular() -> some View {
        modifier(FootnoteNeutralMediumRegular())
    }
}

// Footnote - Bold
public extension View {

    func footnoteNeutralMediumBold() -> some View {
        modifier(FootnoteNeutralMediumBold())
    }
    
    func footnoteSecondaryBlack() -> some View {
        modifier(FootnoteSecondaryBlack())
    }

    func footnoteTertiaryExtraDarkBold() -> some View {
        modifier(FootnoteTertiaryExtraDarkBold())
    }
}
