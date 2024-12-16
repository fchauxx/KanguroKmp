@testable import Kanguro
import XCTest
import SwiftUI
import KanguroSharedDomain

class VetAdviceScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var vetAdviceVC: VetAdviceViewController!
    var viewModel: InformerViewModel!
    let type: VetAdviceType = .dog
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vetAdviceVC = VetAdviceViewController(type: type)
        viewModel = InformerViewModel(types: [.VetAdvice])
        vetAdviceVC.viewModel = viewModel
        vetAdviceVC.loadView()
    }
}

// MARK: - View State
extension VetAdviceScreenTests {
    
    func testRequestSucceeded() {
        let dataList = [InformerData(key: 0,
                                     value: "value",
                                     description: "description",
                                     type: .VA_D)]
        viewModel.informerDataList = dataList
        vetAdviceVC.changed(state: .requestSucceeded)
        XCTAssertEqual(dataList.count,
                       vetAdviceVC.scrollableInformerBaseView.informerData?.count)
    }
}

// MARK: - Setup
extension VetAdviceScreenTests {
    
    func testSetupScrollableInformerBaseView() {
        vetAdviceVC.setupScrollableInformerBaseView()
        XCTAssertEqual(vetAdviceVC.scrollableInformerBaseView.titleLabelsData?.topTitle,
                       "vetAdvice.title.label".localized + type.rawValue)
    }
    
    func testSetupImages() {
        var image = UIImage(named: "vetAdvice-dog")
        vetAdviceVC.setupImages()
        XCTAssertEqual(vetAdviceVC.scrollableInformerBaseView.image, image)
        
        vetAdviceVC.type = .cat
        image = UIImage(named: "vetAdvice-cat")
        vetAdviceVC.setupImages()
        XCTAssertEqual(vetAdviceVC.scrollableInformerBaseView.image, image)
    }
}
