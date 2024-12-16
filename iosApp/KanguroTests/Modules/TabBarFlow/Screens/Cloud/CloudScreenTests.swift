@testable import Kanguro
import XCTest
import SwiftUI

class CloudScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vc: CloudViewController!
    var viewModel: CloudViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = CloudViewController(type: .base)
        viewModel = CloudViewModel()
        vc.viewModel = viewModel
        vc.loadView()
        vc.viewDidLoad()
    }
}

// MARK: - Setup
extension CloudScreenTests {
    
    func testSetupLabelsHidden() {
        XCTAssertTrue(vc.breadcrumb.isHidden)
    }
}
