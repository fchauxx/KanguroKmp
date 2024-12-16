import SwiftUI

public struct DescriptionContainer: View {
    private let description: String
    private let isAlert: Bool

    public init(description: String, isAlert: Bool) {
        self.description = description
        self.isAlert = isAlert
    }

    public var body: some View {
        Text(description)
            .frame(maxWidth: .infinity)
            .font(.system(size: 12))
            .foregroundStyle(Color.secondaryDark)
            .multilineTextAlignment(.center)
            .padding(16)
            .background(self.isAlert ? Color.warningLightest : Color.blueLightest)
            .cornerRadius(8)
            .overlay {
                RoundedRectangle(cornerRadius: 8)
                    .stroke(self.isAlert ? Color.warningDarkest: Color.secondaryDarkest, lineWidth: 0.5)
            }

    }

}

struct DescriptionContainer_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            DescriptionContainer(
                description: "This is a very big description to teste line break in the simulator description",
                isAlert: false
            )
            DescriptionContainer(
                description: "This is a alert description",
                isAlert: true
            )
        }.padding()

    }
}
