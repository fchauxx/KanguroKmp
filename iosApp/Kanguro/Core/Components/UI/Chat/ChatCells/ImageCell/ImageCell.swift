import UIKit

class ImageCell: UITableViewCell, ChatCellProtocol {
    
    // MARK: - IB Outlets
    @IBOutlet private var photoBackgroundView: UIView!
    @IBOutlet var heartPinkBorderView: UIView!
    @IBOutlet var mainPhotoImageView: UIImageView!
    @IBOutlet private var heartImageView: UIImageView!
    @IBOutlet private var removeButton: UIButton!
    
    // MARK: - Stored Properties
    var didAnimate: Bool = false
    
    // MARK: - Actions
    var didTapRemoveAction: SimpleClosure = {}
}

// MARK: - Life Cycle
extension ImageCell {
    
    override func prepareForReuse() {
        mainPhotoImageView.image = nil
        heartPinkBorderView.isHidden = true
        didAnimate = false
    }
}

// MARK: - Setup
extension ImageCell {
    
    func setup(image: UIImage, isPetImage: Bool = true, isDeletable: Bool) {
        photoBackgroundView.roundCorners(corners: [.topRight, .topLeft, .bottomLeft], radius: 8)
        mainPhotoImageView.image = image
        removeButton.isHidden = !isDeletable
        if isPetImage { doLikeAnimation() }
    }
}

// MARK: - Private Methods
extension ImageCell {
    
    private func doLikeAnimation() {
        
        if heartPinkBorderView.isHidden == false { return }
        
        Timer.scheduledTimer(withTimeInterval: 1, repeats: false) { [weak self] _ in
            guard let self = self else { return }
            self.heartPinkBorderView.isHidden = false
            let animate = CABasicAnimation(keyPath: "transform.scale")
            animate.fromValue = 0.5
            animate.toValue = 1
            animate.duration = 0.5
            self.heartImageView.layer.add(animate, forKey: nil)
        }
    }
}

// MARK: - IB Actions
extension ImageCell {
    
    @IBAction private func closeButtonTouchUpInside(_ sender: UIButton) {
        didTapRemoveAction()
    }
}
