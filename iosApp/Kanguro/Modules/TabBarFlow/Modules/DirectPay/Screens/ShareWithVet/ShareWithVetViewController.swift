import UIKit
import KanguroSharedDomain

class ShareWithVetViewController: BaseViewController {
    
    // MARK: - IBOutlets
    @IBOutlet private var customPicker: CustomPickerSelectionListView!
    @IBOutlet private var navigationBarView: TitleNavigationBarView!
    @IBOutlet private var shareDescription: CustomLabel!
    @IBOutlet private var step1Label: CustomLabel!
    @IBOutlet private var step1Description: CustomLabel!
    @IBOutlet private var step1Action: CustomButton!
    @IBOutlet private var step2Label: CustomLabel!
    @IBOutlet private var step2Description: CustomLabel!
    @IBOutlet private var step2Action: CustomButton!
    @IBOutlet var uploadPreviewStackView: SecondaryAttachmentListView!
    @IBOutlet private var doneButton: CustomButton!
    
    // MARK: - Dependencies
    var viewModel: ShareWithVetViewModel
    
    // MARK: - Stored Properties
    var downloadIcon = UIImage(named: "ic-download")
    var uploadIcon = UIImage(named: "ic-upload")
    var imagePicker = UIImagePickerController()
    
    // MARK: Actions
    var goToRootAction: SimpleClosure
    var didTapDownloadDocumentAction: SimpleClosure
    var didTapUploadSignedDocAction: SimpleClosure
    var didTapDoneAction: SimpleClosure
    var goToFinishDTP: SimpleClosure
    
    init(viewModel: ShareWithVetViewModel,
         goToRootAction: @escaping SimpleClosure = {},
         didTapDownloadDocumentAction: @escaping SimpleClosure = {},
         didTapUploadSignedDocAction: @escaping SimpleClosure = {},
         didTapDoneAction: @escaping SimpleClosure = {},
         goToFinishDTP: @escaping SimpleClosure = {}
    ) {
        self.viewModel = viewModel
        self.goToRootAction = goToRootAction
        self.didTapDownloadDocumentAction = didTapDownloadDocumentAction
        self.didTapUploadSignedDocAction = didTapUploadSignedDocAction
        self.didTapDoneAction = didTapDoneAction
        self.goToFinishDTP = goToFinishDTP
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension ShareWithVetViewController {
    
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
extension ShareWithVetViewController {
    
    private func changed(state: ShareWithVetViewState) {

        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getDTPPreSignedURL(claimId: viewModel.petClaim.id)
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .uploadSucceeded(let data):
            addPreviewFile(file: data)
        case .getPreSignedURLSucceeded:
            hideLoadingView()
        case .requestSucceeded:
            hideLoadingView()
            goToFinishDTP()
        case .requestFailed:
            hideLoadingView()
            showSimpleAlert(message: viewModel.requestError)
        }
    }
}

// MARK: - Setup
extension ShareWithVetViewController {
    
    private func setupLayout() {
        setupTitleNavigation()
        setupLabels()
        setupButtons()
        setupViews()
        setupConstraints()
        setupActions()
        setupImagePicker()
        setupPickerMenuGesture()
    }
    
    private func setupTitleNavigation() {
        navigationBarView.setup(title: "directPay.shareWithVet.title.label".localized,
                                didTapCloseButtonAction: goToRootAction)
    }
    
    private func setupLabels() {
        shareDescription.set(text: "directPay.shareWithVet.description.label".localized,
                             style: TextStyle(color: .secondaryDark,
                                              size: .p16))
        step1Label.set(text: "commom.step1".localized.uppercased(),
                       style: TextStyle(color: .primaryDarkest,
                                        weight: .black))
        step1Description.set(text: "directPay.shareWithVet.step1Description.label".localized,
                             style: TextStyle(color: .secondaryDark,
                                              weight: .bold,
                                              size: .p16))
        step2Label.set(text: "commom.step2".localized.uppercased(),
                       style: TextStyle(color: .primaryDarkest,
                                        weight: .black))
        step2Description.setHighlightedText(text: "directPay.shareWithVet.step2Description.label".localized,
                                            style: TextStyle(color: .secondaryDark, size: .p16),
                                            highlightedText: "directPay.shareWithVet.step2Highlighted.label".localized,
                                            highlightedStyle: TextStyle(color: .secondaryDark,
                                                                        weight: .bold,
                                                                        size: .p16))
    }
    
    private func setupButtons() {
        
        step1Action.set(title: "directPay.shareWithVet.downloadDoc.button.label".localized,
                        style: .secondary)
        if let downloadIcon {
            step1Action.updateIcon(downloadIcon)
        }
        step1Action.cornerRadius = 20
        step1Action.onTap { [weak self] in
            guard let self else { return }
            self.didTapDownloadDocumentAction()
        }
        
        step2Action.set(title: "directPay.shareWithVet.uploadDoc.button.label".localized,
                        style: .primary)
        if let uploadIcon {
            step2Action.updateIcon(uploadIcon)
        }
        step2Action.cornerRadius = 20
        step2Action.onTap { [weak self] in
            guard let self else { return }
            self.didTapUploadSignedDocAction()
        }
        
        doneButton.set(title: "directPay.shareWithVet.done.button".localized,
                       style: .primary)
        doneButton.setImage(nil, for: .normal)
        doneButton.onTap { [weak self] in
            guard let self else { return }
            self.didTapDoneAction()
        }
    }
    
    private func setupViews() {
        setDoneButtonStatus()
        customPicker.isHidden = true
        customPicker.setup(data: [
            CustomPickerData(id: 1,
                             dataType: .camera,
                             icon: UIImage(named: "ic-camera"),
                             label: "commom.scanDocument".localized),
            CustomPickerData(id: 2,
                             dataType: .file,
                             icon: UIImage(named: "ic-document-2"),
                             label: "commom.selectFile".localized)
        ])
    }
    
    private func setupConstraints() {
        customPicker.translatesAutoresizingMaskIntoConstraints = false
        customPicker.listView.heightAnchor.constraint(equalToConstant: 155).isActive = true
    }
    
    private func setupActions() {
        didTapDownloadDocumentAction = { [weak self] in
            guard let self,
                  let fileUrl = viewModel.petClaim.fileUrl else { return }
            showAttachmentOptionsView(fileUrl)
        }
        didTapUploadSignedDocAction = { [weak self] in
            guard let self else { return }
            self.customPicker.isHidden = false
        }
        didTapDoneAction = { [weak self] in
            guard let self else { return }
            self.viewModel.createDTPVetSignature()
        }
        customPicker.didTapCustomPickerCardAction = { [weak self] selectedCustomPickerData in
            guard let self else { return }
            switch selectedCustomPickerData.dataType {
            case .camera:
                takePicture()
            case .file:
                selectFile()
            default:
                break
            }
        }
    }
    
    private func setupPickerMenuGesture() {
        let gesture = UITapGestureRecognizer(target: self, action: #selector(self.hideUploadDocumentSelection))
        gesture.numberOfTapsRequired = 1
        gesture.cancelsTouchesInView = false
        customPicker.alphaView.addGestureRecognizer(gesture)
    }
    
    private func setupImagePicker() {
        imagePicker.delegate = self
        imagePicker.allowsEditing = false
    }
    
    @objc func hideUploadDocumentSelection() {
        customPicker.isHidden = true
    }
    
    func setDoneButtonStatus() {
        doneButton.isEnabled(viewModel.uploadedFilesIds.count >= 1 ? true : false)
    }
}
