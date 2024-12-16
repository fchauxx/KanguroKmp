import UIKit

class FileCell: UITableViewCell {
    
    // MARK: - IB Outlets
    
    @IBOutlet private var fileImageView: UIImageView!
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var editionButtonView: EditionButtonView!
    
    // MARK: - Stored Properties
    private var fileName: String?
    private var fileSize: Int?
    
    // MARK: - Actions
    var didTapEditionButtonAction: SimpleClosure = {}
}

// MARK: - Setup
extension FileCell {
    
    func setupLayout() {
        setupLabels()
        setupImages()
        setupEditionButtonView()
    }
    
    func setupLabels() {
        guard let fileName = fileName,
              let fileSize = fileSize else { return }
        titleLabel.set(text: fileName, style: TextStyle(color: .secondary, size: .p16))
        subtitleLabel.set(text: "\(fileSize)kb", style: TextStyle(color: .secondary))
    }
    
    func setupImages() {
        fileImageView.image = UIImage(named: "attachment")
    }
    
    private func setupEditionButtonView() {
        editionButtonView.setup(type: .excludable)
        editionButtonView.update(action: didTapEditionButtonAction)
    }
}

// MARK: - Public Methods
extension FileCell {
    
    func update(fileName: String, fileSize: Int) {
        self.fileName = fileName
        self.fileSize = fileSize
    }
}
