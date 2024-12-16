import SwiftUI

// Body - Regular
public extension View {

    func bodyNeutralDarkRegular() -> some View {
        modifier(BodyNeutralDarkRegular())
    }

    func bodySecondaryDarkestRegular() -> some View {
        modifier(BodySecondaryDarkestRegular())
    }

    func bodySecondaryDarkRegular() -> some View {
        modifier(BodySecondaryDarkRegular())
    }
}

// Body - Medium
public extension View {

    func bodySecondaryDarkMedium() -> some View {
        modifier(BodySecondaryDarkBold())
    }
}

// Body - Bold
public extension View {

    func bodySecondaryDarkBold() -> some View {
        modifier(BodySecondaryDarkBold())
    }

    func bodySecondaryDarkestBold() -> some View {
        modifier(BodySecondaryDarkestBold())
    }

    func bodyNeutralDarkBold() -> some View {
        modifier(BodySecondaryDarkBold())
    }
    
    func bodyPrimaryDarkestBold() -> some View {
        modifier(BodyPrimaryDarkestBold())
    }
}

// Body - Extra Bold
public extension View {

    func bodyWhiteExtraBold() -> some View {
        modifier(BodyWhiteExtraBold())
    }

    func bodySecondaryDarkestExtraBold() -> some View {
        modifier(BodySecondaryDarkestExtraBold())
    }
    
    func subheadPrimaryDarkestBlack() -> some View {
        modifier(SubheadBlack())
    }
}
