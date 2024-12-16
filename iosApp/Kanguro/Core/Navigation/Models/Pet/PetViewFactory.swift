import SwiftUI
import Resolver
import KanguroPetPresentation
import KanguroPetDomain
import KanguroPetData
import KanguroNetworkDomain
import KanguroUserDomain
import KanguroSharedDomain
import KanguroSharedData
import KanguroSharedPresentation
import KanguroDesignSystem

@available(iOS 16.0, *)
struct PetViewFactory {
    
    @ViewBuilder
    static func make(_ page: PetPage, router: PetRouter<PetPage>, network: NetworkProtocol) -> some View {
        switch page {
        case .getQuote: makeGetAQuote()
        case .petUpselling: makeUpsellingView(router: router)
        case .airvetInstruction: makeAirvetInstructionView(router: router)
        }
    }
    
    public static func makeUpsellingView(router: PetRouter<PetPage>) -> some View {
        
        return UpsellingView(type: .pet, didTapTellMeMore: {
            router.present(page: .getQuote)
        })
        .environmentObject(router)
        .environment(\.appLanguageValue, User.getLanguage())
    }
    
    public static func makeGetAQuote() -> some View {
        if let url = AppURLs.getPetQuote.url {
            let viewController = WebviewViewController(url: url)
            return AnyView(UIViewControllerAsView(viewController: viewController))
        } else {
            return AnyView(Text("No URL available"))
        }
    }

    public static func makeAirvetInstructionView(
        router: PetRouter<PetPage>
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
