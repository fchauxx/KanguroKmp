import UIKit
import KanguroPetDomain

class FindVetTableViewCell: UITableViewCell {
    
    @IBOutlet private var locationLabel: CustomLabel!
    @IBOutlet private var emailLabel: CustomLabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }
    
    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)
        // Configure the view for the selected state
    }
}

// MARK: - Public methods
extension FindVetTableViewCell {
    
    public func setup(vetData: Veterinarian) {
        guard let vetEmail = vetData.email,
              let clinic = vetData.clinicName else { return }
        locationLabel.set(text: clinic,
                          style: TextStyle(color: .secondaryDarkest,
                                           size: .p16))
        emailLabel.set(text: vetEmail,
                       style: TextStyle(color: .secondaryMedium,
                                        size: .p16))
    }
}
