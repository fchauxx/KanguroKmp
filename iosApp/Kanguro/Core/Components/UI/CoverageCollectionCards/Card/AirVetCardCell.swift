import UIKit
import KanguroSharedDomain
import KanguroPetDomain
import KanguroDesignSystem
import KanguroUserDomain

class AirVetCardCell: UICollectionViewCell {

    // MARK: - IB Outlets
    @IBOutlet var airVetCardHostingView: UIView!

    // MARK: - Actions
    private var didTapAirVetCard: SimpleClosure = {}
}

// MARK: - Setup
private extension AirVetCardCell {

    func setupLayout(in viewController: UIViewController) {
        let cardView = AirvetCardView(onTapCard: didTapAirVetCard)
            .environment(\.appLanguageValue, User.getLanguage())

        airVetCardHostingView.setupSwiftUIIntoUIKitView(
            swiftUIView: cardView,
            basedViewController: viewController,
            attachConstraints: false
        )

        airVetCardHostingView.cornerRadius = 8
    }
}

// MARK: - Public Methods

extension AirVetCardCell {

    func setup(in viewController: UIViewController, action: @escaping SimpleClosure) {
        self.didTapAirVetCard = action
        setupLayout(in: viewController)
        self.setShadow(shadowOffset: CGSize(width: 2, height: 2))
    }
}
