import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

class FeedbackViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var feedbackService: PetUpdateFeedbackUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var data: PetFeedbackDataParameters
    var claimId: String
    var requestError = ""
    
    var sliderData: CustomSliderData {
        return CustomSliderData(wordsList: ["feedback.awful".localized,
                                            "feedback.notGood".localized,
                                            "feedback.ok".localized,
                                            "feedback.good".localized,
                                            "feedback.awesome".localized])
    }
    
    // MARK: - Initializers
    init(claimId: String, feedbackData: PetFeedbackDataParameters) {
        self.claimId = claimId
        data = feedbackData
    }
}

// MARK: - Analytics
extension FeedbackViewModel {
    
    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Feedback)
    }
}

// MARK: - Public Methods
extension FeedbackViewModel {
    
    func update(feedbackDescription: String) {
        self.data.feedbackDescription = feedbackDescription
    }
    
    func update(feedbackRate: Int) {
        self.data.feedbackRate = feedbackRate
        state = .dataChanged
    }
}

// MARK: - Network
extension FeedbackViewModel {
    
    func sendFeedback() {
        state = .loading
        let parameters = PetClaimParameters(id: claimId)
        feedbackService.execute(parameters, feedback: data) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverDefault.error".localized
                self.state = .requestFailed
            case .success:
                self.state = .requestSucceeded
            }
        }
    }
}
