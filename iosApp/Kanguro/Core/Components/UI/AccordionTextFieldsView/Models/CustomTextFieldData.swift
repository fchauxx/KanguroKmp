import Foundation
import UIKit

struct CustomTextFieldData: FormDataProtocol {
    
    let title: String
    var placeholder: String?
    var isEditable: Bool = true
    let textFieldType: TextFieldType
    var leadingIcon: UIImage? = nil
    var valueDidChange: StringClosure?
    var didTappedCustomTextfieldOption: SimpleClosure?
}
