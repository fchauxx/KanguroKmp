import SwiftUI
import Resolver
import Combine
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroPetDomain

public class PetFeedbackViewModel: ObservableObject {

    enum PetFeedbackViewState {
        case started
        case loading
        case requestFailed
        case requestSucceeded
    }

    // MARK: - Dependencies
    @LazyInjected var feedbackService: PetUpdateFeedbackUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol

    // MARK: - Wrapped Properties
    @Environment(\.appLanguageValue) var language
    // MARK: - Published Properties
    @Published var state: PetFeedbackViewState = .started
    @Published var requestError = ""

    // MARK: - Stored Properties
    var claimId: String
    var lang: String {
        language.rawValue
    }

    // MARK: - Closures
    let onFinish: SimpleClosure

    // MARK: - Initializers
    public init(claimId: String, onFinish: @escaping SimpleClosure) {
        self.claimId = claimId
        self.onFinish = onFinish
    }
}

// MARK: - Analytics
extension PetFeedbackViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Feedback)
    }
}

// MARK: - Network
extension PetFeedbackViewModel {

    func sendFeedback(rate: Int, description: String) {
        state = .loading
        let parameters = PetClaimParameters(id: claimId)
        let data = PetFeedbackDataParameters(feedbackRate: rate, feedbackDescription: description)

        feedbackService.execute(parameters, feedback: data) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverDefault.error".localized(self.lang)
                self.state = .requestFailed
            case .success:
                self.state = .requestSucceeded
            }
        }
    }
}
