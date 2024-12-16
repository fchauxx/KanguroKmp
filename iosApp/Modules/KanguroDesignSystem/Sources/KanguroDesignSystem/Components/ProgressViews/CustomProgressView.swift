import SwiftUI

public struct CustomProgressView: View {
    
    // MARK: - Wrapped Properties
    @State private var progress = 0.0
    
    // MARK: - Stored Properties
    let minValue: Double
    let maxValue: Double
    
    // MARK: - Actions
    var didEndEditingAction: FloatClosure
    
    // MARK: - Initializers
    public init(minValue: Double,
                maxValue: Double,
                didEndEditingAction: @escaping FloatClosure) {
        self.minValue = minValue
        self.maxValue = maxValue
        self.didEndEditingAction = didEndEditingAction
    }
    
    // MARK: - Computed Properties
    var progressText: String {
        progress > 0 ? progress.getCurrencyFormatted() : minValue.getCurrencyFormatted()
    }
    
    // MARK: - Computed Properties
    var sliderCircle: UIImage? {
        let progressCircleConfig = UIImage.SymbolConfiguration(scale: .large)
        if let circleImage = UIImage(named: "ic-slider-button",
                                     in: .module,
                                     with: progressCircleConfig)?.resized(to: CGSize(width: 20, height: 8)) {
            return circleImage
        } else {
            return UIImage(systemName: "circle-fill")
        }
    }
    
    public var body: some View {
        VStack {
            Text(progressText)
                .headlineTertiaryDarkestRegular()
                .padding(.bottom, InsetSpacing.nano)
            
            Slider(value: $progress, in: minValue...maxValue) { editing in
                if !editing {
                    didEndEditingAction(progress)
                }
            }
            .tint(Color.tertiaryDarkest)
            .onAppear {
                UISlider.appearance()
                    .setThumbImage(sliderCircle, for: .normal)
            }
            .scaleEffect(CGSize(width: 1.0, height: 2.5))
            
            HStack {
                Text(String(minValue.getCurrencyFormatted()))
                    .footnoteNeutralMediumBold()
                Spacer()
                Text(String(maxValue.getCurrencyFormatted()))
                    .footnoteNeutralMediumBold()
            }
        }
    }
}

// MARK: - Previews
struct CustomProgressView_Previews: PreviewProvider {
    static var previews: some View {
        ZStack {
            CustomProgressView(minValue: 500, maxValue: 5000, didEndEditingAction: { _ in })
        }
        .padding(.horizontal, 60)
    }
}
