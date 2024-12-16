import XCTest
import KanguroAnalyticsDomain
import KanguroAnalyticsData

final class AnalyticsTests: XCTestCase {

    func testInitEmptyLoggedScreens() {
        let sut = makeSUT()
        XCTAssertEqual(sut.loggedScreens, [])
    }

    func testLogOneScreen() {
        let sut = makeSUT()
        let screen = KanguroAnalyticsEnums.Screen.Cellphone
        sut.analyticsLogScreen(screen: screen)
        XCTAssertEqual(sut.loggedScreens, [screen])
    }

    func testLogMultipleScreen() {
        let sut = makeSUT()
        let screen1 = KanguroAnalyticsEnums.Screen.Cellphone
        let screen2 = KanguroAnalyticsEnums.Screen.Claims
        sut.analyticsLogScreen(screen: screen1)
        XCTAssertEqual(sut.loggedScreens, [screen1])
        sut.analyticsLogScreen(screen: screen2)
        XCTAssertEqual(sut.loggedScreens, [screen1, screen2])
    }

    // MARK: Test helpers

    func makeSUT() -> AnalyticsSpy {
        return AnalyticsSpy()
    }

}
