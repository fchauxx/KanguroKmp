import SwiftUI

// Caption - Regular
public extension View {

    func captionWhiteRegular() -> some View {
        modifier(CaptionWhiteRegular())
    }

    func captionSecondaryDarkRegular() -> some View {
        modifier(CaptionSecondaryDarkRegular())
    }
    
    func captionSecondaryDarkRegularBig() -> some View {
        modifier(CaptionSecondaryDarkRegularBig())
    }

    func captionSecondaryDarkestRegular() -> some View {
        modifier(CaptionSecondaryDarkestRegular())
    }

    func captionNeutralDarkRegular() -> some View {
        modifier(CaptionNeutralDarkRegular())
    }
}

// Caption - Bold
public extension View {

    func captionWhiteBold() -> some View {
        modifier(CaptionWhiteBold())
    }
    
    func captionTertiaryDarkBold() -> some View {
        modifier(CaptionTertiaryDarkBold())
    }

    func captionSecondaryDarkBold() -> some View {
        modifier(CaptionSecondaryDarkBold())
    }

    func captionSecondaryDarkestBold() -> some View {
        modifier(CaptionSecondaryDarkestBold())
    }
}
