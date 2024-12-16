//
//  LanguageHandler.swift
//  Kanguro
//
//  Created by Natasha Rebelo on 20/03/24.
//

import Foundation
import Resolver
import KanguroUserDomain
import KanguroSharedDomain
import KanguroStorageDomain

protocol LanguageHandlerProtocol {
    func updateLanguageIfNeeded(
        with language: Language,
        completion: @escaping ((Result<Void, Error>) -> ())
    )
}

class LanguageHandler: LanguageHandlerProtocol {

    @LazyInjected private var getLocalUserService: GetLocalUser
    @LazyInjected private var updateLocalUserService: UpdateLocalUser
    @LazyInjected private var userDefaults: Storage

    func updateLanguageIfNeeded(
        with language: Language,
        completion: @escaping ((Result<Void, Error>) -> ())
    ) {
        guard let user: User = try? getLocalUserService.execute().get(),
              let savedLanguage = user.language else {
            completion(.failure(RequestError(errorType: .notFound,
                                             errorMessage: "User language is not set locally.")))
            return
        }

        if language != savedLanguage {
            updateLanguage(with: language) { result in
                completion(result)
            }
        }
    }

    private func updateLanguage(
        with language: Language,
        completion: @escaping ((Result<Void, Error>) -> ())
    ) {
        guard var user: User = try? getLocalUserService.execute().get() else { return }
        user.language = language
        updateLocalUserService.execute(user) { result in
            if let _ = try? result.get() {
                NotificationCenter.default.post(
                    name: .languageUpdate,
                    object: nil,
                    userInfo: ["language": language.rawValue]
                )

                completion(.success(()))
            } else {
                assertionFailure("Could not update local user")
            }
        }
    }
}
