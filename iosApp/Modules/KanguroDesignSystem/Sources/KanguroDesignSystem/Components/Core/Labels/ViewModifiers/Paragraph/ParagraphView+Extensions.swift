import SwiftUI

// Paragraph - Regular
public extension View {

    func paragraphSecondaryMediumRegular() -> some View {
        modifier(ParagraphSecondaryMediumRegular())
    }

    func paragraphSecondaryLightRegularItalic() -> some View {
        modifier(ParagraphSecondaryLightRegularItalic())
    }
}

// Paragraph - Bold
public extension View {

    func paragraphTertiaryDarkestRegularItalic() -> some View {
        modifier(ParagraphTertiaryDarkestBoldItalic())
    }
    
    func paragraphTertiaryExtraDarkBold() -> some View {
        modifier(ParagraphTertiaryExtraDarkBold())
    }
}

// Paragraph - Black
public extension View {

    func paragraphSecondaryDarkestBlack() -> some View {
        modifier(ParagraphSecondaryDarkestBlack())
    }
}
