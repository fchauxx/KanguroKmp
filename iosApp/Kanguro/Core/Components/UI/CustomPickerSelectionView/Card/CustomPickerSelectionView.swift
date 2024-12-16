import Foundation
import UIKit

class CustomPickerSelectionView: BaseView, NibOwnerLoadable {

    // MARK: - IB Outlets
    @IBOutlet private var icon: UIImageView!
    @IBOutlet private var label: CustomLabel!
    @IBOutlet private var button: CustomButton!

    // MARK: - Stored Properties
    var data: CustomPickerData

    // MARK: - Actions
    var didTapCustomPickerCardAction: TextFieldCustomPickerDataClosure = { _ in }

    // MARK: - Initializers
    required init?(coder: NSCoder) {
        data = CustomPickerData(label: "Picker label")
        super.init(coder: coder)
        self.loadNibContent()
    }
    override init(frame: CGRect) {
        data = CustomPickerData(label: "Picker label")
        super.init(frame: frame)
        self.loadNibContent()
    }
}


    // MARK: - Privatee Methods
extension CustomPickerSelectionView {

    private func setupLayout() {
        setupLabels()
        setupViews()
        setupImages()
    }

    private func setupLabels() {
        label.set(text: data.label, style: TextStyle(color: .secondaryDarkest, size: .p16))
    }

    private func setupImages() {
        guard let image = data.icon else { return }
        icon.image = image
        icon.tintColor = .secondaryDarkest
    }

    private func setupViews() {
        if data.icon != nil {
            self.icon.isHidden = false
        } else {
            self.icon.isHidden = true
        }
    }

    func setup(data: CustomPickerData) {
        self.data = data
        setupLayout()
    }
}

extension CustomPickerSelectionView {

    @IBAction func didTappedPickerTouchUpInside(_ sender: UIButton) {
        didTapCustomPickerCardAction(data)
    }
}
