import Foundation
import SwiftUI

public struct OpeningHoursCard: View {
    
    let openingHours: String
    
    public init(openingHours: String) {
        self.openingHours = openingHours
    }
    
    public var body: some View {
        HStack {
            Text(openingHours)
                .foregroundStyle(Color.secondaryDarkest)
                .padding([.bottom, .top], InsetSpacing.nano)
                .padding([.leading, .trailing], InsetSpacing.xxs)
            Spacer()
        }
        .frame(maxWidth: .infinity)
        .background(Color.secondaryLightest)
        .cornerRadius(CornerRadius.sm)
        .overlay(
            RoundedRectangle(cornerRadius: CornerRadius.sm)
                .stroke(Color.secondaryLight, lineWidth: BorderWidth.hairline)
        )
    }
}

struct OpeningHoursCard_Previews: PreviewProvider {
    static var previews: some View {
        OpeningHoursCard(openingHours: "Mon - Fri: 9:00am - 7:00pm EST\nSaturday: 10:00am - 2:00pm EST\nSunday: Closed")
    }
}
