//
//  SupportCauseViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 18/01/23.
//

import UIKit

class SupportCauseViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: SupportCauseViewModel!
    
    // MARK: - IB Outlets
    @IBOutlet private var titleLabel: CustomLabel!
    @IBOutlet private var subtitleLabel: CustomLabel!
    @IBOutlet private var currentlySupportLabel: CustomLabel!
    @IBOutlet private var donationCauseCardView: DonationCauseCardView!
    @IBOutlet private var donatedTitleLabel: CustomLabel!
    @IBOutlet private var donatedAmountLabel: CustomLabel!
    @IBOutlet private var donationValueView: UIView!
    
    // MARK: Actions
    var goBackAction: SimpleClosure = {}
    var didTapChooseToChangeCause: SimpleClosure = {}
}


// MARK: - Life Cycle
extension SupportCauseViewController {
    
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
extension SupportCauseViewController {
    
    private func changed(state: SupportCauseViewState) {
        
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
            viewModel.getActualCharity()
            viewModel.getDonatedValue()
        case .requestFailed:
            showSimpleAlert(message: viewModel.requestError)
        case .requestActualDonationSucceeded:
            setupActualUserSupportCause()
        case .requestDonatedValueSucceeded:
            setupBottomDonationView()
        default:
            break
        }
    }
}

// MARK: - Setup
extension SupportCauseViewController {
    
    private func setupLayout() {
        setupLabels()
    }
    
    private func setupLabels() {
        titleLabel.set(text: "supportCause.title.label".localized,
                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h32, font: .raleway))
        subtitleLabel.set(text: "supportCause.subtitle.label".localized,
                          style: TextStyle(color: .neutralDark, size: .p16))
        currentlySupportLabel.set(text: "supportCause.currentCause.label".localized.uppercased(),
                                  style: TextStyle(color: .neutralMedium, size: .p12))
    }
    
    private func setupActualUserSupportCause() {
        guard let actualDonation = viewModel.actualUserDonation else { return }
        donationCauseCardView.setup(data: actualDonation, isToChangeCause: true)
        donationCauseCardView.didTapChooseToChangeCause = { [weak self] in
            guard let self else { return }
            self.didTapChooseToChangeCause()
        }
    }
    
    private func setupBottomDonationView() {
        let value = viewModel.donatedValue
        donatedTitleLabel.set(text: value != nil ? "donation.donated.label".localized : "donation.placeholder.label".localized,
                              style: TextStyle(color: .white, weight: .bold, size: .h24, font: .lato, alignment: .center, lines: 2))
        if value != nil {
            donatedAmountLabel.isHidden = false
            donatedAmountLabel.set(text: "$\(value ?? 0)",
                                   style: TextStyle(color: .white, weight: .bold, size: .h24, font: .lato))
        }
    }
}

// MARK: - IB Actions
extension SupportCauseViewController {
    
    @IBAction private func goBackTouchUpInside(_ sender: UIButton) {
        goBackAction()
    }
}

