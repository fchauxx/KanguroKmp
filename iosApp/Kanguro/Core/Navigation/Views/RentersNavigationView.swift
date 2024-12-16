import SwiftUI
import KanguroRentersPresentation
import KanguroRentersDomain
import KanguroNetworkDomain

@available(iOS 16.0, *)
public struct RentersNavigationView<FirstPage: View>: View {
    
    // MARK: - Stored Properties
    let firstPage: FirstPage
    let network: NetworkProtocol
    
    // MARK: - Wrapped Properties
    @EnvironmentObject var router: RentersRouter<RentersPage>
    
    // MARK: - Content
    public var body: some View {
        NavigationStack(path: $router.path) {
            firstPage
                .navigationDestination(for: RentersPage.self) { page in
                    RentersViewFactory.make(page, router: router, network: network)
                        .environmentObject(router)
                        .navigationBarBackButtonHidden(true)
                }
        }
        .sheet(item: $router.sheet) { firstPage in
            NavigationStack(path: $router.sheetPath) {
                RentersViewFactory.make(firstPage, router: router, network: network)
                    .navigationDestination(for: RentersPage.self) { page in
                        RentersViewFactory.make(page, router: router, network: network)
                            .environmentObject(router)
                            .navigationBarBackButtonHidden(true)
                    }
            }
        }
    }
}

@available(iOS 16.0, *)
public struct RentersNavigationViewFactory {
    
    @ViewBuilder
    static func make(page: RentersPage,
                     network: NetworkProtocol,
                     router: RentersRouter<RentersPage> = RentersRouter<RentersPage>(),
                     navigation: UINavigationController? = nil) -> some View {
        RentersNavigationView(
            firstPage: RentersViewFactory.make(page, 
                                               router: router,
                                               network: network,
                                               navigation: navigation),
            network: network
        ).environmentObject(router)
    }
}
