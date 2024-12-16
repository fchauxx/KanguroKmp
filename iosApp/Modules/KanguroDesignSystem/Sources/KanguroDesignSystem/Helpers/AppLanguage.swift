import Foundation
import KanguroSharedDomain
import SwiftUI

public struct AppLanguageKey: EnvironmentKey {
    public static let defaultValue: KanguroSharedDomain.Language = .English
}

extension EnvironmentValues {
    public var appLanguageValue: Language {
        get { self[AppLanguageKey.self] }
        set { self[AppLanguageKey.self] = newValue }
    }
}

extension View {
    public func appLanguageValue(_ appLanguageValue: Language) -> some View {
        environment(\.appLanguageValue, appLanguageValue)
    }
}
