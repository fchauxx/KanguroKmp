import SwiftUI
import KanguroRentersDomain
import KanguroSharedDomain
import KanguroDesignSystem

fileprivate struct AdditionalCoverageTypeData {
    let title: String
    let icon: Image
    let popupType: AdditionalCoveragePopUpData
}

// MARK: - Views Computed Properties
public extension RenterCoverageDetailsView {
    
    fileprivate func getAdditionalCoverageTypeData(type: RenterAdditionalCoverageType) -> AdditionalCoverageTypeData {
        switch type {
        case .ReplacementCost:
            return .init(
                title: "coverageDetails.additionalCoverage.replacementCost".localized(lang),
                icon: Image.replacementCostIcon,
                popupType: .replacementCost
            )
        case .FraudProtection:
            return .init(
                title: "coverageDetails.additionalCoverage.fraudProtection".localized(lang),
                icon: Image.fraudProtectionIcon,
                popupType: .fraudProtection
            )
        case .WaterSewerBackup:
            return .init(
                title: "coverageDetails.additionalCoverage.waterSewerBackup".localized(lang),
                icon: Image.waterSewerBackupIcon,
                popupType: .waterSewer
            )

        }
    }
    
    var additionalCoveragesViewDataList: [AdditionalCoverageViewData]? {
        guard let coverages = viewModel.policy.additionalCoverages else { return nil }
        var list: [AdditionalCoverageViewData] = []
        coverages.forEach { coverage in
            guard let type = coverage.type,
                  let coverageLimit = coverage.coverageLimit,
                  let deductibleLimit = coverage.deductibleLimit else { return }

            let additionalCoverageTypeData = getAdditionalCoverageTypeData(type: type)

            let data = AdditionalCoverageViewData(
                icon: additionalCoverageTypeData.icon,
                title: additionalCoverageTypeData.title,
                coverageLimit: coverageLimit,
                deductibleLimit: deductibleLimit,
                footerText: "coverageDetails.additionalCoverage.footer".localized(lang),
                popupType: additionalCoverageTypeData.popupType
            )
            list.append(data)
        }
        return list
    }
    
    var dwellingTypeText: String {
        switch viewModel.policy.dwellingType {
        case .SingleFamily, .none:
            return "coverageDetails.dwellingType.singleFamily".localized(lang)
        case .MultiFamily:
            return "coverageDetails.dwellingType.MultiFamily".localized(lang)
        case .Apartment:
            return "coverageDetails.dwellingType.Apartment".localized(lang)
        case .Townhouse:
            return "coverageDetails.dwellingType.Townhouse".localized(lang)
        case .StudentHousing:
            return "coverageDetails.dwellingType.StudentHousing".localized(lang)
        }
    }
    
    var addressText: String? {
        guard let address = viewModel.policy.address,
              let streetNumber = address.streetNumber,
              let streetName = address.streetName,
              let city = address.city,
              let state = address.state else { return nil }
        return "\(streetNumber) \(streetName), \(city), \(state)"
    }
}
