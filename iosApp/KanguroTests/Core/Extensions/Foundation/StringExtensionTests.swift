@testable import Kanguro
import XCTest
import UIKit

class StringExtensionsTests: XCTestCase {
    
    // MARK: - Initializers
    override func setUp() {
        continueAfterFailure = false
    }
}

// MARK: - Setup
extension StringExtensionsTests {
    
    func testLenght() {
        let text = "12345"
        XCTAssertEqual(5, text.length)
    }
    
    func testIsValidEmail() {
        var email = "tales.souza@poatek.com"
        XCTAssertEqual(true, email.isValidEmail)
        email = "tales.souza@poatek"
        XCTAssertEqual(false, email.isValidEmail)
    }
    
    func testIsValidPassword() {
        var password = "1c3d5S!"
        XCTAssertEqual(true, password.isValidPassword)
        password = "1c3d5!"
        XCTAssertEqual(false, password.isValidPassword)
    }
    
    func testOnlyNumbers() {
        let text = "123TEST456"
        XCTAssertEqual("123456", text.onlyNumbers)
    }
    
    func testOnlyNumbersAndLetters() {
        let text = "@@1U@2D&3T@@"
        XCTAssertEqual("1U2D3T", text.onlyNumbersAndLetters)
    }
    
    func testBankRoutingNumber() {
        var routingNumber = "021000021"
        XCTAssertTrue(routingNumber.isValidBankRountingNumber)
        routingNumber = "044115090"
        XCTAssertTrue(routingNumber.isValidBankRountingNumber)
        routingNumber = "051903761"
        XCTAssertTrue(routingNumber.isValidBankRountingNumber)
        routingNumber = "02a10000211"
        XCTAssertFalse(routingNumber.isValidBankRountingNumber)
        routingNumber = "021009021"
        XCTAssertFalse(routingNumber.isValidBankRountingNumber)
        routingNumber = "0u10A902c"
        XCTAssertFalse(routingNumber.isValidBankRountingNumber)
    }
    
    func testBankAccountNumber() {
        var bankAccount = "123456"
        XCTAssertTrue(bankAccount.isValidBankAccount)
        bankAccount = "123"
        XCTAssertFalse(bankAccount.isValidBankAccount)
        bankAccount = "14a23456B"
        XCTAssertFalse(bankAccount.isValidBankAccount)
        bankAccount = "1234567"
        XCTAssertTrue(bankAccount.isValidBankAccount)
        bankAccount = "123456789014"
        XCTAssertTrue(bankAccount.isValidBankAccount)
    }
    
    func test_convertToFloat_WhenInsertAStringWhichContainsNumbers_ShouldReturnADoubleValue() {
        let number = "$2,340.40"
        let number_2 = "131okc3okf"
        let number_3 = "1234"
        let number_4 = "test"
        let number_5 = ".301,a"
        let number_6 = "a,555.a"
        
        let doubleNumber = number.convertToFloat
        let doubleNumber2 = number_2.convertToFloat
        let doubleNumber3 = number_3.convertToFloat
        let doubleNumber4 = number_4.convertToFloat
        let doubleNumber5 = number_5.convertToFloat
        let doubleNumber6 = number_6.convertToFloat
        
        XCTAssertEqual(doubleNumber, 2340.40)
        XCTAssertEqual(doubleNumber2, 1313)
        XCTAssertEqual(doubleNumber3, 1234)
        XCTAssertNil(doubleNumber4)
        XCTAssertEqual(doubleNumber5, 0.301)
        XCTAssertEqual(doubleNumber6, 555)
    }
    
    func test_parseJSONAndReturnCount_WhenStepIsSendFiles_ShouldProcessJSONAndReturnTheCorrectNumberOfSentFiles() {
        
        let jsonExample1 = "{\"fileInBase64\": \"test\", \"fileExtension\": \".jpg\"}"
        let jsonExample2 = "{\"fileInBase64\": \"test\", \"fileExtension\": \".jpg\"}|{\"fileInBase64\": \"afrf\", \"fileExtension\": \".jpg\"}"
        let jsonExample3 = "{\"fileInBase64\": \"test\", \"fileExtension\": \".jpg\"}|{\"fileInBase64\": \"afrf\", \"fileExtension\": \".jpg\"}|{\"fileInBase64\": \"afrf\", \"fileExtension\": \".jpg\"}"
        
        let firstJson = jsonExample1.parseJSONAndReturnElementCount
        XCTAssertEqual(firstJson, 1)
        let secondJson = jsonExample2.parseJSONAndReturnElementCount
        XCTAssertEqual(secondJson, 2)
        let thirdJson = jsonExample3.parseJSONAndReturnElementCount
        XCTAssertEqual(thirdJson, 3)
    }
}
