import SwiftUI

public struct AdvertisingCarouselView: View {

    // MARK: - Property Wrappers
    @State private var currentIndex: Int = 0
    @State private var dragOffset: CGFloat = 0
    @State private var cardWidth: CGFloat
    @State private var cardHeight: CGFloat

    // MARK: - Stored Properties
    let cards: [AdvertisingCardViewData]
    let pageControlMaxHeight: CGFloat
    let pageControlMinHeight: CGFloat

    // MARK: - Actions
    var redirectToPartnerWebpageAction: StringClosure

    public init(
        currentIndex: Int = 0,
        dragOffset: CGFloat = 0,
        cards: [AdvertisingCardViewData],
        cardWidth: CGFloat,
        cardHeight: CGFloat,
        pageControlMaxHeight: CGFloat,
        pageControlMinHeight: CGFloat,
        redirectToPartnerWebpageAction: @escaping StringClosure
    ) {
        self.currentIndex = currentIndex
        self.dragOffset = dragOffset
        self.cards = cards
        self.cardWidth = cardWidth
        self.cardHeight = cardHeight
        self.pageControlMaxHeight = pageControlMaxHeight
        self.pageControlMinHeight = pageControlMinHeight
        self.redirectToPartnerWebpageAction = redirectToPartnerWebpageAction
    }

    public var body: some View {
        GeometryReader { geometry in
            VStack(alignment: .center) {
                HStack {

                    if currentIndex == cards.count - 1 {
                        Spacer()
                    }

                    ZStack {
                        ForEach(cards) { card in
                            AdvertisingCardView(
                                currentIndex: $currentIndex,
                                cardWidth: $cardWidth,
                                cardHeight: $cardHeight,
                                card: card,
                                geometry: geometry
                            )
                            .offset(
                                x: dragOffset + CGFloat(card.id - currentIndex) * (geometry.size.width > 393 ? geometry.size.width * 0.75 : geometry.size.width * 0.8)
                            )
                            .onTapGesture {
                                redirectToPartnerWebpageAction(card.partnerName)
                            }
                        }
                    }
                    .gesture(
                        DragGesture()
                            .onChanged { value in
                                dragOffset = value.translation.width
                            }
                            .onEnded { value in
                                let cardWidth = geometry.size.width * 0.3
                                let offset = value.translation.width / cardWidth

                                withAnimation(Animation.spring()) {
                                    dragOffset = 0

                                    if value.translation.width < -offset
                                    {
                                        currentIndex = min(currentIndex + 1, cards.count - 1)
                                    } else if value.translation.width > offset {
                                        currentIndex = max(currentIndex - 1, 0)
                                    }
                                }
                            }
                    )
                    .padding(.horizontal, InsetSpacing.xs)

                    if currentIndex == 0 {
                        Spacer()
                    }
                }
                .frame(width: geometry.size.width)

                AdvertisingPageControlView(
                    currentCardIndex: $currentIndex,
                    numberOfCards: cards.count,
                    maxHeight: pageControlMaxHeight,
                    minHeight: pageControlMinHeight
                )
                .padding(.top, InsetSpacing.xxs)
            }
            .frame(maxHeight: .infinity)
        }
    }
}

#Preview {
    AdvertisingCarouselView(
        cards: [
            AdvertisingCardViewData(id: 0, imageName: "banner-roam", URL: "", partnerName: "roam"),
            AdvertisingCardViewData(id: 1, imageName: "banner-roam", URL: "", partnerName: "roam"),
            AdvertisingCardViewData(id: 2, imageName: "banner-roam", URL: "", partnerName: "roam")
        ],
        cardWidth: 327.0,
        cardHeight: 136.0,
        pageControlMaxHeight: 12.0,
        pageControlMinHeight: 7.0, redirectToPartnerWebpageAction: {_ in }
    )
}
