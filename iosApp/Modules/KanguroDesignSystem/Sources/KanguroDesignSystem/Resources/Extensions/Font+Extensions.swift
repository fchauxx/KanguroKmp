import SwiftUI

public enum KanguroDesignSystemError: Error {
    case couldNotFindFontFile(String)
    case couldNotRegisterFont(String)
}

protocol FontRegistrable {
    static func registerAll() throws
}

public enum LatoFont: String, CaseIterable, FontRegistrable {

    case latoBlack = "Lato-Black"
    case latoBlackItalic = "Lato-BlackItalic"
    case latoBold = "Lato-Bold"
    case latoBoldItalic = "Lato-BoldItalic"
    case latoLight = "Lato-Light"
    case latoLightItalic = "Lato-LightItalic"
    case latoRegular = "Lato-Regular"
    case latoRegularItalic = "Lato-RegularItalic"

    public static func registerAll() throws {
        try Self.allCases.forEach { variant in
            guard let fontURL = Bundle.module.url(forResource: variant.rawValue, withExtension: "ttf"),
                  let fontDataProvider = CGDataProvider(url: fontURL as CFURL),
                  let font = CGFont(fontDataProvider) else {
                assertionFailure("Could not register font")
                throw KanguroDesignSystemError.couldNotFindFontFile(variant.rawValue)
            }
            var error: Unmanaged<CFError>?
            guard CTFontManagerRegisterGraphicsFont(font, &error) else {
                guard let fontName = font.postScriptName, UIFont.fontNames(forFamilyName: "Lato").contains(fontName as String) else {
                    throw KanguroDesignSystemError.couldNotRegisterFont(variant.rawValue)
                }
                return
            }
        }
    }
}

public enum RalewayFont: String, CaseIterable {

    case ralewayBlack = "Raleway-Black"
    case ralewayBold = "Raleway-Bold"
    case ralewayLight = "Raleway-Light"
    case ralewayMedium = "Raleway-Medium"
    case ralewayRegular = "Raleway-Regular"

    public static func registerAll() throws {
        try Self.allCases.forEach { variant in
            guard let fontURL = Bundle.module.url(forResource: variant.rawValue, withExtension: "ttf"),
                  let fontDataProvider = CGDataProvider(url: fontURL as CFURL),
                  let font = CGFont(fontDataProvider) else {
                assertionFailure("Could not register font")
                throw KanguroDesignSystemError.couldNotFindFontFile(variant.rawValue)
            }
            var error: Unmanaged<CFError>?
            guard CTFontManagerRegisterGraphicsFont(font, &error) else {
                guard let fontName = font.postScriptName, UIFont.fontNames(forFamilyName: "Raleway").contains(fontName as String) else {
                    throw KanguroDesignSystemError.couldNotRegisterFont(variant.rawValue)
                }
                return
            }
        }
    }
}

public enum MuseoSans: String, CaseIterable {
    case museoSans = "MuseoSans-500"
    case museoSansBlack = "MuseoSans-900"
    case museSansBlackItalic = "MuseoSans-900Italic"
    case museoSansBold = "MuseoSans-700"
    case museoSansBoldItalic = "MuseoSans-700Italic"
    case museoSansItalic = "MuseoSans-500Italic"
    // case museoSansLight = "MuseoSansLight"
    // case museoSansLightItalic = "MuseoSansLightItalic"
    // case museoSansRegular = "MuseoSansRegular"
    case museoSansThinItalic = "MuseoSans-300Italic"
    case museoSansThin = "MuseoSans-300"

    public static func registerAll() throws {
        try Self.allCases.forEach { variant in
            guard let fontURL = Bundle.module.url(forResource: variant.rawValue, withExtension: "otf"),
                  let fontDataProvider = CGDataProvider(url: fontURL as CFURL),
                  let font = CGFont(fontDataProvider) else {
                assertionFailure("Could not register font")
                throw KanguroDesignSystemError.couldNotFindFontFile(variant.rawValue)
            }
            var error: Unmanaged<CFError>?
            guard CTFontManagerRegisterGraphicsFont(font, &error) else {
                guard let fontName = font.postScriptName, UIFont.fontNames(forFamilyName: "Museo Sans").contains(fontName as String) else {
                    throw KanguroDesignSystemError.couldNotRegisterFont(variant.rawValue)
                }
                return
            }
        }
    }
}

public extension Font {

    static var latoInitialized = false
    static var ralewayInitialized = false
    static var museoInitialized = false

    static func lato(_ lato: LatoFont, size: CGFloat) -> Font {
        if !latoInitialized {
            do {
                try LatoFont.registerAll()
                latoInitialized = true
            } catch {
                assertionFailure("Could not initialize font")
            }
        }
        return .custom(lato.rawValue, size: size)
    }

    static func raleway(_ raleway: RalewayFont, size: CGFloat) -> Font {
        if !ralewayInitialized {
            do {
                try RalewayFont.registerAll()
                ralewayInitialized = true
            } catch {
                assertionFailure("Could not initialize font")
            }
        }
        return .custom(raleway.rawValue, size: size)
    }

    static func museo(_ museo: MuseoSans, size: CGFloat) -> Font {
        if !museoInitialized {
            do {
                try MuseoSans.registerAll()
                museoInitialized = true
            } catch {
                assertionFailure("Could not initialize font")
            }
        }
        return .custom(museo.rawValue, size: size)
    }

    static func listAllFonts() {
        UIFont.familyNames.forEach({ familyName in
            let fontNames = UIFont.fontNames(forFamilyName: familyName)
            print(familyName, fontNames)
        })
    }
}


