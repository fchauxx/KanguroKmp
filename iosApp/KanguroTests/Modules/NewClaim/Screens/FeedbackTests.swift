@testable import Kanguro
import XCTest
import SwiftUI
import KanguroPetDomain

class FeedbackTests: XCTestCase {
    
    //MARK: - Stored Properties
    var vc: FeedbackViewController!
    var viewModel: FeedbackViewModel!
    let claimId = "1234"
    
    //MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
        vc = FeedbackViewController()
        viewModel = FeedbackViewModel(claimId: claimId, feedbackData: PetFeedbackDataParameters())
        vc.viewModel = viewModel
        let _ = self.vc.view
    }
}

// MARK: - Setup
extension FeedbackTests {
    
    func testSendFeedbackAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.didTapSendFeedbackAction = action
        vc.didTapSendFeedbackAction()
        XCTAssertEqual(count, 1)
    }
    
    func testDidFinishAction() {
        var count = 0
        let action: SimpleClosure = { count += 1 }
        vc.didFinishFeedbackAction = action
        vc.didFinishFeedbackAction()
        XCTAssertEqual(count, 1)
    }
    
    func testSetupLabels() {
        let text = "feedback.title.label".localized
        XCTAssertEqual(vc.titleLabel.text, text)
    }
    
    func testSetupTextViews() {
        let text = "feedback.placeholder.textfield".localized
        XCTAssertEqual(vc.feedbackTextView.placeholder, text)
    }
    
    func testSetupButtons() {
        let text = "feedback.sendFeedback.button".localized
        XCTAssertEqual(vc.feedbackButton.originalText, text)
    }
    
    func testUpdateKanguroImage() {
        for value in 1...5 {
            vc.updateKanguroImage(rate: value)
            let image = UIImage(named: "feedback-\(value)")
            XCTAssertEqual(vc.kanguroImageView.image, image)
        }
    }
    
    func testUpdateKanguroTestReturn() {
        vc.updateKanguroImage(rate: 6)
        for value in 1...5 {
            let image = UIImage(named: "feedback-\(value)")
            XCTAssertNotEqual(vc.kanguroImageView.image, image)
        }
    }
}
