import SwiftUI

public enum PresenterNavigationHeaderViewStyle {
    
    case cleared
    case backgrounded
}

public struct PresenterNavigationHeaderView: View {
    
    var style: PresenterNavigationHeaderViewStyle
    
    // MARK: - Actions
    var backAction: SimpleClosure?
    var closeAction: SimpleClosure?
    
    public init(style: PresenterNavigationHeaderViewStyle = .cleared,
                backAction: SimpleClosure? = nil,
                closeAction: SimpleClosure? = nil) {
        self.style = style
        self.backAction = backAction
        self.closeAction = closeAction
    }
    
    public var body: some View {
        HStack {
            if let backAction {
                Button {
                    backAction()
                } label: {
                    Image.backIcon
                        .resizable()
                        .frame(width: IconSize.sm, height: IconSize.sm)
                        .foregroundColor(.secondaryMedium)
                        .padding(InsetSpacing.nano)
                        .background {
                            if style == .backgrounded {
                                Circle()
                                    .fill(Color.white)
                                    .onTapGesture {
                                        backAction()
                                    }
                            }
                        }
                }
            }
            Spacer()
            if let closeAction {
                Button {
                    closeAction()
                } label: {
                    Image.closeIcon
                        .resizable()
                        .frame(width: IconSize.md_2, height: IconSize.md_2)
                        .foregroundColor(.secondaryMedium)
                        .padding(InsetSpacing.nano)
                        .background {
                            if style == .backgrounded {
                                Circle()
                                    .fill(Color.white)
                                    .onTapGesture {
                                        closeAction()
                                    }
                            }
                        }
                }
            }
        }
    }
}

struct SwiftUIView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            Color.black
            VStack {
                PresenterNavigationHeaderView(backAction: {}, closeAction: {})
                PresenterNavigationHeaderView(style: .backgrounded, backAction: {})
            }
        }
    }
}
