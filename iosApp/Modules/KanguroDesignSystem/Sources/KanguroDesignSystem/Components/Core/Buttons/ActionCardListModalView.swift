import SwiftUI

public struct ActionCardListModalView: View {
    
    // MARK: - Wrapped Properties
    @State private var offset: CGFloat
    
    // MARK: - Stored Properties
    let cardButtons: [CardButton]
    let cornerRadius: CGFloat
    let corners: UIRectCorner
    
    // MARK: - Actions
    var closeAction: SimpleClosure
    
    public init(offset: CGFloat = 500,
                cardButtons: [CardButton],
                cornerRadius: CGFloat = CornerRadius.md,
                corners: UIRectCorner = [.allCorners],
                closeAction: @escaping SimpleClosure) {
        self.offset = offset
        self.cardButtons = cardButtons
        self.cornerRadius = cornerRadius
        self.corners = corners
        self.closeAction = closeAction
    }
    
    public var body: some View {
        ZStack(alignment: .bottom) {
            Color.black
                .edgesIgnoringSafeArea(.all)
                .opacity(OpacityLevel.medium)
                .onTapGesture {
                    close()
                }
            VStack {
                VStack(alignment: .leading, spacing: StackSpacing.nano) {
                    Button(action: {
                        close()
                    }, label: {
                        HStack {
                            Spacer()
                            Rectangle()
                                .cornerRadius(CornerRadius.halfQuarck)
                                .foregroundColor(Color.secondaryMedium)
                                .frame(width: 60, height: HeightSize.nano)
                            
                            Spacer()
                        }
                    })
                    .frame(height: HeightSize.xs)
                    .gesture(DragGesture(minimumDistance: 0, coordinateSpace: .local)
                        .onEnded({ value in
                            if value.translation.height > 0 {
                                close()
                            }
                        }))
                    ActionCardListView(cardButtons: cardButtons)
                        .padding(.bottom, InsetSpacing.xs)
                }
                .background(.white)
                .cornerRadius(cornerRadius, corners: corners)
                .offset(x: 0, y: offset)
                .onAppear {
                    withAnimation(.spring()) {
                        offset = 0
                    }
                }
            } //: VStack
        } //: ZStack
        .edgesIgnoringSafeArea(.all)
    }
    
    private func close() {
        withAnimation {
            offset = 500
            closeAction()
        }
    }
}

struct ActionCardListModalView_Previews: PreviewProvider {
    static var previews: some View {
        ActionCardListModalView(cardButtons: [
            CardButton(content: AnyView(ActionCardButton(title: "Take picture(s)",
                                                         icon: Image.cameraIcon,
                                                         style: .secondary,
                                                         didTapAction: {}))),
            CardButton(content: AnyView(ActionCardButton(title: "Select picture(s)",
                                                         icon: Image.fileIcon,
                                                         style: .secondary,
                                                         didTapAction: {})))
        ], closeAction: {
            //add this modal content to mother view
//            @State var modalContent: AdditionalCoveragePopUpData?
//            self.modalContent = nil
        })
    }
}
