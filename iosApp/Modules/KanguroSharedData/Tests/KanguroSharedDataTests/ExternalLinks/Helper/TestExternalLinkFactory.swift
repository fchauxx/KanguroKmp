import Foundation
import KanguroSharedData
import KanguroSharedDomain

struct TestExternalLinkFactory {
    static func makeRemoteExternalLink(
        link: String = "www.roampets.com"
    ) -> RemoteURLRedirect {
        RemoteURLRedirect(redirectTo: link)
    }
    
    static func makeExternalLink(
        link: String = "www.roampets.com"
    ) -> URLRedirect {
        URLRedirect(redirectTo: link)
    }
}
