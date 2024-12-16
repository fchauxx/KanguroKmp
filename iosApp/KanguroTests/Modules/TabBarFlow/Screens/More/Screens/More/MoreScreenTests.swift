@testable import Kanguro
import XCTest
import SwiftUI
import KanguroUserDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroStorageData
import KanguroUserData
import Resolver
import KanguroFeatureFlagDomain

class MoreScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: MoreViewController!
    var viewModel: MoreViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        let keychain: SecureStorage = SecureStorageImplementation()
        let featureFlagMock: GetFeatureFlagBoolUseCaseProtocol = GetFeatureFlagBoolUseCaseMock()
        Resolver.register { keychain }
        Resolver.register { featureFlagMock }
        keychain.cleanAll()
        vc = MoreViewController()
        viewModel = MoreViewModel(productType: ProductType.petAndRentersProduct)
        viewModel.keychain = keychain
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Setup
extension MoreScreenTests {
    
    func testSetupLabels() {
        XCTAssertEqual(vc.titleLabel.text,
                       "more.moreActions.label".localized)
    }
    
    func testSetupActionCardLists() {
        XCTAssertEqual(vc.actionCardLists.first?.cardBGColor,
                       .white)
    }
    
    func testSetupPreferencesActionCardsList() {
        XCTAssertEqual(vc.preferencesActionCardsList.title,
                       "more.preferences.actionCardsList".localized.uppercased())
    }
    
    func testSetupSupportActionCardsList() {
        XCTAssertEqual(vc.supportActionCardsList.title,
                       "more.support.actionCardsList".localized.uppercased())
    }
    
    func testSetupLegalActionCardsList() {
        XCTAssertEqual(vc.legalActionCardsList.title,
                       "more.legal.actionCardsList".localized.uppercased())
    }
}

// MARK: - View Model
extension MoreScreenTests {
    
    func test_logout_WhenUserLogout_ShouldReturnNilUserAndUpdateStateToDataChanged() {
        let codableUser: RemoteUser = RemoteUser(id: "userTestId")
        viewModel.keychain.save(value: codableUser, key: KeychainStorageKey.user.rawValue)
        
        viewModel.logout()
        
        XCTAssertNil(viewModel.user)
        XCTAssertEqual(viewModel.state, .dataChanged)
    }
}
