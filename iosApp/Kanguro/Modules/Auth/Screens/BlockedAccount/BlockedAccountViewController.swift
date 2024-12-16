//
//  BlockedAccountViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 23/09/22.
//

import UIKit

class BlockedAccountViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: BlockedAccountViewModel!
    
    // MARK: - Actions
    var goBackAction: SimpleClosure = {}
    
    // MARK: - IB Outlets
    @IBOutlet var accountProblemMessageLabel: CustomLabel!
    @IBOutlet var contactUsLabel: CustomLabel!
    @IBOutlet var mailButton: CustomButton!
    @IBOutlet var phoneButton: CustomButton!
}

// MARK: - Life Cycle
extension BlockedAccountViewController {
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setupLayout()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        goBackAction()
    }
}

// MARK: - View State
extension BlockedAccountViewController {
    
    func changed(state: DefaultViewState) {
        switch state {
        case .started:
            viewModel.analyticsLogScreen()
        default:
            break
        }
    }
}

// MARK: - Setup
extension BlockedAccountViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
        setupActions()
    }
    
    private func setupButtons() {
        mailButton.set(title: "profile.delete.label".localized, style: .tertiary)
        phoneButton.set(title: "forgotPassword.phone".localized, style: .tertiary)
        
        let mailIcon = UIImage(named: "ic-mail")?.withRenderingMode(.alwaysTemplate)
        mailButton.setImage(mailIcon, for: .normal)
        let phoneIcon = UIImage(named: "ic-phone")?.withRenderingMode(.alwaysTemplate)
        phoneButton.setImage(phoneIcon, for: .normal)
        
        mailButton.imageView?.tintColor = .tertiaryDarkest
        phoneButton.imageView?.tintColor = .tertiaryDarkest
        
        mailButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 10)
        phoneButton.imageEdgeInsets = UIEdgeInsets(top: 0, left: 0, bottom: 0, right: 10)
    }
    
    private func setupActions() {
        mailButton.onTap {
            self.viewModel.openEmail()
        }
        phoneButton.onTap {
            self.viewModel.callSupport()
        }
    }
    
    private func setupLabels() {
        accountProblemMessageLabel.set(text: "blockedAccount.problemMessage.label".localized,
                                       style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .p21,
                                        alignment: .center))
        contactUsLabel.set(text: "blockedAccount.contactUs.label".localized.uppercased(),
                           style: TextStyle(color: .secondaryDark, size: .p11, alignment: .center))
    }
}

// MARK: - IB Actions
extension BlockedAccountViewController {
    
    @IBAction func closeButtonTouchUpInside(_ sender: Any) {
        self.dismiss(animated: true)
    }
}
