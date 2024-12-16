import SwiftUI
import KanguroPetData
import KanguroPetDomain
import Resolver
import KanguroRentersPresentation
import KanguroRentersDomain
import KanguroRentersData
import KanguroNetworkDomain
import KanguroUserDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroSharedPresentation
import KanguroDesignSystem
import KanguroPetPresentation

@available(iOS 16.0, *)
struct RentersViewFactory {

    // MARK: - Stored Properties
    static var policyId: String? = nil
    static var categorySelected: ScheduledItemCategory? = nil
    static var scheduledItem: ScheduledItem? = nil
    
    @ViewBuilder
    static func make(_ page: RentersPage,
                     router: RentersRouter<RentersPage>,
                     network: NetworkProtocol,
                     navigation: UINavigationController? = nil) -> some View {
        switch page {
        case .rentersDashboard(let policies): makeRentersDashboardView(network: network, router: router, policies: policies)
        case .rentersUpselling: makeUpsellingView(router: router)
        case .onboardingChat(let policy): makeOnboardingRenters(network: network,
                                                                router: router,
                                                                policy: policy,
                                                                navigation: navigation)
        case .claimsChat(let policy): makeClaimsChat(network: network, router: router, policy: policy)
        case .coverageDetails(let policy): makeRentersCoverageDetails(network: network,
                                                                      router: router,
                                                                      policy: policy,
                                                                      navigation: navigation)
        case .editPolicyDetails(let policy): makeEditPolicyDetails(network: network,
                                                                   router: router,
                                                                   policy: policy)
        case .scheduleItemSummaryView(let policy): makeScheduledItemSummaryView(network: network, router: router, policy: policy)
        case .scheduleItemAddPictureView: makeScheduleItemAddPictureView(network: network, router: router)
        case .scheduleItemCategoryView: makeScheduleItemCategoryView(network: network, router: router)
        case .scheduleNewItem: makeScheduleNewItemView(network: network, router: router)
        case .tabBarChat(let productType): makeTabBarChatView(
            product: productType,
            network: network,
            navigation: navigation,
            router: router
        )
        case .getQuote: makeRentersGetAQuote()
        case .paymentSettings: makePaymentSettings(router: router)
        case .paymentMethods: makePaymentMethods(router: router)
        case .bankingInfo: makeBankingInfo(router: router)
        case .supportCause: makeSupportCause(router: router)
        case .donationCause(let type, let donationCauses): makeDonationCause(type: type,
                                                                             donationCauses: donationCauses,
                                                                             router: router)
        case .donationTypeCause: makeDonationTypeCause(router: router)
            
        case .editAdditionalCoverage(let policy):
            makeEditAdditionalCoverage(policy: policy, network: network, router: router)
            
        case .rentersFAQ: makeRentersFAQ(network: network, router: router)
            
        case .uploadFileVideo: makeUploadFileVideo(network: network, router: router)
            
        case .whatisCoveredRenters: makeWhatIsCoveredView(network: network, router: router)
            
        case .homeFAQ: makeHomeFAQView(network: network, navigation: navigation, router: router)
            
        case .airvetInstruction: makeAirvetInstructionView(router: router)
        }
    }
    
    public static func makeWhatIsCoveredView(network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        return WhatIsCoveredView {
            router.dismiss()
        }
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeUploadFileVideo(network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        let getTemporaryFilesService = GetTemporaryFile (
            temporaryFileRepo: TemporaryFileRepository(network: network)
        )
        
        let uploadTemporaryFilesService = UploadTemporaryFile (
            temporaryFileRepo: TemporaryFileRepository(network: network)
        )
        
        let createTemporaryFilesService = CreateTemporaryFile (
            temporaryFileRepo: TemporaryFileRepository(network: network)
        )
        
        let viewModel = UploadFileViewModel(getTemporaryFileService: getTemporaryFilesService,
                                            uploadTemporaryFileService: uploadTemporaryFilesService,
                                            createTemporaryFileService: createTemporaryFilesService,
                                            delegate: SwiftUIChatbotFactory.chatbotViewModel)
        
        return UploadFileView(viewModel: viewModel, closeAction: {
            router.dismiss()
        }, nextAction: {
            router.dismiss()
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeRentersFAQ(network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        let viewModel = RentersFAQViewModel(type: .FAQRenters)
        return RentersFAQView(viewModel: viewModel, backAction: {
            router.pop()
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeEditAdditionalCoverage(policy: RenterPolicy, network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        
        let updatePlanSummaryItemsService = UpdatePlanSummary(rentersPolicyRepo: RentersPolicyRepository(network: network))
        
        let updatePolicyPricingService = UpdatePolicyPricing(rentersPolicyRepo: RentersPolicyRepository(network: network))
        
        
        let viewModel = EditAdditionalCoverageOptionsViewModel(
            policyId: policy.id,
            updatePlanSummaryItemsService: updatePlanSummaryItemsService,
            updatePolicyPricingService: updatePolicyPricingService,
            cardsData: [])
        
        return EditAdditionalCoverageOptionsView(viewModel: viewModel, backAction: {
            router.pop()
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeTabBarChatView(
        product: ProductType,
        network: NetworkProtocol,
        navigation: UINavigationController? = nil,
        router: RentersRouter<RentersPage>
    ) -> some View {
        let viewModel: RentersChatViewModel = RentersChatViewModel()

        return RentersChatView(
            viewModel: viewModel,
            didTapBannerAction: {
                guard let navigation else { return }
                switch product {
                case .petProduct:
                    showPetFileAClaim(navigation: navigation)
                case .rentersProduct:
                    showRentersFileClaimPopUp(navigation: navigation)
                case .petAndRentersProduct:
                    showFileClaimPopUp(navigation: navigation)
                }
            },
            didTapEmailAction: {
                guard let url = URL(string: "mailto:javier@kanguroinsurance.com"),
                      UIApplication.shared.canOpenURL(url) else { return }
                UIApplication.shared.open(url)
            },
            didTapAirvetAction: {
                router.present(page: RentersPage.airvetInstruction)
            },
            showAirvetAction: product != .rentersProduct && viewModel.shouldShowLiveVet
        )
        .environmentObject(router)
        .environment(\.appLanguageValue,User.getLanguage())
    }
    
    private static func showFileClaimPopUp(navigation: UINavigationController) {
        guard let bottomButtonsPopUp = BottomButtonsPopUpViewController.create() as? BottomButtonsPopUpViewController else { return }

        let popUp = PopUpViewController(contentViewController: bottomButtonsPopUp)
        
        bottomButtonsPopUp.setup(
            data: [
                ActionCardData(
                    leadingImage: UIImage(
                        named: "ic-dog"
                    ),
                    leadingTitle: "bottomButtonsPopUp.claim.pet".localized,
                    didTapAction: {
                        bottomButtonsPopUp.dismiss(animated: true) {
                            showPetFileAClaim(navigation: navigation)
                        }
                    }
                ),
                ActionCardData(
                    leadingImage: UIImage(
                        named: "ic-home"
                    ),
                    leadingTitle: "bottomButtonsPopUp.claim.renters".localized,
                    didTapAction: {
                        bottomButtonsPopUp.dismiss(animated: true) {
                            showRentersFileClaimPopUp(navigation: navigation)
                        }
                    }
                )
            ]
        )
        
        popUp.show(onViewController: navigation)
    }
    
    private static func showPetFileAClaim(navigation: UINavigationController) {
        let chatbotCoordinator = CentralChatbotCoordinator(navigation: navigation,
                                                           chatbotData: ChatbotData(type: .NewClaim),
                                                           serviceType: .local)
        chatbotCoordinator.start()
    }
    
    private static func showRentersFileClaimPopUp(navigation: UINavigationController) {
        guard let informationPopUp = InformationPopUpViewController.create() as? InformationPopUpViewController else { return }
        
        let popUp = PopUpViewController(contentViewController: informationPopUp)
        let data = InformationPopUpData(
            title: "centralChatbot.fileClaim.label".localized,
            description: "dashboard.fileClaim.popup.label".localized,
            style: .detail,
            highlighted: (
                text: "rentersclaims@kanguroinsurance.com",
                style: TextStyle(
                    color: .tertiaryDark,
                    size: .p16,
                    underlined: true
                )
            )
        )
        informationPopUp.setup(data: data)
        popUp.show(onViewController: navigation)
    }
    
    public static func makeRentersGetAQuote() -> some View {
        @LazyInjected var getLocalUserService: GetLocalUser
        var localUser: User? {
            guard let user: User = try? getLocalUserService.execute().get() else { return nil }
            return user
        }
        
        let url: URL? = localUser.map {
            URL(string: AppURLs.getQuoteLoggedUrl(user: $0, quoteType: .renters))
        } ?? AppURLs.getRentersQuote.url
        
        return makeWebViewController(with: url)
    }
    
    private static func makeWebViewController(with url: URL?) -> some View {
        guard let url = url else { return AnyView(Text("No URL available")) }
        
        let viewController = WebviewViewController(url: url)
        return AnyView(UIViewControllerAsView(viewController: viewController))
    }
    
    public static func makeOnboardingRenters(network: NetworkProtocol,
                                             router: RentersRouter<RentersPage>,
                                             policy: RenterPolicy,
                                             navigation: UINavigationController? = nil) -> some View {
        
        let onboardingChatbotViewModel = OnboardingChatViewModel(router,
                                                                 didFinishAction: {
            if let navigation {
                navigation.popViewController(animated: true)
            }
        })
        
        return OnboardingChatView(
            viewModel: onboardingChatbotViewModel
        ) {
            SwiftUIChatbotFactory.make(
                delegate: onboardingChatbotViewModel,
                network: network,
                journey: .RentersOnboarding,
                policyId: policy.id
            )
        }
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeClaimsChat(network: NetworkProtocol, router: RentersRouter<RentersPage>, policy: RenterPolicy) -> some View {
        
        // get renter policy here
        
        let claimsChatbotViewModel: ClaimsChatViewModel = ClaimsChatViewModel(router)
        //trocar pra ClaimsJourney quando tivermos ela do backend
        return ClaimsChatView(
            viewModel: claimsChatbotViewModel,
            backAction: {
                router.pop()
            }
        ) {
            SwiftUIChatbotFactory.make(
                delegate: claimsChatbotViewModel,
                network: network,
                journey: .RentersOnboarding,
                policyId: policy.id
            )
        }
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeRentersDashboardView(network: NetworkProtocol,
                                                router: RentersRouter<RentersPage>,
                                                policies: [RenterPolicy]) -> some View {
        
        @LazyInjected var getLocalUserService: GetLocalUser
        var user: User? {
            guard let user: User = try? getLocalUserService.execute().get() else { return nil }
            return user
        }
        
        return RentersDashboardView(viewModel: RentersDashboardViewModel(policies: policies, user: user),
                                    didTapRenterPolicyCard: { policy in
            router.push(RentersPage.coverageDetails(policy: policy))
        }, didTapGetQuoteAction: {
            router.present(page: .getQuote)
        }, didTapDonationBannerCardAction: { isDonating in
            router.present(page: isDonating ? .supportCause : .donationTypeCause)
        }, didTapFileAClaimAction: {
#warning("Change to get the correct policy id")
            router.push(RentersPage.claimsChat(policy: RenterPolicy(id: "abc")))
        }, didTapTrackClaimAction: {
#warning("Check if it shouldn't have a Renters track claim instead of the pet one")
        }, didTapAddResidenceAction: {
            router.present(page: .getQuote)
        }, didTapPaymentSettingsAction: {
            router.push(RentersPage.paymentSettings)
        }, didTapFAQAction: {
            router.push(RentersPage.rentersFAQ)
        }, didTapEmailAction: {
            guard let url = URL(string: "mailto:rentersclaims@kanguroinsurance.com"),
                  UIApplication.shared.canOpenURL(url) else { return }
            UIApplication.shared.open(url)
        }
        )
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeUpsellingView(router: RentersRouter<RentersPage>) -> some View {
        
        return UpsellingView(type: .renters, didTapTellMeMore: {
            router.present(page: .getQuote)
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeRentersCoverageDetails(network: NetworkProtocol,
                                                  router:  RentersRouter<RentersPage>,
                                                  policy: RenterPolicy,
                                                  navigation: UINavigationController? = nil) -> some View {
        
        @LazyInjected var getLocalUserService: GetLocalUser
        var user: User? {
            guard let user: User = try? getLocalUserService.execute().get() else { return nil }
            return user
        }
        
        let getRenterPolicyService = GetRenterPolicy(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        let getScheduledItemsService = GetScheduledItems(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        let getAdditionalPartiesService = GetAdditionalParties(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        let getPolicyDocumentsService = GetCloudDocumentByPolicy(
            cloudDocumentRepo: PetCloudDocumentRepository(network: network)
        )
        let coverageDetailsViewModel = RenterCoverageDetailsViewModel(
            policy: policy,
            user: user,
            getRenterPolicyService: getRenterPolicyService,
            getScheduledItemsService: getScheduledItemsService,
            getAdditionalPartiesService: getAdditionalPartiesService,
            getPolicyDocumentsService: getPolicyDocumentsService
        )
        
        return RenterCoverageDetailsView(viewModel: coverageDetailsViewModel,
                                         backAction: {
            if let navigation {
                navigation.popViewController(animated: true)
            } else {
                router.pop()
            }
        },
                                         didTapEditPolicyDetailsAction: {
            router.present(page: .editPolicyDetails(policy: policy))
        },
                                         didTapEditAdditionalCoverageAction: {
            router.push(RentersPage.editAdditionalCoverage(policy: policy))
        },
                                         didTapMyScheduledItemsAction: {
            router.present(page: .scheduleItemSummaryView(policy: policy))
        },
                                         didTapEditAdditionalPartiesAction: {},
                                         didTapBillingPreferencesAction: {
            router.push(RentersPage.paymentSettings)
        },
                                         didTapFileAClaimAction: {
            router.push(RentersPage.claimsChat(policy: policy))
        },
                                         didTapTrackYourClaimAction: {},
                                         didTapChangeMyAddressAction: {},
                                         didTapFaqAction: {
            router.push(RentersPage.rentersFAQ)
        },
        didTapPhoneNumberAction: {
            guard let url = URL(string: "tel://+1-888-546-5264"),
                  UIApplication.shared.canOpenURL(url) else { return }
            UIApplication.shared.open(url)
        }, didTapClaimEmailAction: {
            guard let url = URL(string: "mailto:rentersclaims@kanguroinsurance.com"),
                  UIApplication.shared.canOpenURL(url) else { return }
            UIApplication.shared.open(url)
            
        }, didTapPopUpEmailAction: { email in
            guard let url = URL(string: "mailto:\(email)"),
                  UIApplication.shared.canOpenURL(url) else { return }
            UIApplication.shared.open(url)
        }, didTapWhatisCoveredAction: {
            router.present(page: RentersPage.whatisCoveredRenters)
        }, didTapDocumentAction: { document in
            if let document = document as?KanguroSharedDomain.PolicyDocumentData {
                let controller = PDFViewController()
                controller.viewModel = PolicyDocumentViewModel(cloudType:CloudType.renters, policyId: policy.id, document: document)
                controller.backAction = {
                    navigation?.dismiss(animated: true)
                }
                navigation?.present(controller, animated: true)
            }
        }
        )
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    @ViewBuilder
    public static func makeEditPolicyDetails(network: NetworkProtocol,
                                             router:  RentersRouter<RentersPage>,
                                             policy: RenterPolicy) -> some View {
        
        let getRenterPolicyService = GetPlanSummaryList(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        
        let updateRenterPolicyService = UpdatePlanSummary(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        
        let updatePolicyPricing = UpdatePolicyPricing(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        
        let getPersonalProperties = GetPersonalProperties(
            rentersPolicyRepo: RentersPolicyRepository(network: network)
        )
        
        let viewModel = RenterEditPolicyDetailsViewModel(
            policy: policy,
            planSummary: policy.planSummary,
            getPlanSummaryItemsService: getRenterPolicyService,
            updatePlanSummaryItemsService: updateRenterPolicyService,
            updatePolicyPricingService: updatePolicyPricing,
            getPersonalPropertiesService: getPersonalProperties
        )
        
        RenterEditPolicyDetailsView(viewModel: viewModel,
                                    backAction: {
            router.pop()
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    @ViewBuilder
    public static func makeScheduledItemSummaryView(network: NetworkProtocol, router: RentersRouter<RentersPage>, policy: RenterPolicy) -> some View {
        
        let scheduledSummaryViewModel: ScheduleItemSummaryViewModel = ScheduleItemSummaryViewModel(
            policyId: policy.id,
            delegate: SwiftUIChatbotFactory.chatbotViewModel,
            getScheduledItemsService: GetScheduledItems(rentersPolicyRepo: RentersPolicyRepository(network: network)),
            deleteScheduledItemService: DeleteScheduledItem(rentersPolicyRepo: RentersPolicyRepository(network: network)))
        ScheduleItemSummaryView(viewModel: scheduledSummaryViewModel,
                                didTapUploadOrEditPicture: { scheduledItem in
            RentersViewFactory.scheduledItem = scheduledItem
            router.push(RentersPage.scheduleItemAddPictureView, pathType: .sheet)
        }, didTapAddMoreItems: { policyId in
            RentersViewFactory.policyId = policyId
            router.push(RentersPage.scheduleItemCategoryView, pathType: .sheet)
        }, backAction: {}, closeAction: {})
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    
    @ViewBuilder
    public static func makeScheduleItemAddPictureView(network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        
        let scheduleItemAddPictureViewModel: ScheduleItemAddPictureViewModel = ScheduleItemAddPictureViewModel(
            updateScheduledItemsImagesService: UpdateScheduledItemsImages(rentersPolicyRepo: RentersPolicyRepository(network: network)),
            createTemporaryPictureService: CreateTemporaryFile(temporaryFileRepo: TemporaryFileRepository(network: network)),
            receiptOrAppraisalImages: [],
            itemImages: [],
            itemWithReceiptOrAppraisalImages: [],
            scheduledItemId: RentersViewFactory.scheduledItem?.id)
        
        ScheduleItemAddPictureView(viewModel: scheduleItemAddPictureViewModel,
                                   backAction: {
            router.pop(pathType: .sheet)
        },
                                   didPicturesUploaded: {
            router.popToRoot(pathType: .sheet)
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    @ViewBuilder
    public static func makeScheduleItemCategoryView(network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        
        let scheduleItemCategoryViewModel: ScheduleItemCategoryViewModel = ScheduleItemCategoryViewModel(
            itemCategoryList: [],
            getScheduledItemsCategoriesService: GetScheduledItemsCategories(rentersPolicyRepo: RentersPolicyRepository(network: network)))
        
        ScheduleItemCategoryView(viewModel: scheduleItemCategoryViewModel,
                                 didTapCategorySelected: { category in
            RentersViewFactory.categorySelected = category
            router.push(RentersPage.scheduleNewItem, pathType: .sheet)
        },
                                 backAction: { router.pop(pathType: .sheet) })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    @ViewBuilder
    public static func makeScheduleNewItemView(network: NetworkProtocol, router: RentersRouter<RentersPage>) -> some View {
        
        let scheduleNewItemViewModel: ScheduleNewItemViewModel = ScheduleNewItemViewModel(
            updatePricingService: UpdateScheduledItemPricing(
                rentersPolicyRepo: RentersPolicyRepository(network: network)),
            createScheduledItemService: CreateScheduledItem(rentersPolicyRepo: RentersPolicyRepository(network: network)),
            temporaryScheduledItem: ScheduledItem(category: RentersViewFactory.categorySelected),
            policyId: RentersViewFactory.policyId)
        if let category = RentersViewFactory.categorySelected {
            ScheduleNewItemView(viewModel: scheduleNewItemViewModel,
                                categoryImage: category.category.image,
                                selectedCategory: category.label,
                                backAction: { router.pop(pathType: .sheet) },
                                didCreatedNewItem: { router.popToRoot(pathType: .sheet) })
            .environmentObject(router)
            .environment(\.appLanguageValue, User.getLanguage())
        }
    }
    
    @ViewBuilder
    public static func makeHomeFAQView(
        network: NetworkProtocol,
        navigation: UINavigationController? = nil,
        router: RentersRouter<RentersPage>
    ) -> some View {
        let viewModel = HomeFAQViewModel()
        HomeFAQView(viewModel: viewModel) {
            if let navigation {
                navigation.popViewController(animated: true)
            } else {
                router.pop()
            }
        }
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeAirvetInstructionView(
        router: RentersRouter<RentersPage>
    ) -> some View {
        let viewModel = AirvetInstructionViewModel()
        return AirvetInstructionView(
            viewModel: viewModel,
            downloadAppAction: { airvetUserDetails in
                Utilities.openAirvet(airvetUserDetails: airvetUserDetails)
            },
            onCopyIdAction: { insuranceId in
                UIPasteboard.general.string = insuranceId
            }
        )
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
   
}
