@testable import Kanguro
import XCTest
import KanguroSharedDomain
import SwiftUI

class PetParentsScreenTests: XCTestCase {
    
    // MARK: - Stored Properties
    var petParentsVC: PetParentsViewController!
    var viewModel: InformerViewModel!
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        petParentsVC = PetParentsViewController()
        viewModel = InformerViewModel(types: [.NewPetParent])
        petParentsVC.viewModel = viewModel
        petParentsVC.loadView()
    }
}

// MARK: - View State
extension PetParentsScreenTests {
    
    func testRequestSucceeded() {
        let dataList = [InformerData(key: 0,
                                     value: "value",
                                     description: "description",
                                     type: .NPP_D)]
        viewModel.informerDataList = dataList
        petParentsVC.changed(state: .requestSucceeded)
        XCTAssertEqual(dataList.count,
                       petParentsVC.scrollableInformerBaseView.informerData?.count)
    }
}

// MARK: - Setup
extension PetParentsScreenTests {
    
    func testSetupScrollableInformerBaseView() {
        petParentsVC.setupScrollableInformerBaseView()
        XCTAssertEqual(petParentsVC.scrollableInformerBaseView.titleLabelsData?.topTitle,
                       "petParents.title.label".localized)
    }
}
