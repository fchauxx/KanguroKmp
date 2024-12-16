import SwiftUI

public struct AdvertisingCardView: View {

    // MARK: - Property Wrappers
    @Binding var currentIndex: Int
    @Binding var cardWidth: CGFloat
    @Binding var cardHeight: CGFloat

    // MARK: - Stored properties
    let card: AdvertisingCardViewData
    let geometry: GeometryProxy

    public var body: some View {

        VStack {
            Image(card.imageName)
                .resizable()
                .scaledToFill()
                .frame(width: cardWidth, height: cardHeight)
                .clipShape(RoundedRectangle(cornerRadius: CornerRadius.sm))

        }
    }
}
