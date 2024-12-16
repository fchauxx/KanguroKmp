import UIKit
import KanguroPetDomain

class SearchVetViewController: BaseViewController {
    
    // MARK: - IBOutlets
    @IBOutlet private var searchField: CustomTextFieldView!
    @IBOutlet private var stackView: UIStackView!
    @IBOutlet private var tableView: UITableView!
    @IBOutlet private var descriptionView: UIView!
    @IBOutlet private var searchDescriptionImage: UIImageView!
    @IBOutlet private var searchDescription: CustomLabel!
    @IBOutlet private var continueWithThisEmail: CustomLabel!

    // MARK: - Stored Properties
    var viewModel: SearchVetViewModel
    
    // MARK: - Computed Properties
    var filteredVetData: [Veterinarian] {
        guard let vetDataList = viewModel.vetDataList else { return [] }
        if !viewModel.textFieldInput.isEmpty {
            return vetDataList.filter {
                guard let email = $0.email else { return false }
                return email.lowercased().contains(viewModel.textFieldInput.lowercased())
            }
        } else {
            return vetDataList
        }
    }
    
    // MARK: - Actions
    var didSelectVetAction: VetDataClosure
    var didSelectedNewVetEmail: StringClosure
    
    // MARK: Initializers
    init(viewModel: SearchVetViewModel,
         didSelectVetAction: @escaping VetDataClosure,
         didSelectedNewVetEmail: @escaping StringClosure
    ) {
        self.viewModel = viewModel
        self.didSelectVetAction = didSelectVetAction
        self.didSelectedNewVetEmail = didSelectedNewVetEmail
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension SearchVetViewController {
    
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
extension SearchVetViewController {
    
    private func changed(state: DefaultViewState) {
        hideLoadingView()
        switch state {
        case .started:
            viewModel.getVeterinarians()
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestSucceeded:
            setupViews()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        default:
            break
        }
    }
}

// MARK: - Setup
extension SearchVetViewController {
    
    private func setupLayout() {
        setupLabels()
        setupImages()
        setupViews()
        setupTableView()
        setupLabelAction()
    }

    private func setupLabels() {
        searchDescription.set(text: "directPay.searchVet.description.label".localized,
                              style: TextStyle(color: .secondaryDark, size: .p16, alignment: .center))
        continueWithThisEmail.setHighlightedText(text: "directPay.searchVet.continueButton.label".localized,
                                         style: TextStyle(color: .secondaryDarkest,
                                                          weight: .regular, size: .p16),
                                         highlightedText: "directPay.searchVet.continueButton.label".localized,
                                         highlightedStyle: TextStyle(color: .tertiaryDarkest,
                                                                     size: .p16,
                                                                     underlined: true))
    }

    @objc func labelTapped(_ sender: UITapGestureRecognizer) {
        if viewModel.isValidEmail {
            didSelectedNewVetEmail(viewModel.textFieldInput)
        } else {
            viewModel.invalidInput = true
            searchField.showError(text: "textFieldTypeError.email".localized)
        }
    }

    private func setupLabelAction() {
        let labelTap = UITapGestureRecognizer(target: self, action: #selector(self.labelTapped(_:)))
        continueWithThisEmail.isUserInteractionEnabled = true
        continueWithThisEmail.addGestureRecognizer(labelTap)
    }

    private func setupImages() {
        searchDescriptionImage.image = UIImage(named: "search")
    }
    
    private func setupTableView() {
        tableView.register(identifiers: [FindVetTableViewCell.identifier])
    }
    
    private func setupViews() {
        searchField.set(type: .search,
                        config: TextFieldConfig(title: "directPay.searchVet.title.label".localized,
                                                placeholder: "directPay.searchVet.placeholder.label".localized,
                                                returnKeyType: .default),
                        actions: TextFieldActions(didChangeAction: { [weak self] text in
            guard let self else { return }
            removeTextFieldErrorStateIfNeeded()
            self.viewModel.textFieldInput = text
            self.tableView.reloadData()
        }))
        continueWithThisEmail.isHidden = true
        tableView.reloadData()
    }
}

// MARK: - Update
extension SearchVetViewController {

    func updateDescriptionView() {
        if !viewModel.textFieldInput.isEmpty {
            searchDescription.set(text: "directPay.searchVet.emailNotExist.label".localized,
                                  style: TextStyle(color: .secondaryDark, size: .p16, alignment: .center))
            continueWithThisEmail.isHidden = false
        } else {
            setupLabels()
            continueWithThisEmail.isHidden = true
        }
    }

    func removeTextFieldErrorStateIfNeeded() {
        if viewModel.invalidInput {
            searchField.hideError()
            viewModel.invalidInput = false
        }
    }
}

// MARK: - UITableViewDelegate & UITableViewDataSource
extension SearchVetViewController: UITableViewDelegate, UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        descriptionView.isHidden = filteredVetData.count > 0 ? true : false
        updateDescriptionView()
        return filteredVetData.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell: FindVetTableViewCell = tableView.dequeueReusableCell(for: indexPath) {
            cell.setup(vetData: filteredVetData[indexPath.row])
            return cell
        } else {
            return UITableViewCell()
        }
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        didSelectVetAction(filteredVetData[indexPath.row])
    }
}
