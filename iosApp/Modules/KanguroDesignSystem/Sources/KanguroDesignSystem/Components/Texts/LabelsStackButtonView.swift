import SwiftUI

public struct LabelsStackButtonView: View {
    
    var title: String
    var description: String
    var buttonTitle: String
    var action: SimpleClosure
    
    public init(title: String, 
                description: String,
                buttonTitle: String,
                action: @escaping SimpleClosure) {
        self.title = title
        self.description = description
        self.buttonTitle = buttonTitle
        self.action = action
    }
    
    public var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack {
                Text(title)
                    .bodySecondaryDarkBold()
                Spacer()
                AddButtonView(buttonTitle, action)
            }
            
            Text(description)
                .captionSecondaryDarkRegular()
                .italic()
        }
    }
}

#Preview {
    LabelsStackButtonView(title: "Picture of your item",
                          description: "Please take a picture of the damaged item.\nIf the item has been stolen or lost, skip the photo",
                          buttonTitle: "Add",
                          action: {})
}
