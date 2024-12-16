import Foundation
import Resolver

final class AppDependencies {
    
    static func setup(dependencies: [ModuleDependencies]? = nil) {
        
        let dependencies = dependencies ?? [CoreModuleDependencies(),
                                            StorageModuleDependencies(),
                                            NetworkModuleDependencies(),
                                            FeatureFlagModuleDependencies(),
                                            UserModuleDependencies(),
                                            ChatbotModuleDependencies(),
                                            PetModuleDependencies(),
                                            VetModuleDependencies(),
                                            TemporaryFilesModuleDependencies(),
                                            ResetPasswordModuleDependencies(),
                                            PetClaimsModuleDependencies(),
                                            RemindersModuleDependencies(),
                                            LanguageModuleDependencies(),
                                            PoliciesModuleDependencies(),
                                            ReferralCodeModuleDependencies(),
                                            ProfileModuleDependencies(),
                                            PasswordModuleDependencies(),
                                            TermsOfServiceModuleDependencies(),
                                            KanguroParametersModuleDependencies(),
                                            KeycloakModuleDependencies(),
                                            BankModuleDependencies(),
                                            AnalyticsModuleDependencies(),
                                            NotificationsModuleDependencies(),
                                            VersionModuleDependencies(),
                                            CharityModuleDependencies(),
                                            CloudDocumentModuleDependencies(),
                                            ExternalLinksModuleDependencies(),
                                            ContactInformationModuleDependencies()]
        for module in dependencies {
            module.setupDependencies()
        }
    }
}
