import UIKit

class VerticalReminderCardCell: BaseTableViewCell {
    
    // MARK: - Dependencies
    var viewModel: ReminderCardCellViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var contentBGView: UIView!
    @IBOutlet private var petNameLabel: CustomLabel!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var petPhotoImageView: UIImageView!
    @IBOutlet private var iconImageView: UIImageView!
    @IBOutlet private var leadingBgConstraint: NSLayoutConstraint!
    
    // MARK: - Actions
    var didTapMedicalHistoryAction: PetClosure = { _ in }
    var didTapCommunicationAction: StringClosure = { _ in }
}

// MARK: - Life Cycle
extension VerticalReminderCardCell {
    override func layoutSubviews() {
        super.layoutSubviews()
        setCellSpacement(bottom: 8)
    }
}

// MARK: - View State
extension VerticalReminderCardCell {
    
    private func changed(state: DefaultViewState) {
        
        switch state {
        case .started:
            viewModel.getPet()
        case .requestSucceeded:
            setupLayout()
        default:
            break
        }
    }
}

// MARK: - Setup
extension VerticalReminderCardCell {
    
    private func setupObserver() {
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    private func setupLayout() {
        setupLabels()
        setupViews()
        setupImages()
    }
    
    private func setupLabels() {
        let reminder = viewModel.reminder
        
        titleLabel.set(text: reminder.type.title,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        petNameLabel.set(text: viewModel.petName ?? "",
                         style: TextStyle(color: .secondaryDark, size: .p11))
        petNameLabel.setupToFitWidth()
        subtitleLabel.setupToFitWidth()
        
        var subtitleText: String = ""
        var subtitleColor: UIColor = .clear
        
        switch reminder.type {
        case .MedicalHistory:
            guard let text = reminder.remainingDays,
                  let isCriticalDateEvent = reminder.isCriticalDateEvent else { return }
            subtitleText = text
            subtitleColor = isCriticalDateEvent ? .negativeDarkest : .secondaryDark
        case .Claim:
            subtitleText = reminder.claimWarningType?.title ?? ""
            subtitleColor = .warningDarkest
        }
        
        subtitleLabel.set(text: subtitleText,
                          style: TextStyle(color: subtitleColor))
        subtitleLabel.isHidden = false
    }
    
    private func setupViews() {
        var backgroundColor: UIColor?
        var contentBgColor: UIColor?
        var leadingDistance: CGFloat = 4
        
        switch viewModel.reminder.type {
        case .MedicalHistory:
            guard let isCriticalDateEvent = viewModel.reminder.isCriticalDateEvent else { return }
            backgroundColor = isCriticalDateEvent ? .negativeDarkest : .secondaryLight
            contentBgColor = .white
            leadingDistance = isCriticalDateEvent ? 4 : 1
        case .Claim:
            backgroundColor = .warningDark
            contentBgColor = .warningLightest
        }
        
        contentView.backgroundColor = backgroundColor
        contentBGView.backgroundColor = contentBgColor
        leadingBgConstraint.constant = leadingDistance
    }
    
    private func setupImages() {
        let pet = viewModel.pet
        petPhotoImageView.setAsCircle()
        petPhotoImageView.kf.setImage(with: pet?.petPictureResource, placeholder: pet?.placeholderImage)
        
        switch viewModel.reminder.type {
        case .Claim:
            iconImageView.image = UIImage(named: "ic-warning")
            iconImageView.isHidden = false
        default:
            break
        }
    }
}

// MARK: - Public Methods
extension VerticalReminderCardCell {
    
    func setup(medicalHistoryAction: @escaping PetClosure, communicationAction: @escaping StringClosure) {
        self.didTapMedicalHistoryAction = medicalHistoryAction
        self.didTapCommunicationAction = communicationAction
        setupObserver()
    }
}

// MARK: - IB Actions
extension VerticalReminderCardCell {
    
    @IBAction private func reminderButtonTouchUpInside(_ sender: UIButton) {
        switch viewModel.reminder.type {
        case .MedicalHistory:
            guard let pet = viewModel.pet else { return }
            didTapMedicalHistoryAction(pet)
        case .Claim:
            guard let claimId = viewModel.reminder.claimId else { return }
            didTapCommunicationAction(claimId)
        }
    }
}
