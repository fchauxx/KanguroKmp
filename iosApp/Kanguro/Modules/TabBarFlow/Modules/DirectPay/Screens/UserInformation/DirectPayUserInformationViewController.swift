import UIKit
import KanguroPetDomain

class DirectPayUserInformationViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: DirectPayUserInformationViewModel
    
    // MARK: - IB Outlets
    @IBOutlet private var navigationHeader: TitleNavigationBarView!
    @IBOutlet var formSectionList: FormularySectionListView!
    @IBOutlet var nextButton: CustomButton!
    @IBOutlet private var petPickerList: CustomPickerSelectionListView!
    @IBOutlet private var descriptionTitleLabel: CustomLabel!
    @IBOutlet private var customTextViewBg: UIView!
    @IBOutlet private var customTextView: CustomTextView!
    
    // MARK: Actions
    var goBackAction: SimpleClosure
    var goToRootAction: SimpleClosure
    var didTapNextAction: NewPetVetDirectPayClaimClosure
    var openPetSelection: SimpleClosure
    var showSearchView: SimpleClosure
    
    init(viewModel: DirectPayUserInformationViewModel,
         goBackAction: @escaping SimpleClosure = {},
         goToRootAction: @escaping SimpleClosure = {},
         didTapNextAction: @escaping NewPetVetDirectPayClaimClosure = { _ in },
         openPetSelection: @escaping SimpleClosure = {},
         showSearchView: @escaping SimpleClosure = {}
    ) {
        self.viewModel = viewModel
        self.goBackAction = goBackAction
        self.goToRootAction = goToRootAction
        self.didTapNextAction = didTapNextAction
        self.openPetSelection = openPetSelection
        self.showSearchView = showSearchView
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension DirectPayUserInformationViewController {
    
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
extension DirectPayUserInformationViewController {
    
    private func changed(state: DefaultViewState) {
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        case .dataChanged:
            updateVetEmail()
            updateVetName()
            updateClinicName()
        default:
            break
        }
    }
}

// MARK: - Setup
extension DirectPayUserInformationViewController {
    
    private func setupLayout() {
        setupTitleNavigation()
        setupLabels()
        setupForm()
        setupTextField()
        setupButtons()
        setupActions()
        setupPickerMenuGesture()
    }
    
    private func setupTitleNavigation() {
        navigationHeader.setup(title: "directPay.title.label".localized,
                               didTapBackButtonAction: goBackAction,
                               didTapCloseButtonAction: goToRootAction)
    }
    
    private func setupLabels() {
        descriptionTitleLabel.set(text: "directPay.userInformation.claimDescription.title.label".localized,
                                  style: TextStyle(color: .secondaryDark, size: .p12))
    }
    
    private func setupForm() {
        formSectionList.setupForm(formSectionDataList: [
            FormularySectionData(sectionTitle: "directPay.userInformation.firstSection.title.label".localized,
                                 formFields: [
                                    CustomTextFieldData(title: "directPay.userInformation.firstField.title.label".localized,
                                                        placeholder: viewModel.pickFirstPet?.label ?? "",
                                                        isEditable: true,
                                                        textFieldType: .customPicker,
                                                        leadingIcon: viewModel.pickFirstPet?.icon,
                                                        didTappedCustomTextfieldOption: { [weak self] in
                                                            guard let self else { return }
                                                            openPetSelection()
                                                        }),
                                    CustomTextFieldData(title: "directPay.userInformation.secondField.title.label".localized,
                                                        placeholder: viewModel.userName,
                                                        isEditable: false,
                                                        textFieldType: .default)
                                 ]),
            FormularySectionData(sectionTitle: "directPay.userInformation.secondSection.title.label".localized,
                                 formFields: [
                                    CheckboxListLabelData(checkboxQuestionTitle: "directPay.userInformation.checkbox.title.label".localized,
                                                          isMultipleSelectionEnabled: false,
                                                          options: [
                                                            CheckboxLabelData(isSelected: false, option: "directPay.checkbox.accident.label".localized),
                                                            CheckboxLabelData(isSelected: false, option: "directPay.checkbox.illness.label".localized)
                                                          ],
                                                          listOrientation: .horizontal,
                                                          optionsSelected: { [weak self] selectedItem in
                                                              guard let self else { return }
                                                              self.viewModel.setCheckboxSelection(selectedItem: selectedItem)
                                                              self.updateNextButtonEnabledStatus()
                                                          }),
                                    CustomTextFieldData(title: "directPay.userInformation.thirdField.title.label".localized,
                                                        placeholder: "directPay.userInformation.thirdField.placeholder.label".localized,
                                                        isEditable: true,
                                                        textFieldType: .calendar,
                                                        valueDidChange: { [weak self] dateSelected in
                                                            guard let self else { return }
                                                            self.viewModel.newDirectPayClaim.invoiceDate = dateSelected.date
                                                            self.updateNextButtonEnabledStatus()
                                                        }),
                                    CustomTextFieldData(title: "directPay.userInformation.fourthField.title.label".localized,
                                                        placeholder: "directPay.userInformation.fourthField.placeholder.label".localized,
                                                        isEditable: true,
                                                        textFieldType: .customSearchVet,
                                                        didTappedCustomTextfieldOption: { [weak self] in
                                                            guard let self else { return }
                                                            self.showSearchView()
                                                        }),
                                    CustomTextFieldData(title: "directPay.userInformation.fifthField.title.label".localized,
                                                        placeholder: "directPay.userInformation.fifthField.placeholder.label".localized,
                                                        isEditable: true,
                                                        textFieldType: .default,
                                                        valueDidChange: { [weak self] vetName in
                                                            guard let self else { return }
                                                            self.viewModel.newDirectPayClaim.veterinarianName = vetName
                                                            self.updateNextButtonEnabledStatus()
                                                        }),
                                    CustomTextFieldData(title: "directPay.userInformation.sixthField.title.label".localized,
                                                        placeholder: "directPay.userInformation.sixthField.placeholder.label".localized,
                                                        isEditable: true,
                                                        textFieldType: .default,
                                                        valueDidChange: { [weak self] clinicName in
                                                            guard let self else { return }
                                                            self.viewModel.newDirectPayClaim.veterinarianClinic = clinicName
                                                            self.updateNextButtonEnabledStatus()
                                                        })
                                 ])
        ])
    }
    
    private func setupTextField() {
        let style = TextStyle(color: .secondaryDarkest, size: .p16)
        customTextView.setup(style: style,
                             actions: TextFieldActions(didChangeAction: { [weak self] value in
            guard let self else { return }
            self.viewModel.update(dtpClaimDescription: value)
            self.updateNextButtonEnabledStatus()
        }))
        customTextView.set(placeholder: "directPay.userInformation.claimDescription.placeholder.label".localized,
                           style: style)
        customTextViewBg.borderColor = .secondaryLight
        customTextViewBg.borderWidth = 1
        customTextViewBg.cornerRadius = 8
    }
    
    private func setupButtons() {
        nextButton.set(title: "directPay.action.label".localized,
                       style: .primary)
        nextButton.isEnabled(viewModel.isUserAllowedToContinueDTPClaimCreation)
        nextButton.setImage(nil, for: .normal)
    }
    
    private func setupActions() {
        openPetSelection = { [weak self] in
            guard let self else { return }
            self.petPickerList.setup(data: viewModel.petPickerList)
            self.petPickerList.isHidden = false
        }
        
        petPickerList.didTapCustomPickerCardAction = { [weak self] selectedCustomPickerData in
            guard let self else { return }
            self.petPickerList.isHidden = true
            updatePetSelectionPicker(data: selectedCustomPickerData)
        }
    }
    
    private func setupPickerMenuGesture() {
        let gesture = UITapGestureRecognizer(target: self, action: #selector(self.hidePetPickerSelection))
        gesture.numberOfTapsRequired = 1
        gesture.cancelsTouchesInView = false
        petPickerList.alphaView.addGestureRecognizer(gesture)
    }
    
    @objc private func hidePetPickerSelection() {
        petPickerList.isHidden = true
    }
}

extension DirectPayUserInformationViewController {
    
    @IBAction private func nextButtonTouchUpInside(_ sender: UIButton) {
        didTapNextAction(viewModel.newDirectPayClaim)
    }
}
