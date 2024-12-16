import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestInformerDataFactory {
    static func makeRemoteInformerData(
        key: Int,
        value: String? = "Some Information",
        description: String? = "Some Description",
        type: RemoteKanguroParameterType? = .FAQ,
        language: RemoteLanguage? = .English,
        isActive: Bool? = true
    ) -> RemoteInformerData {
        RemoteInformerData(key: key,
                           value: value,
                           description: description,
                           type: type,
                           language: language,
                           isActive: isActive)
    }
    
    static func makeInformerData(
        key: Int,
        value: String? = "Some Information",
        description: String? = "Some Description",
        type: KanguroParameterType? = .FAQ,
        language: Language? = .English,
        isActive: Bool? = true
    ) -> InformerData {
        InformerData(key: key,
                     value: value,
                     description: description,
                     type: type,
                     language: language,
                     isActive: isActive)
    }
}
