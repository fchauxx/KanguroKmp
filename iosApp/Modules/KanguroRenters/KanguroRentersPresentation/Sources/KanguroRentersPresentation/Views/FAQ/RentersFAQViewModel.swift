import UIKit
import Combine
import Resolver
import KanguroSharedDomain
import SwiftUI

public class RentersFAQViewModel: ObservableObject {
        
    // MARK: - Dependencies
    @LazyInjected var parametersService: GetInformationTopicsUseCaseProtocol
        
    // MARK: - Stored Properties
    @Published var informerDataList: [InformerData] = []
    private var type: InformerDataType
    
    //MARK: - Wrapped Properties
    @Published public var isLoading = true
    @Published var requestError: String = ""

    // MARK: - Initializers
    public init(type: InformerDataType) {
        self.type = type
    }
}

// MARK: - Network
extension RentersFAQViewModel {
    
    func getInformerDataList() {
        let parameters = KanguroParameterModuleParameters(key: type.rawValue)
        parametersService.execute(parameters: parameters) { response in
            switch response {
            case .failure(let error):
                self.isLoading = false
                self.requestError = error.errorMessage ?? "error"
            case .success(let informerDataList):
                self.isLoading = false
                self.informerDataList = informerDataList
            }
        }
    }
}
