import UIKit
import Combine
import Resolver
import KanguroAnalyticsDomain
import KanguroSharedDomain
import KanguroStorageDomain
import KanguroUserDomain

enum ProfileViewState {

    case started
    case dataChanged
    case logoutUser
    case loading
    case requestFailed
    case requestSucceeded
}

class ProfileViewModel {

    // MARK: - Dependencies
    @LazyInjected var keychain: SecureStorage
    @LazyInjected var updateProfileService: UpdateProfileUseCaseProtocol
    @LazyInjected var updatePasswordService: UpdatePasswordUseCaseProtocol
    @LazyInjected var analytics: KanguroAnalyticsModuleProtocol
    @LazyInjected var getLocalUserService: GetLocalUser
    @LazyInjected var updateLocalUserService: UpdateLocalUser

    // MARK: - Published Properties
    @Published var state: ProfileViewState = .started

    // MARK: - Stored Properties
    var profileData: ProfileData = ProfileData()
    var passwordData: PasswordData = PasswordData()
    var requestError = ""

    // MARK: - Computed Properties
    var user: User? {
        guard let user: User = try? getLocalUserService.execute().get() else { return nil }
        return user
    }
    var fullName: String {
        return (user?.givenName ?? "") + (user?.surname ?? "")
    }
}

// MARK: - Analytics
extension ProfileViewModel {

    func analyticsLogScreen() {
        analytics.analyticsLogScreen(screen: .Profile)
    }
}

// MARK: - Public Methods
extension ProfileViewModel {

    func update(firstName: String) {
        profileData.firstName = firstName
        state = .dataChanged
    }

    func update(lastName: String) {
        profileData.lastName = lastName
        state = .dataChanged
    }

    func update(phoneNumber: String) {
        profileData.phoneNumber = phoneNumber
        state = .dataChanged
    }

    func update(oldPassword: String) {
        passwordData.oldPassword = oldPassword
        state = .dataChanged
    }

    func update(password: String) {
        passwordData.password = password
        state = .dataChanged
    }

    func update(repeatedPassword: String) {
        passwordData.confirmPassword = repeatedPassword
        state = .dataChanged
    }

    func validatePasswordData() -> Bool {
        guard let oldPassword = passwordData.oldPassword,
              let password = passwordData.password,
              let confirmPassword = passwordData.confirmPassword else { return false }
        let isConfirmPasswordEqual = (confirmPassword == password)
        let isNewPasswordDifferent = (oldPassword != confirmPassword)
        return (isNewPasswordDifferent && isConfirmPasswordEqual)
    }

    func logout() {
        keychain.cleanAll()
        state = .logoutUser
    }
}

// MARK: - Network
extension ProfileViewModel {

    func updateProfileData() {
        state = .loading
        let parameters = ProfileParameters(givenName: profileData.firstName ?? user?.givenName,
                                           surname: profileData.lastName ?? user?.surname,
                                           phone: profileData.phoneNumber ?? user?.phone)
        updateProfileService.execute(parameters: parameters) { [weak self] response in
            guard let self else { return }
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                guard var user: User = try? getLocalUserService.execute().get() else {
                    assertionFailure("Could not fetch local user")
                    return
                }
                user.givenName = parameters.givenName
                user.surname = parameters.surname
                user.phone = parameters.phone
                self.updateLocalUserService.execute(user) { result in
                    guard let _ = try? result.get() else {
                        assertionFailure("Could not save user locally")
                        self.state = .requestFailed
                        return
                    }
                    self.state = .requestSucceeded
                }
            }
        }
    }

    func updatePassword() {
        state = .loading
        guard let email = user?.email,
              let currentPassword = passwordData.oldPassword,
              let newPassword = passwordData.confirmPassword else { return }
        let parameters = PasswordParameters(email: email,
                                            currentPassword: currentPassword,
                                            newPassword: newPassword)
        updatePasswordService.execute(parameters: parameters) { response in
            switch response {
            case .failure(let error):
                self.requestError = error.errorMessage ?? "serverError.default".localized
                self.state = .requestFailed
            case .success:
                self.state = .requestSucceeded
            }
        }
    }
}
