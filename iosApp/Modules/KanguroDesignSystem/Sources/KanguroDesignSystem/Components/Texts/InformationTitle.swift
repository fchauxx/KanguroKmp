import SwiftUI

public struct InformationTitle: View {
    
    let text: String
    let textStyle: TextStyle
    let tapAction: SimpleClosure
    
    public init(text: String, 
                textStyle: TextStyle,
                tapAction: @escaping SimpleClosure) {
        self.text = text
        self.textStyle = textStyle
        self.tapAction = tapAction
    }
    
    public var body: some View {
        HStack {
            Text(text)
                .font(textStyle.font)
                .foregroundStyle(textStyle.color)
            Button(action: {
                tapAction()
            }, label: {
                Image.informationFilledIcon
            })
        }
    }
}

struct InformationTitle_Preview: PreviewProvider {
    
    static var previews: some View {
        InformationTitle(text: "Your liability",
                         textStyle: TextStyle(font: .museo(.museoSansBold,
                                                           size: 21),
                                              color: .secondaryDark),
                         tapAction: {})
    }
}
