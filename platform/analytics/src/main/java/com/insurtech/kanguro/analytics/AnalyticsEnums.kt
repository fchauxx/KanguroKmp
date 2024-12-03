package com.insurtech.kanguro.analytics

class AnalyticsEnums {

    enum class Screen {
        // Onboarding
        Welcome,
        LoginEmail,
        LoginPassword,
        LoginBlockedAccount,
        ForgotPassword,
        NewPassword,

        // TabBar
        Dashboard,
        Coverages,
        CentralChatbot,
        Cloud,
        CloudFiles,
        More,

        // Dashboard
        FileClaimChatbot,
        VetAdvice,
        FrequentlyAskedQuestions,
        NewPetParents,
        Blog,
        PetFrequentlyAskedQuestions,

        // Coverage
        Claims,
        CoverageDetails,
        PaymentSettings,
        CoverageWhatsCovered,
        PreventiveWhatsCovered,
        Profile,
        Reminders,
        Settings,
        ReferFriends,

        // Others
        AdditionalInfoChatBot,
        BankingInformation,
        BillingPreferences,
        CarouselItem,
        ClaimDetails,
        CodeValidation,
        ExperienceFeedback,
        FilePickerOption,
        GetQuote,
        GetQuoteRenters,
        GetQuoteSelectProduct,
        MessageInstructions,
        PaymentMethod,
        PledgeOfHonor,
        PhoneValidation,
        PrivacyPolicy,
        SupportCause,
        SelectYourCause,
        WellnessPreventive,
        ReviewDocumentsChatBot,
        RecommendedLocationsChatBot,
        CommunicationChatbot,
        UpdateApp,
        Notifications,
        LiveVet,

        // Upselling
        PetUpselling,
        RentersUpselling,

        // Scheduled Items
        ScheduledItems,
        ScheduledItemsCategory,
        ScheduledItemsSelectedPictures,
        ScheduledItemsAddItem,

        // Renters
        RentersChatbot,
        RentersChatbotScheduledItems,
        RentersChatbotAddingItem,
        Home,
        RentersCoverageDetails,
        Chat,
        RentersDashboard,
        RentersEditAdditionalParties,
        RentersEditCoverageDetails,
        RentersTrackYourClaims,
        RentersEditAdditionalCoverage,
        RentersWhatIsCovered,
        RentersChangeMyAddress,
        RentersFrequentlyAskedQuestions,

        // Direct Pay to you vet flow
        DirectPayToVetInitFlow,
        DirectPayVetAlmostDone,
        DirectPayToVetInstructions,
        DirectPayToVetForm,
        DirectPayToVetPledgeOfHonor,
        DirectPayToVetDocumentSent,
        DirectPayToVetShareWithYourVet,

        // Pet
        PetDashboard
    }
}
