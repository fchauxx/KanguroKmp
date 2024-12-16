@testable import Kanguro
import XCTest
import SwiftUI
import KanguroUserDomain
import KanguroStorageData
import KanguroStorageDomain
import Resolver
import KanguroUserData

class SettingsScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: SettingsViewController!
    var viewModel: SettingsViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        let keychain: SecureStorage = SecureStorageImplementation()
        keychain.cleanAll()
        Resolver.register { keychain }
        continueAfterFailure = false
        vc = SettingsViewController()
        viewModel = SettingsViewModel()
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Setup
extension SettingsScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.titleLabel.text,
                       "settings.title.label".localized)
    }
    
    func testSetupSelectionViews() {
        XCTAssertEqual(vc.englishSelectionView.data?.title,
                       viewModel.initialLanguage.title)
        
        XCTAssertEqual(vc.englishSelectionView.data?.isSelected,
                       viewModel.initialLanguage == .English)
    }
    
    func testSetupActions() {
        let remoteUser = RemoteUser(id: "id")
        viewModel.keychain.save(value: remoteUser, key: KeychainStorageKey.user.rawValue)
        
        vc.didTapSelectionViewAction(1)
        DispatchQueue.main.asyncAfter(
            deadline: .now() + 0.05,
            execute: {
                if let user: RemoteUser = self.viewModel.keychain.get(key: KeychainStorageKey.user.rawValue) {
                    XCTAssertEqual(user.language, "Spanish")
                } else {
                    XCTFail("No User")
                }
            }
        )

        vc.didTapSelectionViewAction(0)
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.05, execute: {
            guard let user: RemoteUser = self.viewModel.keychain.get(key: KeychainStorageKey.user.rawValue) else { return XCTFail("No User") }
            XCTAssertEqual(user.language, "English")
        })
        
        viewModel.keychain.remove(key: KeychainStorageKey.user.rawValue)
    }
}
