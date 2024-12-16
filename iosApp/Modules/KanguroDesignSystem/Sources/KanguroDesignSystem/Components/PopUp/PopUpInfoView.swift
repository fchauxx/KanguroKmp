import SwiftUI

public struct PopUpData {
    
    let title: String
    let description: String
    var highlightedData: (text: String, style: TextStyle)?
    
    public init(title: String,
                description: String,
                highlightedData: (text: String, style: TextStyle)? = nil
    ) {
        self.title = title
        self.description = description
        self.highlightedData = highlightedData
    }
}

public struct PopUpInfoView: View {
    
    // MARK: - Wrapped Properties
    @State var offset: CGFloat
    
    // MARK: - Stored Properties
    var data: PopUpData
    
    // MARK: - Actions
    var closeAction: SimpleClosure
    var didTapHighlightedDataTextAction: StringClosure

    public init(data: PopUpData,
                offset: CGFloat = 1000,
                closeAction: @escaping SimpleClosure,
didTapPopUpEmailAction: @escaping StringClosure = { _ in }) {
        self.data = data
        self.offset = offset
        self.closeAction = closeAction
        self.didTapHighlightedDataTextAction = didTapPopUpEmailAction
    }
    
    public var body: some View {
        ZStack {
            Color.black
                .edgesIgnoringSafeArea(.all)
                .opacity(OpacityLevel.medium)
                .onTapGesture {
                    close()
                }
            VStack {
                VStack(alignment: .leading, spacing: StackSpacing.nano) {
                    HStack(alignment: .bottom) {
                        Text(data.title)
                            .font(.lato(.latoBlack, size: 16))
                            .foregroundColor(.secondaryDarkest)
                        
                        Spacer()
                        Button(action: {
                            close()
                        }, label: {
                            Image.closeIcon
                                .resizable()
                                .frame(width: IconSize.md, height: IconSize.md)
                                .foregroundColor(.secondaryDarkest)
                        })
                    }
                    
                    Rectangle()
                        .frame(width: 33, height: 4)
                        .foregroundColor(.primaryDark)
                        .cornerRadius(CornerRadius.quarck)
                    if let highlightedData = data.highlightedData {
                        HighlightedText(text: data.description,
                                        highlightedText: highlightedData.text,
                                        baseStyle: TextStyle(font: .lato(.latoRegular, size: 13),
                                                             color: .secondaryMedium),
                                        highlightedStyle: highlightedData.style)
                        .onTapGesture {
                            UIPasteboard.general.string = highlightedData.text
                            email(to: highlightedData.text)
                        }
                    } else {
                        Text(data.description)
                            .paragraphSecondaryMediumRegular()
                    }
                }
                .padding([.leading, .trailing, .bottom], InsetSpacing.md)
                .padding(.top, InsetSpacing.xs)
                .background(.white)
                .cornerRadius(CornerRadius.md)
                .offset(x: 0, y: offset)
                .onAppear {
                    withAnimation(.spring()) {
                        offset = 0
                    }
                }
            }
            .padding(InsetSpacing.xs)
        }
    }
    
    private func close() {
        withAnimation {
            offset = 1000
            closeAction()
        }
    }
    
    private func email(to email: String) {
        didTapHighlightedDataTextAction(email)
    }
}

struct PopUpInfoView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            PopUpInfoView(
                data:
                    PopUpData(title: "Title",
                              description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariaturk"),
                closeAction: {}, didTapPopUpEmailAction: {_ in })
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(.black)
    }
}
