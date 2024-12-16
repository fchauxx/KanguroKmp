import UIKit

enum CustomPickerDataType {

    case camera
    case file
    case picture
    case `default`
}

struct CustomPickerData: Equatable {

    var id: Int?
    var dataType: CustomPickerDataType = .default
    var icon: UIImage?
    var label: String
}
