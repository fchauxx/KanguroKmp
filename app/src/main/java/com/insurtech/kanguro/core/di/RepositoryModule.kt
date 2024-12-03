package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.core.repository.chatbot.ChatbotRepository
import com.insurtech.kanguro.core.repository.chatbot.IChatbotRepository
import com.insurtech.kanguro.core.repository.claims.ClaimsRepository
import com.insurtech.kanguro.core.repository.claims.IClaimsRepository
import com.insurtech.kanguro.core.repository.pets.IPetsRepository
import com.insurtech.kanguro.core.repository.pets.PetsRepository
import com.insurtech.kanguro.core.repository.policy.IPolicyRepository
import com.insurtech.kanguro.core.repository.policy.PolicyRepository
import com.insurtech.kanguro.core.repository.user.IUserRepository
import com.insurtech.kanguro.core.repository.user.UserRepository
import com.insurtech.kanguro.data.repository.IAdvicesRepository
import com.insurtech.kanguro.data.repository.IBackendVersionRepository
import com.insurtech.kanguro.data.repository.ICharityRepository
import com.insurtech.kanguro.data.repository.ICloudDocumentRepository
import com.insurtech.kanguro.data.repository.IContactInformationRepository
import com.insurtech.kanguro.data.repository.IExternalLinksRepository
import com.insurtech.kanguro.data.repository.ILoginRepository
import com.insurtech.kanguro.data.repository.IPreferencesRepository
import com.insurtech.kanguro.data.repository.IRefactoredBanksRepository
import com.insurtech.kanguro.data.repository.IRefactoredClaimRepository
import com.insurtech.kanguro.data.repository.IRefactoredPetRepository
import com.insurtech.kanguro.data.repository.IRefactoredPolicyRepository
import com.insurtech.kanguro.data.repository.IRefactoredUserRepository
import com.insurtech.kanguro.data.repository.IRentersChatbotRepository
import com.insurtech.kanguro.data.repository.IRentersPolicyEndorsementRepository
import com.insurtech.kanguro.data.repository.IRentersPolicyRepository
import com.insurtech.kanguro.data.repository.IRentersPricingRepository
import com.insurtech.kanguro.data.repository.IRentersScheduledItemsRepository
import com.insurtech.kanguro.data.repository.ITemporaryFileRepository
import com.insurtech.kanguro.data.repository.ITermRepository
import com.insurtech.kanguro.data.repository.IVetPlacesRepository
import com.insurtech.kanguro.data.repository.IVeterinarianRepository
import com.insurtech.kanguro.data.repository.impl.AdvicesRepository
import com.insurtech.kanguro.data.repository.impl.BackendVersionRepository
import com.insurtech.kanguro.data.repository.impl.CharityRepository
import com.insurtech.kanguro.data.repository.impl.CloudDocumentRepository
import com.insurtech.kanguro.data.repository.impl.ContactInformationRepository
import com.insurtech.kanguro.data.repository.impl.ExternalLinksRepository
import com.insurtech.kanguro.data.repository.impl.LoginRepository
import com.insurtech.kanguro.data.repository.impl.PreferencesRepository
import com.insurtech.kanguro.data.repository.impl.RefactoredBanksRepository
import com.insurtech.kanguro.data.repository.impl.RefactoredClaimRepository
import com.insurtech.kanguro.data.repository.impl.RefactoredPetRepository
import com.insurtech.kanguro.data.repository.impl.RefactoredPolicyRepository
import com.insurtech.kanguro.data.repository.impl.RefactoredUserRepository
import com.insurtech.kanguro.data.repository.impl.RentersChatbotRepository
import com.insurtech.kanguro.data.repository.impl.RentersPolicyEndorsementRepository
import com.insurtech.kanguro.data.repository.impl.RentersPolicyRepository
import com.insurtech.kanguro.data.repository.impl.RentersPricingRepository
import com.insurtech.kanguro.data.repository.impl.RentersScheduledItemsRepository
import com.insurtech.kanguro.data.repository.impl.TemporaryFileRepository
import com.insurtech.kanguro.data.repository.impl.TermRepository
import com.insurtech.kanguro.data.repository.impl.VetPlacesRepository
import com.insurtech.kanguro.data.repository.impl.VeterinarianRepository
import com.insurtech.kanguro.domain.chatbot.newchatbot.NewChatBotRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindPetsRepository(r: PetsRepository): IPetsRepository

    @Singleton
    @Binds
    abstract fun bindClaimsRepository(r: ClaimsRepository): IClaimsRepository

    @Binds
    @Singleton
    abstract fun bindChatbotRepository(r: ChatbotRepository): IChatbotRepository

    @Binds
    @Singleton
    @LocalChatbotRepository
    abstract fun bindNewChatBotRepository(r: NewChatBotRepository): IChatbotRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(r: UserRepository): IUserRepository

    @Singleton
    @Binds
    abstract fun bindPolicyRepository(r: PolicyRepository): IPolicyRepository

    @Singleton
    @Binds
    abstract fun bindTermRepository(r: TermRepository): ITermRepository

    @Singleton
    @Binds
    abstract fun bindAdvicesRepository(r: AdvicesRepository): IAdvicesRepository

    @Singleton
    @Binds
    abstract fun bindBackendVersionRepository(r: BackendVersionRepository): IBackendVersionRepository

    @Singleton
    @Binds
    abstract fun bindRefactoredPolicyRepository(refactoredPolicyRepository: RefactoredPolicyRepository): IRefactoredPolicyRepository

    @Singleton
    @Binds
    abstract fun bindRefactoredUserRepository(refactoredUserRepository: RefactoredUserRepository): IRefactoredUserRepository

    @Singleton
    @Binds
    abstract fun bindRefactoredClaimRepository(refactoredClaimRepository: RefactoredClaimRepository): IRefactoredClaimRepository

    @Singleton
    @Binds
    abstract fun bindRefactoredPetRepository(refactoredPetRepository: RefactoredPetRepository): IRefactoredPetRepository

    @Singleton
    @Binds
    abstract fun bindCloudDocumentRepository(
        cloudDocumentRepository: CloudDocumentRepository
    ): ICloudDocumentRepository

    @Singleton
    @Binds
    abstract fun bindCharityRepository(r: CharityRepository): ICharityRepository

    @Singleton
    @Binds
    abstract fun bindExternalLinksRepository(r: ExternalLinksRepository): IExternalLinksRepository

    @Singleton
    @Binds
    abstract fun bindVetPlacesRepository(r: VetPlacesRepository): IVetPlacesRepository

    @Singleton
    @Binds
    abstract fun bindPreferencesRepository(r: PreferencesRepository): IPreferencesRepository

    @Singleton
    @Binds
    abstract fun bindLoginRepository(r: LoginRepository): ILoginRepository

    @Singleton
    @Binds
    abstract fun bindRentersChatbotRepository(r: RentersChatbotRepository): IRentersChatbotRepository

    @Singleton
    @Binds
    abstract fun bindRentersPolicyRepository(r: RentersPolicyRepository): IRentersPolicyRepository

    @Singleton
    @Binds
    abstract fun bindRentersScheduledItemsRepository(r: RentersScheduledItemsRepository): IRentersScheduledItemsRepository

    @Singleton
    @Binds
    abstract fun bindTemporaryFileRepository(r: TemporaryFileRepository): ITemporaryFileRepository

    @Singleton
    @Binds
    abstract fun bindVeterinarianRepository(r: VeterinarianRepository): IVeterinarianRepository

    @Singleton
    @Binds
    abstract fun bindRefactoredBanksRepository(r: RefactoredBanksRepository): IRefactoredBanksRepository

    @Singleton
    @Binds
    abstract fun bindRentersPricingRepository(r: RentersPricingRepository): IRentersPricingRepository

    @Singleton
    @Binds
    abstract fun bindRentersPolicyEndorsementRepository(r: RentersPolicyEndorsementRepository): IRentersPolicyEndorsementRepository

    @Singleton
    @Binds
    abstract fun bindContactInformationRepository(r: ContactInformationRepository): IContactInformationRepository

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LocalChatbotRepository
}
