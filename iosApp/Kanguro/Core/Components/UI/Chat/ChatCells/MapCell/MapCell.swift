import UIKit
import MapKit
import CoreLocation

class MapCell: UITableViewCell {
    
    // MARK: - IB Outlets
    @IBOutlet private var map: MKMapView!
    @IBOutlet private var mapBackgroundView: UIView!

    // MARK: - Actions
    var didTapMapAction: SimpleClosure = {}
}

// MARK: - Private Methods
extension MapCell {
    
    func setup(didTapMapAction: @escaping SimpleClosure) {
        self.didTapMapAction = didTapMapAction
        setupLayout()
    }
}

// MARK: - IB Actions
extension MapCell {
    func setupLayout() {
        mapBackgroundView.roundCorners(corners: [.topRight, .bottomLeft, .bottomRight], radius: 8)
    }
}

// MARK: - IB Actions
extension MapCell {
    @IBAction func goToPresentedMap(sender: UIButton) {
        didTapMapAction()
    }
}
