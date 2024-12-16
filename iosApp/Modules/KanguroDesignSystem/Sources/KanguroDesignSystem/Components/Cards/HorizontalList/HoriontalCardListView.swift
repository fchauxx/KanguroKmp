import SwiftUI

public struct HoriontalCardListView: View {
    
    let cardsData: [HorizontalListCardViewData]
    let didTapAddItemButton: SimpleClosure
    
    public init(cardsData: [HorizontalListCardViewData],
                didTapAddItemButton: @escaping SimpleClosure) {
        self.cardsData = cardsData
        self.didTapAddItemButton = didTapAddItemButton
    }
    
    public var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: InsetSpacing.xxs) {
                ForEach(cardsData) { data in
                    HorizontalListCardView(data: data)
                }
                
                Button(action: {
                    didTapAddItemButton()
                }, label: {
                    ZStack {
                        Circle()
                            .frame(width: IconSize.md_2, height: IconSize.md_2)
                            .foregroundStyle(Color.white)
                        Image.addSingleIcon
                            .resizable()
                            .frame(width: IconSize.sm, height: IconSize.sm)
                    }
                })
            }
            .padding(.leading, InsetSpacing.xs)
        }
    }
}

// MARK: - Previews
struct HoriontalCardListView_Previews: PreviewProvider {
    
    static var previews: some View {
        ZStack {
            Color.neutralBackground
            HoriontalCardListView(
                cardsData: [
                    HorizontalListCardViewData(icon: Image.iconStatusApartment,
                                               title: "Home",
                                               subtitle: "Tampa, FL",
                                               statusText: "Active",
                                               status: true,
                                               linkText: "see details",
                                               tapAction: {}),
                    HorizontalListCardViewData(icon: Image.iconStatusTownhouse,
                                               title: "Home",
                                               subtitle: "Tampa, FL",
                                               statusText: "Inactive",
                                               status: false,
                                               linkText: "see details",
                                               tapAction: {})
                ],
                didTapAddItemButton: {}
            )
        }
    }
}
