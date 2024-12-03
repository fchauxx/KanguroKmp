package com.insurtech.kanguro.core.di

import com.insurtech.kanguro.data.remote.BackendVersionRemoteDataSource
import com.insurtech.kanguro.data.remote.BanksRemoteDataSource
import com.insurtech.kanguro.data.remote.CharityRemoteDataSource
import com.insurtech.kanguro.data.remote.ChatbotV2RemoteDataSource
import com.insurtech.kanguro.data.remote.ClaimRemoteDataSource
import com.insurtech.kanguro.data.remote.CloudDocumentRemoteDataSource
import com.insurtech.kanguro.data.remote.ContactInformationRemoteDataSource
import com.insurtech.kanguro.data.remote.ExternalLinksRemoteDataSource
import com.insurtech.kanguro.data.remote.KanguroParameterRemoteDataSource
import com.insurtech.kanguro.data.remote.LoginRemoteDataSource
import com.insurtech.kanguro.data.remote.PetRemoteDataSource
import com.insurtech.kanguro.data.remote.PolicyRemoteDataSource
import com.insurtech.kanguro.data.remote.RentersPolicyEndorsementRemoteDataSource
import com.insurtech.kanguro.data.remote.RentersPolicyRemoteDataSource
import com.insurtech.kanguro.data.remote.RentersPricingRemoteDataSource
import com.insurtech.kanguro.data.remote.RentersScheduledItemsRemoteDataSource
import com.insurtech.kanguro.data.remote.TemporaryFileRemoteDataSource
import com.insurtech.kanguro.data.remote.TermRemoteDataSource
import com.insurtech.kanguro.data.remote.UserRemoteDataSource
import com.insurtech.kanguro.data.remote.VetPlacesRemoteDataSource
import com.insurtech.kanguro.data.remote.VeterinarianRemoteDataSource
import com.insurtech.kanguro.data.source.BackendVersionDataSource
import com.insurtech.kanguro.data.source.BanksDataSource
import com.insurtech.kanguro.data.source.CharityDataSource
import com.insurtech.kanguro.data.source.ChatbotDataSource
import com.insurtech.kanguro.data.source.ClaimDataSource
import com.insurtech.kanguro.data.source.CloudDocumentDataSource
import com.insurtech.kanguro.data.source.ContactInformationDataSource
import com.insurtech.kanguro.data.source.ExternalLinksDataSource
import com.insurtech.kanguro.data.source.KanguroParameterDataSource
import com.insurtech.kanguro.data.source.LoginDataSource
import com.insurtech.kanguro.data.source.PetDataSource
import com.insurtech.kanguro.data.source.PolicyDataSource
import com.insurtech.kanguro.data.source.RentersPolicyDataSource
import com.insurtech.kanguro.data.source.RentersPolicyEndorsementDataSource
import com.insurtech.kanguro.data.source.RentersPricingDataSource
import com.insurtech.kanguro.data.source.RentersScheduledItemsDataSource
import com.insurtech.kanguro.data.source.TemporaryFileDataSource
import com.insurtech.kanguro.data.source.TermDataSource
import com.insurtech.kanguro.data.source.UserDataSource
import com.insurtech.kanguro.data.source.VetPlacesDataSource
import com.insurtech.kanguro.data.source.VeterinarianDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {

    @Singleton
    @Binds
    abstract fun bindBackVersionRemoteDataSource(
        backendVersionRemoteDataSource: BackendVersionRemoteDataSource
    ): BackendVersionDataSource

    @Singleton
    @Binds
    abstract fun bindUserRemoteDataSource(
        userRemoteDataSource: UserRemoteDataSource
    ): UserDataSource

    @Singleton
    @Binds
    abstract fun bindClaimRemoteDataSource(
        claimRemoteDataSource: ClaimRemoteDataSource
    ): ClaimDataSource

    @Singleton
    @Binds
    abstract fun bindCloudDocumentRemoteDataSource(
        cloudDocumentRemoteDataSource: CloudDocumentRemoteDataSource
    ): CloudDocumentDataSource

    @Singleton
    @Binds
    abstract fun bindPolicyRemoteDataSource(
        policyRemoteDataSource: PolicyRemoteDataSource
    ): PolicyDataSource

    @Singleton
    @Binds
    abstract fun bindKanguroParameterRemoteDataSource(
        kanguroParameterRemoteDataSource: KanguroParameterRemoteDataSource
    ): KanguroParameterDataSource

    @Singleton
    @Binds
    abstract fun bindPetRemoteDataSource(
        petRemoteDataSource: PetRemoteDataSource
    ): PetDataSource

    @Singleton
    @Binds
    abstract fun bindVetPlacesRemoteDataSource(
        vetPlacesRemoteDataSource: VetPlacesRemoteDataSource
    ): VetPlacesDataSource

    @Singleton
    @Binds
    abstract fun bindLoginRemoteDataSource(
        loginRemoteDataSource: LoginRemoteDataSource
    ): LoginDataSource

    @Singleton
    @Binds
    abstract fun bindChatbotRemoteDataSource(
        chatbotV2RemoteDataSource: ChatbotV2RemoteDataSource
    ): ChatbotDataSource

    @Singleton
    @Binds
    abstract fun bindTemporaryFileRemoteDataSource(
        temporaryFileRemoteDataSource: TemporaryFileRemoteDataSource
    ): TemporaryFileDataSource

    @Singleton
    @Binds
    abstract fun bindRentersPolicyRemoteDataSource(
        rentersPolicyRemoteDataSource: RentersPolicyRemoteDataSource
    ): RentersPolicyDataSource

    @Singleton
    @Binds
    abstract fun bindRentersScheduledItemsRemoteDataSource(
        rentersScheduledItemsRemoteDataSource: RentersScheduledItemsRemoteDataSource
    ): RentersScheduledItemsDataSource

    @Singleton
    @Binds
    abstract fun bindVeterinarianRemoteDataSource(
        veterinarianDataSource: VeterinarianRemoteDataSource
    ): VeterinarianDataSource

    @Singleton
    @Binds
    abstract fun bindBanksRemoteDataSource(
        banksRemoteDataSource: BanksRemoteDataSource
    ): BanksDataSource

    @Singleton
    @Binds
    abstract fun bindRentersPricingDataSource(
        rentersPricingRemoteDataSource: RentersPricingRemoteDataSource
    ): RentersPricingDataSource

    @Singleton
    @Binds
    abstract fun bindRentersPolicyEndorsementDataSource(
        rentersPolicyEndorsementRemoteDataSource: RentersPolicyEndorsementRemoteDataSource
    ): RentersPolicyEndorsementDataSource

    @Singleton
    @Binds
    abstract fun bindContactInformationRemoteDataSource(
        contactInformationRemoteDataSource: ContactInformationRemoteDataSource
    ): ContactInformationDataSource

    @Singleton
    @Binds
    abstract fun bindExternalLinksDataSource(
        externalLinksRemoteDataSource: ExternalLinksRemoteDataSource
    ): ExternalLinksDataSource

    @Singleton
    @Binds
    abstract fun bindCharityDataSource(
        charityRemoteDataSource: CharityRemoteDataSource
    ): CharityDataSource

    @Singleton
    @Binds
    abstract fun bindTermDataSource(
        termRemoteDataSource: TermRemoteDataSource
    ): TermDataSource
}
