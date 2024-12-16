import UIKit
import SwiftSignatureView

enum PledgeOfHonorType {
    
    case chatbot
    case directPayToVet
    
    // MARK: - Computed Properties
    var buttonTitle: String {
        switch self {
        case .chatbot:
            return "pledgeOfHonor.confirm.button".localized
        case .directPayToVet:
            return "pledgeOfHonor.sendForm.button".localized
        }
    }
}

class PledgeOfHonorViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: PledgeOfHonorViewModel!
    
    // MARK: - Stored properties
    private var type: PledgeOfHonorType
    
    // MARK: - Computed properties
    private var userActionCauseTitle: String {
        if let title = viewModel.userActualCause?.title {
            return title
        } else {
            return "pledgeOfHonor.boldedBottom.label".localized
        }
    }
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var signatureView: SwiftSignatureView!
    @IBOutlet private var confirmButton: CustomButton!
    @IBOutlet private var donationLabel: CustomLabel!
    @IBOutlet private var donationBoldedLabel: CustomLabel!
    @IBOutlet private var donationLabelsView: UIView!
    @IBOutlet private var agreeCheckBoxView: UIView!
    @IBOutlet private var checkBoxLabel: CustomLabel!
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    var didSaveSignatureAction: ImageClosure
    var didDTPClaimCreationSucceedAction: ClaimClosure
    
    init(type: PledgeOfHonorType,
         didSaveSignatureAction: @escaping ImageClosure = { _ in },
         didDTPClaimCreationSucceedAction: @escaping ClaimClosure = { _ in }) {
        self.type = type
        self.didSaveSignatureAction = didSaveSignatureAction
        self.didDTPClaimCreationSucceedAction = didDTPClaimCreationSucceedAction
        super.init(nibName: nil, bundle: nil)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}

// MARK: - Life Cycle
extension PledgeOfHonorViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        guard let signature = viewModel.signature else { return }
        didSaveSignatureAction(signature)
    }
}

// MARK: - View State
extension PledgeOfHonorViewController {
    
    private func changed(state: PledgeOfHonorState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            setupLayout()
        case .beginToDraw:
            confirmButton.isEnabled(true)
        case .loading:
            showLoadingView(shouldAnimate: false)
        case .requestFailed:
            hideLoadingView()
            showSimpleAlert(message: viewModel.requestError)
        case .requestSucceeded:
            hideLoadingView()
            guard let createdDTPClaim = viewModel.dtpNewClaim else { return }
            didDTPClaimCreationSucceedAction(createdDTPClaim)
        }
    }
}

// MARK: - Setup
extension PledgeOfHonorViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupSignatureView()
        setupLabelAction()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "pledgeOfHonor.title.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        subtitleLabel.set(text: "pledgeOfHonor.subtitle.label".localized, style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21, font: .raleway))
        
        donationLabel.set(text: "pledgeOfHonor.bottom.label".localized, style: TextStyle(color: .secondaryDarkest, size: .p16))
        donationBoldedLabel.set(text: userActionCauseTitle,
                                style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        
        checkBoxLabel.setHighlightedText(text: "pledgeOfHonor.checkbox.label".localized,
                                         style: TextStyle(color: .secondaryDarkest,
                                                          weight: .regular,
                                                          size: .p12,
                                                          alignment: .center),
                                         highlightedText: "pledgeOfHonor.checkbox.label.highlighted".localized,
                                         highlightedStyle: TextStyle(color: .tertiaryExtraDark,
                                                                     weight: .black,
                                                                     size: .p12,
                                                                     underlined: true))
    }
    
    @objc func labelTapped(_ sender: UITapGestureRecognizer) {
        showDonationPopUpMenu()
    }
    
    private func setupLabelAction() {
        let labelTap = UITapGestureRecognizer(target: self, action: #selector(self.labelTapped(_:)))
        checkBoxLabel.isUserInteractionEnabled = true
        checkBoxLabel.addGestureRecognizer(labelTap)
    }
    
    private func showDonationPopUpMenu() {
        guard let informationPopUp = InformationPopUpViewController.create() as? InformationPopUpViewController else {
            return
        }
        
        let popUp = PopUpViewController(contentViewController: informationPopUp)
        informationPopUp.setup(
            data: InformationPopUpData(title: "pledgeOfHonor.popUp.title".localized,
                                       description: "pledgeOfHonor.popUp.description".localized))
        popUp.show(onViewController: self)
        popUp.disableSwipeToDismiss = true
    }
    
    private func setupButtons() {
        confirmButton.isEnabled(false)
        confirmButton.set(title: type.buttonTitle, style: .primary)
        confirmButton.setImage(nil, for: .normal)
        confirmButton.onTap { [weak self] in
            guard let self = self else { return }
            switch type {
            case .chatbot:
                confirmSignature()
                goBackAction()
            case .directPayToVet:
                confirmSignature()
            }
        }
    }
    
    private func confirmSignature() {
        guard let signature = signatureView.getCroppedSignature() else { return }
        viewModel.update(signature: signature)
        clearSignatureView()
        
        if type == .directPayToVet {
            viewModel.manageDTPSignature()
            viewModel.sendForm()
        }
    }
    
    private func clearSignatureView() {
        signatureView.clear()
        confirmButton.isEnabled(false)
    }
    
    private func setupSignatureView() {
        signatureView.strokeColor = .primaryDarkest
        signatureView.delegate = self
    }
}

// MARK: - IB Actions
extension PledgeOfHonorViewController {
    
    @IBAction private func dismissTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
    
    @IBAction private func clearSignatureTouchUpInside(_ sender: UIButton) {
        clearSignatureView()
    }
}

// MARK: - SignatureViewDelegate
extension PledgeOfHonorViewController: SwiftSignatureViewDelegate {
    
    func swiftSignatureViewDidDrawGesture(_ view: ISignatureView, _ tap: UIGestureRecognizer) {
        viewModel.state = .beginToDraw
    }
    
    func swiftSignatureViewDidDraw(_ view: ISignatureView) {
        viewModel.state = .beginToDraw
    }
}
