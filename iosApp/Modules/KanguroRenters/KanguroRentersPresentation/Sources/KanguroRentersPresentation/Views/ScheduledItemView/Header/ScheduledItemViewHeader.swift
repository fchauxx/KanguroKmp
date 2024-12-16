import SwiftUI

struct ScheduledItemViewHeader: View {
    
    var headerText: String
    
    public init(text: String) {
        self.headerText = text
    }
    
    var body: some View {
        HStack(spacing: 8) {
            Image.javierRentersIcon
                .resizable()
                .frame(width: 58, height: 54)
            Text(headerText)
                .headlineSecondaryDarkestBold()
                .minimumScaleFactor(0.3)
            Spacer()
        }
        .frame(maxWidth: .infinity)
    }
}

struct SendScheduledItemViewHeader_Previews: PreviewProvider {
    static var previews: some View {
        ScheduledItemViewHeader(text: "Here's the list of your scheduled items")
    }
}
