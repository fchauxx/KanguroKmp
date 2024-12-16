@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain

class FAQScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var faqVC: FAQViewController!
    var viewModel: InformerViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        faqVC = FAQViewController()
        viewModel = InformerViewModel(types: [.FAQ])
        faqVC.viewModel = viewModel
        faqVC.loadView()
    }
}

// MARK: - View State
extension FAQScreenTests {
    
    func testRequestSucceeded() {
        let dataList = [InformerData(key: 0,
                                     value: "value",
                                     description: "description",
                                     type: .FAQ)]
        viewModel.informerDataList = dataList
        faqVC.changed(state: .requestSucceeded)
        XCTAssertEqual(dataList.count,
                       faqVC.scrollableInformerBaseView.informerData?.count)
    }
}

// MARK: - Setup
extension FAQScreenTests {
    
    func testSetupScrollableInformerBaseView() {
        faqVC.setupScrollableInformerBaseView()
        XCTAssertEqual(faqVC.scrollableInformerBaseView.titleLabelsData?.topTitle,
                       "faq.title.label".localized)
    }
}
