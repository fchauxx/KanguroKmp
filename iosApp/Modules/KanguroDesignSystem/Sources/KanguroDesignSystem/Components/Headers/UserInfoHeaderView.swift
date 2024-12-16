import SwiftUI
import KanguroSharedDomain

public struct UserInfoHeaderView: View {
    
    // MARK: - Stored Properties
    let username: String
    let familyStatus: String
    let userAddress: String
    @Environment(\.appLanguageValue) var language

    var lang: String {
        language.rawValue
    }

    // MARK: - Initializers
    public init(username: String,
                familyStatus: String,
                userAddress: String) {
        self.username = username
        self.familyStatus = familyStatus
        self.userAddress = userAddress
    }
    
    public var body: some View {
        VStack(spacing: 8) {
            HighlightedText(text: "userInfo.hello.label".localized(lang) + " \(username)",
                            highlightedText: username,
                            baseStyle: TextStyle(font: .museo(.museoSansBold, size: 32), color: .secondaryDarkest),
                            highlightedStyle: TextStyle(font: .museo(.museoSansBold, size: 32), color: .primaryDarkest))
            
            VStack(spacing: 2) {
                Text(familyStatus).bodySecondaryDarkRegular()
                Text(userAddress).bodySecondaryDarkRegular()
            }
        }
    }
}

// MARK: - Previews
struct UserInfoHeaderView_Previews: PreviewProvider {
    
    static var previews: some View {
        UserInfoHeaderView(username: "Tales",
                           familyStatus: "Single family",
                           userAddress: "1234 Main Street, Tampa, FL")
    }
}
