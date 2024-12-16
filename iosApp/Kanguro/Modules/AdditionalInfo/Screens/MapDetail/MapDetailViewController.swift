import UIKit
import MapKit
import CoreLocation

class MapDetailViewController: BaseViewController {
    
    // MARK: - IB Outlets
    @IBOutlet var map: MKMapView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet private var draggingView: MapLocationsDraggingView!
    @IBOutlet private var dragginViewBottomDistance: NSLayoutConstraint!
    
    // MARK: - Dependencies
    var viewModel: MapDetailViewModel!
    
    // MARK: - Stored Properties
    let locationManager = CLLocationManager()
    var mapItems: [MKMapItem] = []
    private var currentRegion = MKCoordinateRegion()
    
    // MARK: - Computed Properties
    var userLocation: CLLocation? {
        return locationManager.location ?? nil
    }
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
}

// MARK: - LifeCycle
extension MapDetailViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
        setupLayout()
        checkLocationAuthorization()
    }
}

// MARK: - View State
extension MapDetailViewController {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLocationManager()
        default:
            break
        }
    }
}

// MARK: - Setup
extension MapDetailViewController {
    
    func setupLayout() {
        setupLabels()
    }
    
    func setupLabels() {
        titleLabel.set(text: "mapDetail.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21 ,font: .raleway))
    }
}

// MARK: - DraggingView Methods
extension MapDetailViewController {
    
    
    private func setupDraggingView() {
        var data: [VetLocationData] = []
        for location in mapItems {
            var locationData = VetLocationData()
            locationData.location = location
            locationData.distance = setLocationDistance(location: location)
            if isNearbyLocation(location: location) {
                data.append(locationData)
            }
        }
        setupDraggingViewActions()
        draggingView.setup(mapData: data)
        animateDraggingViewAlpha()
        setupDraggingViewLayout()
    }
    
    private func isNearbyLocation(location: MKMapItem) -> Bool {
        return setLocationDistance(location: location)?.isLess(than: viewModel.coveredDistance) ?? false
    }
    
    private func setLocationDistance(location: MKMapItem) -> Double? {
        guard let userLocation else { return nil }
        let location = location.placemark.location?.distance(from: userLocation)
        return location
    }
    
    private func setupDraggingViewActions() {
        draggingView.didDragAction = { [weak self] in
            guard let self else { return }
            self.setupDraggingViewLayout()
        }
        draggingView.didTapLocationAction = { [weak self] location in
            guard let self else { return }
            self.draggingView.isExpanded = false
            self.draggingView.didDragAction()
            self.goToChosenLocation(location: location)
        }
    }
    
    private func setupDraggingViewLayout() {
        let mapHeight = map.frame.height
        let expansionFactor = (draggingView.isExpanded) ? 0.96 : 0.3
        dragginViewBottomDistance.constant = mapHeight * expansionFactor * (-1)
        UIView.animate(withDuration: 0.5) { [weak self] in
            guard let self else { return }
            self.view.layoutIfNeeded()
        }
    }
    
    private func animateDraggingViewAlpha() {
        draggingView.isHidden = mapItems.isEmpty
        UIView.animate(withDuration: 1, delay: 0) { [weak self] in
            guard let self else { return }
            self.draggingView.alpha = 1
        }
    }
}

// MARK: - Location Methods
extension MapDetailViewController {
    
    private func setupLocationManager() {
        locationManager.delegate = self
        locationManager.desiredAccuracy = 2
        locationManager.distanceFilter = viewModel.coveredDistance
    }
    
    private func checkLocationAuthorization() {
        switch CLLocationManager().authorizationStatus {
        case .authorizedWhenInUse, .authorizedAlways:
            map.showsUserLocation = true
            locationManager.startUpdatingLocation()
        default:
            locationManager.requestWhenInUseAuthorization()
        }
    }
    
    func showPointsOfInterest() {
        let searchRequest = MKLocalSearch.Request()
        searchRequest.naturalLanguageQuery = "veterinary"
        searchRequest.region = currentRegion
        
        let search = MKLocalSearch(request: searchRequest)
        search.start { [weak self] response, error in
            guard let self = self,
                  let response = response else { return }
            
            self.mapItems = []
            for item in response.mapItems {
                if self.isNearbyLocation(location: item) {
                    self.mapItems.append(item)
                }
            }
            self.addMapAnnotations()
            self.setupDraggingView()
        }
    }
    
    private func addMapAnnotations() {
        if mapItems.isEmpty { return }
        for item in mapItems {
            let annotation = MKPointAnnotation()
            annotation.coordinate = item.placemark.coordinate
            annotation.title = item.name
            annotation.subtitle = item.phoneNumber
            self.map.addAnnotation(annotation)
        }
    }
    
    private func goToChosenLocation(location: MKMapItem) {
        let coordinateRegion = MKCoordinateRegion(center: location.placemark.coordinate,
                                                  latitudinalMeters: viewModel.scale,
                                                  longitudinalMeters: viewModel.scale)
        map.setRegion(coordinateRegion, animated: true)
    }
    
    private func renderRegion(_ location: CLLocation) {
        let coordinate = CLLocationCoordinate2D(latitude: location.coordinate.latitude,
                                                longitude: location.coordinate.longitude)
        let coordinateDelta = 0.1
        let span = MKCoordinateSpan(latitudeDelta: coordinateDelta,
                                    longitudeDelta: coordinateDelta)
        
        currentRegion = MKCoordinateRegion(center: coordinate, span: span)
        map.setRegion(currentRegion, animated: true)
    }
}

// MARK: - CLLocationManagerDelegate
extension MapDetailViewController: CLLocationManagerDelegate {
    
    func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]) {
        if let location = locations.last {
            renderRegion(location)
            showPointsOfInterest()
        }
    }
    
    func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        checkLocationAuthorization()
    }
}

// MARK: - IB Actions
extension MapDetailViewController {
    @IBAction func goBack(_ sender: UIButton) {
        goBackAction()
    }
}
