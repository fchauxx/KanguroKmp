import SwiftUI

public struct PickerModel: Hashable, Equatable {
    
    var label: String
    var isSelected: Bool
    var tagName: String?
    
    public init(label: String, isSelected: Bool, tagName: String? = nil) {
        self.label = label
        self.isSelected = isSelected
        self.tagName = tagName
    }
}

public struct SliderButtonView: View {
    
    // MARK: - Stored Properties
    var sources: [PickerModel]
    
    // MARK: - Wrapped Properties
    @Binding public var selection: PickerModel?
    
    public init(
        _ sources: [PickerModel],
        selection: Binding<PickerModel?>
    ) {
        self.sources = sources
        self._selection = selection
    }
    
    public var body: some View {
        VStack(spacing: 0) {
            GeometryReader { proxy in
                HStack(spacing: 0) {
                    mostPopularTagView(width: (proxy.size.width)/3)
                        .opacity(0)
                    mostPopularTagView(width: (proxy.size.width)/3)
                        .opacity(0)
                    mostPopularTagView(width: (proxy.size.width)/3)
                        .opacity(1)
                }
            }
            .frame(height: 33)
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(.gray, lineWidth: 1)
                .frame(height: 56)
                .overlay {
                    HStack(spacing: 0) {
                        ForEach(sources, id: \.self) { item in
                            Button(action: {
                                withAnimation(.easeInOut(duration: 0.150)) {
                                    selection = item
                                }
                            }, label: {
                                Text(item.label)
                                    .font(.lato(.latoBlack, size: 14))
                                    .foregroundColor(selection == item ? .white : Color.secondaryDarkest)
                                    .frame(maxWidth: .infinity)
                                    .multilineTextAlignment(.center)
                            })
                            
                        }
                    }
                }
                .cornerRadius(CornerRadius.sm)
                .background {
                    if let selection = selection, let selectedIdx = sources.firstIndex(of: selection) {
                        GeometryReader { geo in
                            withAnimation(.spring().speed(1.5)) {
                                RoundedRectangle(cornerRadius: CornerRadius.sm)
                                    .foregroundColor(Color.secondaryDarkest)
                                    .frame(width: geo.size.width / CGFloat(sources.count))
                                    .shadow(color: .black.opacity(0.1), radius: 2, x: 1, y: 1)
                                    .offset(x: geo.size.width / CGFloat(sources.count) * CGFloat(selectedIdx), y: 0)
                            }
                        }.frame(height: 56)
                    }
                }
        }
    }
    
    @ViewBuilder func mostPopularTagView(width: CGFloat) -> some View {
        Group {
            Text("Most Popular")
                .calloutWhiteExtraBold()
                .padding(InsetSpacing.nano)
            
        }
        .background(Color.tertiaryExtraDark)
        .cornerRadius(CornerRadius.sm, corners: [.topLeft, .topRight])
        .frame(width: width)
    }
}

struct SliderButtonView_Previews: PreviewProvider {
    static var previews: some View {
        let items = [
            PickerModel(label: "$200", isSelected: false),
            PickerModel(label: "$300", isSelected: false),
            PickerModel(label: "$500", isSelected: true, tagName: "")
        ]
        
        SliderButtonView(items, selection: .constant(items[0]))
    }
}
