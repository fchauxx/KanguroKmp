//
//  HomeFAQViewModel.swift
//
//
//  Created by Mateus Vagner on 26/04/24.
//

import Foundation
import Combine
import Resolver
import KanguroSharedDomain

public class HomeFAQViewModel: ObservableObject {
    // MARK: - Dependencies
    @LazyInjected var parametersService: GetInformationTopicsUseCaseProtocol
    
    // MARK: - Stored Properties
    @Published var vetInformerDatas: [InformerData] = []
    @Published var rentersInformerDatas: [InformerData] = []
    
    //MARK: - Wrapped Properties
    @Published public var isLoading = true
    @Published var requestError: String = ""
    
    // MARK: - Initializers
    public init() {
        
    }
    
    func getInformerData() {
        getRentersInformationData()
        getVetInformationData()
    }
    
    private func getRentersInformationData() {
        let rentersParameters = KanguroParameterModuleParameters(key: InformerDataType.FAQRenters.rawValue)
        
        parametersService.execute(parameters: rentersParameters) { response in
            switch response {
            case .failure(let error):
                self.isLoading = false
                self.requestError = error.errorMessage ?? "error"
            case .success(let rentersInformerDatas):
                self.rentersInformerDatas = rentersInformerDatas
            }
        }
    }
    
    private func getVetInformationData() {
        let petParameters = KanguroParameterModuleParameters(key: InformerDataType.FAQ.rawValue)
        
        parametersService.execute(parameters: petParameters) { response in
            switch response {
            case .failure(let error):
                self.isLoading = false
                self.requestError = error.errorMessage ?? "error"
            case .success(let vetInformerDatas):
                self.isLoading = false
                self.vetInformerDatas = vetInformerDatas
            }
        }
    }
}
