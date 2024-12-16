import UIKit
import KanguroSharedDomain
import KanguroPetDomain

class PlaceholderPictureView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var petImageView: UIImageView!
    @IBOutlet private var buttonImageView: UIImageView!
    @IBOutlet private var bgView: UIView!
    
    // MARK: - Stored Properties
    private var type: PetType?
    
    // MARK: - Actions
    var didTapChangePictureAction: SimpleClosure = {}

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

// MARK: - Public Methods
extension PlaceholderPictureView {

    func setup(_ type: PetType) {
        let color: UIColor = (type == .Dog) ? .primaryDarkest : .tertiaryDark
        petImageView.image = (type == .Dog) ? UIImage(named: "defaultPicture-dog") : UIImage(named: "defaultPicture-cat")
        bgView.backgroundColor = color
        buttonImageView.tintColor = color
    }
}

// MARK: - IB Actions
extension PlaceholderPictureView {

    @IBAction func changePetPicture(_ sender: UIButton) {
        didTapChangePictureAction()
    }
}
