import XCTest
@testable import KanguroChatbotDomain

final class KanguroChatbotDomainTests: XCTestCase {
    func testChatbotMsgEquality() {
        var anArray: Array<any KanguroChatbotMessage> = []
        anArray.append("pet")
        anArray.append("renters")
        anArray.append(13)
        anArray.append("legal")
        var anotherArray: [any KanguroChatbotMessage] = []
        anotherArray.append("pet")
        anotherArray.append("renters")
        anotherArray.append(13)
        anotherArray.append("legal")
        XCTAssert(anArray == anotherArray)
    }

    func testChatbotMsgInequality() {
        var anArray: Array<any KanguroChatbotMessage> = []
        anArray.append("pet")
        anArray.append("renters")
        anArray.append(13)
        anArray.append("legal")
        var anotherArray: [any KanguroChatbotMessage] = []
        anotherArray.append("pet")
        anotherArray.append(101)
        anotherArray.append(13)
        anotherArray.append("legal")
        XCTAssert(anArray != anotherArray)
    }
}

extension Int: KanguroChatbotMessage {
    public func isEqualTo(_ other: any KanguroChatbotMessage) -> Bool {
        guard let another: Int = other as? Int else {
            return false
        }
        return another == self
    }
}
 
