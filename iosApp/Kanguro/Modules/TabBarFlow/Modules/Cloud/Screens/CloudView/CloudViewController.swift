import UIKit
import KanguroSharedDomain

class CloudViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: CloudViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var scrollView: UIScrollView!
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet var navigationBackButton: NavigationBackButton!
    @IBOutlet var breadcrumb: CustomLabel!
    @IBOutlet private var orderByView: UIView!
    @IBOutlet var orderByLabel: CustomLabel!
    @IBOutlet private var orderByImage: UIImageView!
    @IBOutlet var petCloudActionList: ActionCardsList!
    @IBOutlet var rentersCloudActionList: ActionCardsList!

    // MARK: - Stored Properties
    var viewType: CloudViewType
    let breadcrumbStyle: TextStyle = TextStyle(color: .secondaryDark, weight: .bold, size: .p12, font: .raleway)
    let refreshControl = UIRefreshControl()
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var getCloudPolicies: SelectedCloudClosure = { _ in }
    var getCloudPolicyOptionsDocuments: CloudPolicyDocumentsOptionsClosure = { _, _ in }

    // MARK: - Initializers
    init(type: CloudViewType) {
        self.viewType = type
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension CloudViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension CloudViewController {
    
    private func changed(state: CloudViewState) {
        
        refreshControl.endRefreshing()
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewType == .base ? viewModel.getCloudDocuments() : setupCloudActionCardLists()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            hideLoadingView()
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            hideLoadingView()
            setupCloudActionCardLists()
        }
    }
}

// MARK: - Setup
extension CloudViewController {
    
    private func setupLayout() {
        setupViews()
        setupLabels()
        setupRefreshControl()
    }
    
    private func setupViews() {
        breadcrumb.isHidden = false
        navigationBackButton.isHidden = false
        orderByView.isHidden = viewModel.shouldHiddenOrderByButton(viewType: viewType)
    }
    
    private func setupLabels() {
        setupNavigationBackButton(title: viewType.title)
        setupBreadcrumbLabel()
        setupOrderByLabel()

        petCloudActionList.update(title: "")
        rentersCloudActionList.update(title: "")
    }
    
    private func setupBreadcrumbLabel() {
        if viewType == .base {
            breadcrumb.isHidden = true
        } else {
            let path = viewModel.getBreadcrumbPathUppercased(viewType: viewType)
            breadcrumb.set(text: path, style: breadcrumbStyle)
        }
    }
    
    private func setupOrderByLabel() {
        orderByLabel.set(text: "cloud.orderByLatest.label".localized.uppercased(),
                         style: TextStyle(color: .secondaryMedium, size: .p12, font: .raleway, alignment: .right))
    }
    
    private func setupRefreshControl() {
        if viewType == .base {
            refreshControl.addTarget(self, action: #selector(reloadData(_:)), for: UIControl.Event.valueChanged)
            scrollView.refreshControl = refreshControl
        }
    }
    
    private func setupCloudActionCardLists() {
        petCloudActionList.clearActionCards()
        rentersCloudActionList.clearActionCards()

        switch viewType {
        case .base:
            setupPetListActionCards()
            setupRentersListActionCards()
        case .petPolicies:
            petCloudActionList.addActionCards(actionCardDataList: setupCloudDocumentsPoliciesActionCards())
        default: break
        }
    }
    
    private func setupNavigationBackButton(title: String) {
        navigationBackButton.update(title: title)
        navigationBackButton.update(action: goBackAction)
    }
}

// MARK: - Setup Cloud Action Cards
extension CloudViewController {
    
    private func setupPetListActionCards() {
        let petsCloud = viewModel.petList

        if petsCloud.isEmpty {
            petCloudActionList.isHidden = true
            return
        }

        guard let icon = UIImage(named: "ic-folder") else { return }

        var petList: [ActionCardData] = []
        for petCloud in petsCloud {
            guard let petName = petCloud.name else { return }
            petList.append(
                ActionCardData(leadingImage: icon,
                               leadingTitle: petName,
                               didTapCloudActionCard: getCloudPolicies,
                               dataType: .cloudDocumentPolicies(petCloud.toSelectedCloud()))
            )
        }

        petCloudActionList.update(title: "cloud.breadcrumb.label".localized.uppercased())
        petCloudActionList.addActionCards(actionCardDataList: petList)
    }

    private func setupRentersListActionCards() {
        let rentersCloud = viewModel.rentersList

        if rentersCloud.isEmpty {
            rentersCloudActionList.isHidden = true
            return
        }

        guard let icon = UIImage(named: "ic-folder") else { return }

        var rentersList: [ActionCardData] = []
        for renterCloud in rentersCloud {
            guard let name = renterCloud.name else { return }
            rentersList.append(
                ActionCardData(
                    leadingImage: icon,
                    leadingTitle: name,
                    didTapCloudActionCard: getCloudPolicies,
                    dataType: .cloudDocumentPolicies(renterCloud.toSelectedCloud())
                )
            )
        }

        rentersCloudActionList.update(title: "tabBarItem.renters".localized.uppercased())
        rentersCloudActionList.addActionCards(actionCardDataList: rentersList)
    }

    private func setupCloudDocumentsPoliciesActionCards() -> [ActionCardData] {
        guard let selectedCloud = viewModel.selectedCloud,
              let petPolicies = viewModel.policiesSortedByNewest,
              let icon = UIImage(named: "ic-folder") else { return [] }
        
        var petPoliciesList: [ActionCardData] = []
        for policy in petPolicies {
            if let policyId = policy.ciId {
                petPoliciesList.append(
                    ActionCardData(
                        leadingImage: icon,
                        leadingTitle: "\(policyId)",
                        didTapCloudPolicy: getCloudPolicyOptionsDocuments,
                        dataType: .cloudDocumentPolicy(
                            policy,
                            selectedCloud
                        )
                    )
                )
            }
        }
        return petPoliciesList
    }
}

// MARK: - Private Methods
extension CloudViewController {
    
    private func setNavigationBackButtonVisibility(show: Bool) {
        titleLabel.isHidden = show
        navigationBackButton.isHidden = !show
    }
    
    @objc private func reloadData(_ : AnyObject) {
        viewModel.getCloudDocuments()
    }
    
    private func reorderList(_ list: [KanguroSharedDomain.CloudDocumentPolicy]?) {
        guard let list else { return }
        orderByImage.image = orderByImage.image == UIImage(systemName: "arrow.up") ? UIImage(systemName: "arrow.down") : UIImage(systemName: "arrow.up")
        orderByLabel.set(text: orderByImage.image == UIImage(systemName: "arrow.up") ? "cloud.orderByLatest.label".localized.uppercased() : "cloud.orderByOldest.label".localized.uppercased() ,
                         style: TextStyle(color: .secondaryMedium, size: .p12, font: .raleway, alignment: .right))
        viewModel.cloudDocumentPolicies = list.reversed()
        setupCloudActionCardLists()
    }
}

// MARK: - IB Actions
extension CloudViewController {
    
    @IBAction private func orderByTouchUpInside(_ sender: UIButton) {
        reorderList(viewModel.cloudDocumentPolicies)
    }
}
