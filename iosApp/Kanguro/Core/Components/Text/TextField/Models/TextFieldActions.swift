import Foundation

struct TextFieldActions {
    
    var didChangeAction: StringClosure = { _ in }
    var textFieldDidBeginEditingAction: SimpleClosure = {}
    var textFieldDidEndEditingAction: SimpleClosure = {}
    var returnKeyAction: SimpleClosure = {}
    
    var leadingButtonAction: SimpleClosure = {}
    var trailingButtonAction: SimpleClosure = {}
    var fullButtonAction: SimpleClosure = {}
    
    var pickerSelectedAction: TextFieldPickerDataClosure = { _ in }
}
