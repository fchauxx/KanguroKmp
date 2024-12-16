//
//  UpgradeRequiredViewController.swift
//  Kanguro
//
//  Created by Rodrigo Okido on 18/10/22.
//

import UIKit

class UpdateRequiredViewController: BaseViewController {
    
    // MARK: - Dependencies
    var viewModel: UpdateRequiredViewModel!
        
    // MARK: - IB Outlets
    @IBOutlet var updateRequiredTitle: CustomLabel!
    @IBOutlet var updateRequiredDescription: CustomLabel!
    @IBOutlet var updateRequiredButton: CustomButton!
}

// MARK: - Life Cycle
extension UpdateRequiredViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
        observe(viewModel.$state) { [weak self] state in
            guard let self = self else { return }
            self.changed(state: state)
        }
    }
}

// MARK: - View State
extension UpdateRequiredViewController {
    
    func changed(state: DefaultViewState) {
        switch state {
        case .started:
            setupLayout()
        default:
            break
        }
    }
}

// MARK: - Setup
extension UpdateRequiredViewController {
    
    private func setupLayout() {
        setupLabels()
        setupButtons()
    }
    
    private func setupLabels() {
        updateRequiredTitle.set(text: "updateRequired.title.label".localized,
                                style: TextStyle(color: .secondaryDarkest, weight: .bold, size: .h24,
                                                 alignment: .center))
        updateRequiredDescription.set(text: "updateRequired.description.label".localized,
                                      style: TextStyle(color: .secondaryDark, size: .p16, alignment: .center))
    }
    
    private func setupButtons() {
        updateRequiredButton.set(title: "updateRequired.button.label".localized, style: .primary)
        updateRequiredButton.setImage(nil, for: .normal)
        updateRequiredButton.onTap {
            self.viewModel.openAppStore()
        }
    }
}

