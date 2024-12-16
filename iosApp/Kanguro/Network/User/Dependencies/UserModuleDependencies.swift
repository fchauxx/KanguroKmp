import Foundation
import Resolver
import KanguroSharedDomain
import KanguroSharedData
import KanguroNetworkDomain
import KanguroUserDomain
import KanguroUserData
import KanguroStorageDomain

class UserModuleDependencies {
    typealias LocalUserRepositoryProtocol = GetUserRepositoryProtocol & UpdateUserRepositoryProtocol

    typealias RemoteUserRepositoryProtocol = GetUserRepositoryProtocol & UserRepositoryProtocol

    // MARK: - Stored Properties
    var localUserRepo: LocalUserRepositoryProtocol?
    var remoteUserRepo: RemoteUserRepositoryProtocol?
    var createOtpSendRequest: CreateOtpSendRequestUseCaseProtocol?
    var deleteUserAccount: DeleteUserAccountUseCaseProtocol?
    var getUserBankAccount: GetUserBankAccountUseCaseProtocol?
    var getHasAccessBlocked: GetIsUserAccessOkUseCaseProtocol?
    var getOtpValidation: GetOtpValidationUseCaseProtocol?
    var getRemoteUser: GetRemoteUser?
    var getLocalUser: GetLocalUser?
    var updateLocalUser: UpdateLocalUser?
    var login: LoginUseCaseProtocol?
    var refreshToken: RefreshTokenUseCaseProtocol?
    var updateUserBankAccount: UpdateUserBankAccountUseCaseProtocol?
    var updateFirebase: UpdateFirebaseTokenUseCaseProtocol?
    var patchCharity: PatchCharityUseCaseProtocol?

    // MARK: - Initializers
    public init(localUserRepo: LocalUserRepositoryProtocol? = nil,
                remoteUserRepo: RemoteUserRepositoryProtocol? = nil,
                createOtpSendRequest: CreateOtpSendRequestUseCaseProtocol? = nil,
                deleteUserAccount: DeleteUserAccountUseCaseProtocol? = nil,
                getBankAccount: GetUserBankAccountUseCaseProtocol? = nil,
                getHasAccessBlocked: GetIsUserAccessOkUseCaseProtocol? = nil,
                getOtpValidation: GetOtpValidationUseCaseProtocol? = nil,
                getRemoteUser: GetRemoteUser? = nil,
                getLocalUser: GetLocalUser? = nil,
                updateLocalUser: UpdateLocalUser? = nil,
                login: LoginUseCaseProtocol? = nil,
                refreshToken: RefreshTokenUseCaseProtocol? = nil,
                updateBankAccount: UpdateUserBankAccountUseCaseProtocol? = nil,
                updateFirebase: UpdateFirebaseTokenUseCaseProtocol? = nil,
                patchCharity: PatchCharityUseCaseProtocol? = nil) {
        self.localUserRepo = localUserRepo
        self.remoteUserRepo = remoteUserRepo
        self.createOtpSendRequest = createOtpSendRequest
        self.deleteUserAccount = deleteUserAccount
        self.getUserBankAccount = getBankAccount
        self.getHasAccessBlocked = getHasAccessBlocked
        self.getOtpValidation = getOtpValidation
        self.getRemoteUser = getRemoteUser
        self.getLocalUser = getLocalUser
        self.updateLocalUser = updateLocalUser
        self.login = login
        self.refreshToken = refreshToken
        self.updateUserBankAccount = updateBankAccount
        self.updateFirebase = updateFirebase
        self.patchCharity = patchCharity
    }
}

// MARK: - ModuleDependencies
extension UserModuleDependencies: ModuleDependencies {
    
    func setupDependencies() {
        let network: NetworkProtocol = Resolver.resolve()
        let remoteUserRepo = self.remoteUserRepo ?? RemoteUserRepository(network: network)
        Resolver.register { remoteUserRepo }

        let secureStorage: SecureStorage = Resolver.resolve()
        let localUserRepo = LocalUserRepository(storage: secureStorage)
        Resolver.register { localUserRepo }

        let createOtpSendRequest = self.createOtpSendRequest ?? CreateOtpSendRequest(userRepo: remoteUserRepo)
        Resolver.register { createOtpSendRequest }

        let getRemoteUser = self.getRemoteUser ?? GetRemoteUser(remoteUserRepo: remoteUserRepo)
        Resolver.register { getRemoteUser }

        let getLocalUser = self.getLocalUser ?? GetLocalUser(localUserRepo: localUserRepo)
        Resolver.register { getLocalUser }

        let updateLocalUser = self.updateLocalUser ?? UpdateLocalUser(localUserRepo: localUserRepo)
        Resolver.register { updateLocalUser }

        let deleteUserAccount = self.deleteUserAccount ?? DeleteUserAccount(userRepo: remoteUserRepo)
        Resolver.register { deleteUserAccount }
        
        let getUserBankAccount = self.getUserBankAccount ?? GetUserBankAccount(userRepo: remoteUserRepo)
        Resolver.register { getUserBankAccount }
        
        let getHasAccessBlocked = self.getHasAccessBlocked ?? GetIsUserAccessOk(userRepo: remoteUserRepo)
        Resolver.register { getHasAccessBlocked }
        
        let getOtpValidation = self.getOtpValidation ?? GetOtpValidation(userRepo: remoteUserRepo)
        Resolver.register { getOtpValidation }
        
        let login = self.login ?? Login(userRepo: remoteUserRepo)
        Resolver.register { login }
        
        let refreshToken = self.refreshToken ?? RefreshToken(userRepo: remoteUserRepo)
        Resolver.register { refreshToken }
        
        let updateUserBankAccount = self.updateUserBankAccount ?? UpdateUserBankAccount(userRepo: remoteUserRepo)
        Resolver.register { updateUserBankAccount }
        
        let updateFirebaseToken = self.updateFirebase ?? UpdateFirebaseToken(userRepo: remoteUserRepo)
        Resolver.register { updateFirebaseToken }
        
        let patchCharity = self.patchCharity ?? PatchCharity(userRepo: remoteUserRepo)
        Resolver.register { patchCharity }
    }
}
