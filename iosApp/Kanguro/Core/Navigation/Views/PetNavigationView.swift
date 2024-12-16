import SwiftUI
import KanguroPetDomain
import KanguroPetPresentation
import KanguroNetworkDomain

@available(iOS 16.0, *)
public struct PetNavigationView<FirstPage: View>: View {
    
    // MARK: - Stored Properties
    let firstPage: FirstPage
    let network: NetworkProtocol
    
    // MARK: - Wrapped Properties
    @EnvironmentObject var router: PetRouter<PetPage>
    
    // MARK: - Content
    public var body: some View {
        NavigationStack(path: $router.path) {
            firstPage
                .navigationDestination(for: PetPage.self) { page in
                    PetViewFactory.make(page, router: router, network: network)
                        .environmentObject(router)
                        .navigationBarBackButtonHidden(true)
                }
        }
        .sheet(item: $router.sheet) { firstPage in
            NavigationStack(path: $router.sheetPath) {
                PetViewFactory.make(firstPage, router: router, network: network)
                    .navigationDestination(for: PetPage.self) { page in
                        PetViewFactory.make(page, router: router, network: network)
                            .environmentObject(router)
                            .navigationBarBackButtonHidden(true)
                    }
            }
        }
    }
}

@available(iOS 16.0, *)
public struct PetNavigationViewFactory {
    
    @ViewBuilder
    static func make(page: PetPage, network: NetworkProtocol) -> some View {
        let router = PetRouter<PetPage>()
        PetNavigationView(
            firstPage: PetViewFactory.make(page, router: router, network: network), network: network
        ).environmentObject(router)
    }
}
