import Foundation
import KanguroUserDomain

public class SplashViewModel {

    // MARK: - Dependencies
    var user: User?
    let updateLocalUserService: UpdateUserUseCaseProtocol
    let refreshToken: RefreshTokenUseCaseProtocol

    init(
        user: User?,
        updateLocalUserService: UpdateUserUseCaseProtocol,
        refreshToken: RefreshTokenUseCaseProtocol
    ) {
        self.user = user
        self.updateLocalUserService = updateLocalUserService
        self.refreshToken = refreshToken
    }

    func checkTokenAndNavigate(loginNeeded: @escaping BoolClosure) {
        guard var user = self.user,
              user.isPasswordUpdateNeeded == false,
              let userRefreshToken = user.refreshToken
        else {
            loginNeeded(true)
            return
        }

        if !user.isTokenExpired {
            loginNeeded(false)
            return
        }

        let refreshTokenParameters = RefreshTokenParameters(refreshToken: userRefreshToken)

        refreshToken.execute(refreshTokenParameters) { [weak self] result in
            switch result {
            case .success(let token):
                user.updateToken(token: token)
                self?.updateLocalUserService.execute(user) { result in
                    switch result {
                    case .success:
                        loginNeeded(false)
                    case .failure(_):
                        loginNeeded(true)
                    }
                }
            case .failure(_):
                loginNeeded(true)
            }
        }
    }
}
