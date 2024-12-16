import SwiftUI

public struct PopUpActionView: View {

    // MARK: - Wrapped Properties
    @State var offset: CGFloat

    // MARK: - Stored Properties
    var popUpImage: Image
    var popUpDescription: String
    var popUpSubdescription: String?
    var mainActionLabel: String
    var secondaryActionLabel: String
    var mainActionBackground: Color

    // MARK: - Actions
    var cancelAction: SimpleClosure
    var confirmAction: SimpleClosure

    public init(offset: CGFloat = 1000,
                popUpImage: Image,
                popUpDescription: String,
                popUpSubdescription: String? = nil,
                mainActionLabel: String,
                secondaryActionLabel: String,
                mainActionBackground: Color = .secondaryDarkest,
                cancelAction: @escaping SimpleClosure,
                confirmAction: @escaping SimpleClosure) {
        self.offset = offset
        self.popUpImage = popUpImage
        self.popUpDescription = popUpDescription
        self.popUpSubdescription = popUpSubdescription
        self.mainActionLabel = mainActionLabel
        self.secondaryActionLabel = secondaryActionLabel
        self.mainActionBackground = mainActionBackground
        self.cancelAction = cancelAction
        self.confirmAction = confirmAction
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
                VStack(alignment: .center, spacing: StackSpacing.nano) {
                    HStack {
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
                    popUpImage
                        .resizable()
                        .frame(width: IconSize.xxl, height: IconSize.xxl)
                    VStack(spacing: InsetSpacing.xxxs) {
                        Text(popUpDescription)
                            .headlineSecondaryDarkestBold()
                            .multilineTextAlignment(.center)
                        
                        if let popUpSubdescription {
                            Text(popUpSubdescription)
                                .captionSecondaryDarkRegular()
                                .multilineTextAlignment(.center)
                        }
                    }
                    .padding(.top, InsetSpacing.xs)
                    .padding(.bottom, InsetSpacing.lg)
                    
                    HStack {
                        SecondaryButtonView(secondaryActionLabel, isAtPopUp: true) {
                            close()
                        }
                        PrimaryButtonView(mainActionLabel, background: mainActionBackground, isAtPopUp: true) {
                            confirmAction()
                        }
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
            .padding(InsetSpacing.md)
        }
    }

    private func close() {
        withAnimation {
            offset = 1000
            cancelAction()
        }
    }
}

struct PopUpActionView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            PopUpActionView(
                popUpImage: Image.delete2Icon,
                popUpDescription: "Are you sure you want to delete this item?",
                mainActionLabel: "Yes, delete",
                secondaryActionLabel: "Cancel",
                mainActionBackground: .negativeDarkest,
                cancelAction: {},
                confirmAction: {})
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(.black)
    }
}
