import UIKit

enum ActionCardViewType {
    
    case normal(showTag: Bool? = false, tagType: TagType? = nil)
    case accordion(fontSize: TextBuilderFontSize)
    case document
    case file
}

enum TagType: String {
   case new
   case faster

   var tagLabel: String {
      switch self {
      case .new: return "common.new".localized
      case .faster: return "more.faster.tag".localized
      }
   }
}

class ActionCard: BaseView, NibOwnerLoadable {
    
    // MARK: - IB Outlets
    @IBOutlet private var backgroundView: UIView!
    @IBOutlet private var leadingImageView: UIImageView!
    @IBOutlet private var traillingImageView: UIImageView!
    @IBOutlet var leadingTitleLabel: CustomLabel!
    @IBOutlet var leadingSubtitleLabel: CustomLabel!
    @IBOutlet private var topDetailBarView: UIView!
    @IBOutlet private var tagView: TagView!

    // MARK: - Stored Properties
    var data: ActionCardData?
    var isExpanded: Bool = false

    // MARK: - Actions
    var didTapCardAction: SimpleClosure = {}

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        super.init(frame: frame)
        self.loadNibContent()
    }
}

// MARK: - Privatee Methods
extension ActionCard {

    private func setupLayout() {
        setupLabels()
        setupImages()
        setupViews()
    }

    private func setupLabels() {
        guard let data else { return }
        switch data.viewType {
        case .normal:
            leadingTitleLabel.set(text: data.leadingTitle,
                                  style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p16))
        case .accordion(let fontSize):
            leadingTitleLabel.set(text: data.leadingTitle,
                                  style: TextStyle(color: .secondaryDarkest, weight: .bold, size: fontSize))
        case .document:
            leadingTitleLabel.set(text: data.leadingTitle,
                                  style: TextStyle(color: .secondaryDarkest, size: .p16))
        case .file:
            leadingTitleLabel.set(text: data.leadingTitle,
                                  style: TextStyle(color: .secondaryDarkest, size: .p16))
            leadingSubtitleLabel.set(text: data.leadingSubtitle ?? "",
                                     style: TextStyle(color: .secondaryMedium, size: .p12))

        default:
            break
        }

        leadingTitleLabel.setupToFitWidth()
    }

    private func setupViews() {
        guard let data = data else { return }

        backgroundView.cornerRadius = 0

        switch data.viewType {
        case .normal(let showTag, let tagType):
            backgroundView.cornerRadius = 8
            if let showTag, showTag {
                tagView.setup(bgColor: .tertiaryLight,
                              text: tagType?.tagLabel.uppercased() ?? "common.new".localized.uppercased(),
                              textStyle: TextStyle(color: .tertiaryDarkest,
                                                   weight: .bold,
                                                   size: .p10))
                tagView.isHidden = false
            }
        case .document:
            topDetailBarView.isHidden = false
        case .file:
            leadingSubtitleLabel.isHidden = false
            backgroundView.cornerRadius = 8
        default:
            break
        }
    }

    private func setupImages() {
        guard let data else { return }

        var image: UIImage?
        switch data.viewType {
        case .normal:
            image = data.traillingImage
        case .accordion:
            image = (isExpanded ? UIImage(named: "ic-up-arrow") : UIImage(named: "ic-down-arrow"))
        default:
            break
        }

        setupImageView(traillingImageView, withImage: image)
        setupImageView(leadingImageView, withImage: data.leadingImage)
    }

    private func setupImageView(_ imageView: UIImageView, withImage image: UIImage?) {
        imageView.image = image
        imageView.tintColor = .secondaryMedium
        imageView.isHidden = (image == nil)
    }
}

// MARK: - Public Methods
extension ActionCard {
    
    func setup(actionCardData: ActionCardData, backgroundColor: CardBackgroundColor) {
        self.data = actionCardData
        self.backgroundView.backgroundColor = backgroundColor.color

        setupLayout()
    }
    
    func update(isExpanded: Bool) {
        self.isExpanded = isExpanded
        setupImages()
    }
}

// MARK: - IB Actions
extension ActionCard {
    
    @IBAction func tapActionTouchUpInside(_ sender: UIButton) {
        switch data?.dataType {
        case .standard:
            data?.didTapAction?()
        case .data(let document):
            data?.didTapDataAction?(document)
        case .directPayToVets(let pets):
            data?.didTapDTPActionCard?(pets)
        case .donation(let isDonating):
            data?.didTapDonatingAction?(isDonating)
        case .cloudDocumentPolicies(let pet):
            data?.didTapCloudActionCard?(pet)
        case .cloudDocumentPolicy(let policy, let pet):
            data?.didTapCloudPolicy?(policy,pet)
        case .cloudClaimList(let policyId, let claims, let selectedCloud):
            data?.didTapClaimAndInvoicesOption?(policyId, claims, selectedCloud)
        case .cloudPolicyAttachmentsFiles(let id, let policyAttachments, let selectedCloud):
            data?.didTapCloudPolicyAttachmentsFilesActionCard?(id, policyAttachments, selectedCloud)
        case .cloudPolicyDocumentsFiles(let id, let policyDocument, let selectedCloud):
            data?.didTapCloudPolicyDocumentsFilesActionCard?(id, policyDocument, selectedCloud)
        case .cloudClaimFiles(let policyId, let claimDocument, let selectedCloud):
            data?.didTapCloudClaimFilesActionCard?(policyId, claimDocument, selectedCloud)
        case .policyAttachmentFile(let policyAttachment):
            data?.didTapPolicyAttachmentFile?(policyAttachment)
        case .attachmentFile(let attachment):
            data?.didTapAttachmentFile?(attachment)
        default:
            break
        }
    }
}
