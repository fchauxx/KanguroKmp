import Foundation

struct FormularySectionData {

    let sectionTitle: String?
    let formFields: [FormDataProtocol]

    init(sectionTitle: String? = nil,
         formFields: [FormDataProtocol]) {
        self.sectionTitle = sectionTitle
        self.formFields = formFields
    }
}
