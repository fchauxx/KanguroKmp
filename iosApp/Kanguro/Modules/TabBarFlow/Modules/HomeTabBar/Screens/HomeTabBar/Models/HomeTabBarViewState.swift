import Foundation

enum HomeViewState {
    
    case started
    case loading
    case dataChanged
    case validVersion
    case invalidVersion
    case blockedUser
    case allowedUser
    case requestFailed
    case noPoliciesFound

    case goToHome
    case userNotFound
}

enum PetsViewState {
    
    case started
    case loadingPetPicture
    case petProductNotFound
    case goToPetAdditionalInfoFlow
    case didSetPetPolicies
    case getPoliciesSucceeded
}

enum RentersViewState {
    
    case started
    case renterProductNotFound
    case getRenterPoliciesSucceeded
}
