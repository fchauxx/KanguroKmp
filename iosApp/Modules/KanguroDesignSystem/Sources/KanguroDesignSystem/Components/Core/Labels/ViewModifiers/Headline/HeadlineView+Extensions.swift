import SwiftUI

// Headline - Regular
public extension View {

    func headlineSecondaryDarkestRegular() -> some View {
        modifier(HeadlineSecondaryDarkestRegular())
    }

    func headlineTertiaryDarkestRegular() -> some View {
        modifier(HeadlineTertiaryDarkestRegular())
    }
}

// Headline - Bold
public extension View {

    func headlineSecondaryDarkestBold() -> some View {
        modifier(HeadlineSecondaryDarkestBold())
    }
    
    func headlinePrimaryDarkestBold() -> some View {
        modifier(HeadlinePrimaryDarkestBold())
    }
}
