import SwiftUI

public struct NavigationBackButtonView: View {
    
    // MARK: - Stored Properties
    let title: String
    let titleStyle: TextStyle
    let bgColor: Color
    let action: SimpleClosure
    
    // MARK: - Initializers
    public init(title: String, 
                titleStyle: TextStyle = TextStyle(
                    font: .lato(.latoBold, size: 14),
                    color: .secondaryDark),
                bgColor: Color = .neutralBackground,
                action: @escaping SimpleClosure) {
        self.title = title
        self.titleStyle = titleStyle
        self.bgColor = bgColor
        self.action = action
    }
    
    public var body: some View {
        Button(action: action, label: {
            HStack(spacing: InsetSpacing.nano) {
                Image.backIcon
                    .padding(.leading, InsetSpacing.nano)
                
                Text(title)
                    .font(titleStyle.font)
                    .foregroundStyle(titleStyle.color)
                
                Spacer()
            }
            .padding(InsetSpacing.nano)
            .background(bgColor)
        })
    }
}

struct NavigationBackButtonView_Previews: PreviewProvider {
    
    static var previews: some View {
        NavigationBackButtonView(title: "New Claim",
                                 action: {})
    }
}
