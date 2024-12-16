import Foundation
import KanguroSharedDomain

public protocol PetPolicyProtocol: PolicyProtocol {
    var pet: Pet { get set }
}
