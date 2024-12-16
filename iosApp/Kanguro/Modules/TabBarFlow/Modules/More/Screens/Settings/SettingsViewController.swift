import UIKit
import KanguroSharedDomain

class SettingsViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: SettingsViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet var titleLabel: CustomLabel!
    @IBOutlet private var languageTitleLabel: CustomLabel!
    @IBOutlet private var languageSubtitleLabel: CustomLabel!
    @IBOutlet var englishSelectionView: SelectionView!
    @IBOutlet private var spanishSelectionView: SelectionView!
    
    // MARK: - Actions
    var didTapSelectionViewAction: IntClosure = { _ in }
    var goBackAction: SimpleClosure = {}
    var restartAppAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension SettingsViewController {
    
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
extension SettingsViewController {
    
    private func changed(state: DefaultViewState) {
        
        hideLoadingView()
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .dataChanged:
            setupLayout()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            showActionAlert(title: "settings.success.alert".localized,
                            message: "settings.restartApp.alert".localized,
                            action: restartAppAction)
        }
    }
}

// MARK: - Setup
extension SettingsViewController {
    
    private func setupLayout() {
        setupLabels()
        setupActions()
        setupSelectionViews(viewModel.initialLanguage)
    }
    
    private func setupLabels() {
        titleLabel.set(text: "settings.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24, font: .raleway))
        languageTitleLabel.set(text: "settings.languageTitle.label".localized,
                               style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16, font: .raleway))
        languageSubtitleLabel.set(text: "settings.languageSubtitle.label".localized,
                                  style: TextStyle(color: .secondaryMedium, weight: .bold, size: .p12))
    }
    
    private func setupActions() {
        didTapSelectionViewAction = { [weak self] tag in
            guard let self = self else { return }
            let language: Language = (tag == 0) ? .English : .Spanish
            self.viewModel.update(language: language)
        }
    }
    
    private func setupSelectionViews(_ language: Language) {
        englishSelectionView.setup(data: SelectionViewData(title: "settings.english.label".localized,
                                                           didTapButtonAction: didTapSelectionViewAction))
        spanishSelectionView.setup(data: SelectionViewData(title: "settings.spanish.label".localized,
                                                           didTapButtonAction: didTapSelectionViewAction))
        switch language {
        case .English:
            englishSelectionView.isSelected(true)
            spanishSelectionView.isSelected(false)
        case .Spanish:
            englishSelectionView.isSelected(false)
            spanishSelectionView.isSelected(true)
        }
    }
}

// MARK: - IB Actions
extension SettingsViewController {
    
    @IBAction private func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}
