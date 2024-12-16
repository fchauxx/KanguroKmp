import XCTest
import Firebase
@testable import KanguroFeatureFlagData
import KanguroFeatureFlagDomain

final class KanguroFeatureFlagIntegrationTests: XCTestCase {

    override func setUp() {
        let appOptions = FirebaseOptions(
            googleAppID: "1:1016988289017:ios:ffa8731120d22364aadf28",
            gcmSenderID: "1016988289017"
        )
        appOptions.apiKey = "AIzaSyCXq-JfiY60gbBv4PeuSFDKekXapGHNfmI"
        appOptions.projectID = "kanguro-seguro-dev"
        FirebaseApp.configure(options: appOptions)
    }

    func testSetDefaultValues() {
        guard let repo = try? makeSUT() else {
            XCTFail("Could not instantiate sut")
            return
        }
        do {
            try repo.setDefaultValues(makeDefaultValues())
        } catch {
            XCTFail(error.localizedDescription)
        }
    }

    func makeDefaultValues() -> [String: NSObject] {
        var dict: [String: NSObject] = [:]
        dict["dictDummy"] = [
            "records": [
                [
                    "number": 1,
                    "type": "input"
                ] as [String : Any],
                [
                    "number": 2,
                    "type": "output"
                ]
            ]
        ] as NSObject
        dict["intDummy"] = 13 as NSObject
        dict["floatDummy"] = 13.0 as NSObject
        dict["dataDumy"] = Data("any data".utf8) as NSObject
        dict["shouldShowRenters"] = true as NSObject
        dict["stringValueTest"] = "Corinthians" as NSObject
        return dict
    }

    func makeSUT() throws -> FirebaseFeatureFlagRepository {

        let repository = FirebaseFeatureFlagRepository()
        return repository
    }
}
