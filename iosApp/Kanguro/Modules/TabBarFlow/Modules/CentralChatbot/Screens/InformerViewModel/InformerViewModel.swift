import UIKit
import Combine
import Resolver
import KanguroSharedDomain

class InformerViewModel {
    
    // MARK: - Dependencies
    @LazyInjected var parametersService: GetInformationTopicsUseCaseProtocol
    
    // MARK: - Published Properties
    @Published var state: DefaultViewState = .started
    
    // MARK: - Stored Properties
    var informerDataList: [InformerData] = []
    var types: [InformerDataType]
    var requestError = ""
    
    //MARK: - Computed Properties
    var typeImage: UIImage? {
        if types.count > 1 {
            return UIImage(named: "faq-global")
        } else {
            return UIImage(named: "faq")
        }
    }
    var vetAdviceDogList: [InformerData] {
        informerDataList.filter { $0.type == .VA_D }
    }
    var vetAdviceCatList: [InformerData] {
        informerDataList.filter { $0.type == .VA_C }
    }
    
    // MARK: - Initializers
    init(types: [InformerDataType]) {
        self.types = types
    }
}

// MARK: - Network
extension InformerViewModel {
    
    func getInformerDataList() {
        for type in types {
            let parameters = KanguroParameterModuleParameters(key: type.rawValue)
            parametersService.execute(parameters: parameters) { response in
                switch response {
                case .failure(let error):
                    self.requestError = error.errorMessage ?? "serverError.default".localized
                    self.state = .requestFailed
                case .success(let informerDataList):
                    self.informerDataList = informerDataList
                    self.state = .requestSucceeded
                }
            }
        }
    }
}
