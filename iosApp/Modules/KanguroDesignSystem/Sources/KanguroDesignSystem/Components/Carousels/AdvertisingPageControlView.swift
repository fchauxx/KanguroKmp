import SwiftUI

public struct AdvertisingPageControlView: View {

    // MARK: - Property Wrappers
    @Binding var currentCardIndex: Int
    @State var numberOfCards: Int

    // MARK: - Stored Properties
    let maxHeight: CGFloat
    let minHeight: CGFloat

    public var body: some View {
        HStack {
            ForEach(1...numberOfCards, id: \.self) { page in
                Circle()
                    .frame(width: page == currentCardIndex + 1 ? maxHeight : minHeight)
                    .foregroundStyle(page == currentCardIndex + 1 ? Color.primaryDarkest : Color.neutralLight)
                    .onTapGesture {
                        withAnimation {
                            currentCardIndex = page - 1
                        }
                    }
            }
        }
    }
}

#Preview {
    AdvertisingPageControlView(
        currentCardIndex: .constant(3),
        numberOfCards: 5,
        maxHeight: 12.0,
        minHeight: 7.0)
}
