import SwiftUI
import MessageUI
import KanguroDesignSystem
import KanguroRentersDomain
import KanguroSharedDomain

public struct RentersChatView: View {
    
    // MARK: - Property Wrappers
    @Environment(\.appLanguageValue) var language
    @ObservedObject var viewModel: RentersChatViewModel
    @State var showUnavailableComunicationAlert: Bool
    @State var alertText: String

    // MARK: - Computed Properties
    var lang: String {
        language.rawValue
    }

    // MARK: - Stored Properties
    var messageComposerDelegate: MessageComposerDelegate

    // MARK: - Actions
    var didTapBannerAction: SimpleClosure
    var didTapEmailAction: SimpleClosure
    var didTapAirvetAction: SimpleClosure
    let showAirvetAction: Bool

    public init(
        viewModel: RentersChatViewModel,
        showUnavailableComunicationAlert: Bool = false,
        alertText: String = "",
        messageComposerDelegate: MessageComposerDelegate = MessageComposerDelegate(),
        didTapBannerAction: @escaping SimpleClosure,
        didTapEmailAction: @escaping SimpleClosure,
        didTapAirvetAction: @escaping SimpleClosure,
        showAirvetAction: Bool
    ) {
        self.viewModel = viewModel
        self.showUnavailableComunicationAlert = showUnavailableComunicationAlert
        self.alertText = alertText
        self.messageComposerDelegate = messageComposerDelegate
        self.didTapBannerAction = didTapBannerAction
        self.didTapEmailAction = didTapEmailAction
        self.didTapAirvetAction = didTapAirvetAction
        self.showAirvetAction = showAirvetAction
    }

    public var body: some View {

        ZStack(alignment: .top) {
            Color.neutralBackground
            ScrollView {
                VStack(alignment: .leading, spacing: InsetSpacing.md) {
                    Text("renters.chat.title".localized(lang))
                        .titleSecondaryDarkestBold()

                    BannerCardView(data: BannerCardViewData(style: .vertical,
                                                            backgroundColor: .white,
                                                            leadingImage: Image.javierPinkRentersIcon,
                                                            title: "renters.chat.banner.title".localized(lang),
                                                            subtitle: "renters.chat.banner.subtitle".localized(lang),
                                                            tapAction: didTapBannerAction))

                    VStack(alignment: .leading, spacing: InsetSpacing.xs) {
                        if self.showAirvetAction {
                            Text("renters.chat.emergency".localized(lang).uppercased())
                                .captionSecondaryDarkBold()

                            ActionCardListView(cardButtons: [
                                CardButton(content: AnyView(ActionCardButton(
                                    title: "renters.chat.talk.vet".localized(lang),
                                    icon: Image.videoCall,
                                    style: .secondary,
                                    highlightTag: "common.new".localized(lang).uppercased(),
                                    didTapAction: didTapAirvetAction
                                )))
                            ], spacing: StackSpacing.semiquarck)
                        }

                        Text("moreActions.support.card.title".localized(lang).uppercased())
                            .captionSecondaryDarkBold()

                        OpeningHoursCard(openingHours: getCardButtonTitle(type: .text))

                        ActionCardListView(cardButtons: [
                            CardButton(content: AnyView(ActionCardButton(
                                title: getCardButtonTitle(type: .sms),
                                icon: Image.comunicationIcon,
                                style: .secondary,
                                highlightTag: "renters.chat.faster.tag".localized(lang).uppercased(),
                                didTapAction: {sendSmsMessage()}
                            ))),
                            CardButton(content: AnyView(ActionCardButton(
                                title: getCardButtonTitle(type: .whatsapp),
                                icon: Image.whatsappIcon,
                                style: .secondary,
                                highlightTag: "renters.chat.faster.tag".localized(lang).uppercased(),
                                didTapAction: {sendWhatsAppMessage()}
                            ))),
                        ], spacing: StackSpacing.semiquarck)

                        ActionCardListView(cardButtons: [
                            CardButton(content: AnyView(ActionCardButton(
                                title: "moreActions.emailKanguro.card".localized(lang),
                                icon: Image.emailIcon,
                                style: .secondary,
                                didTapAction: didTapEmailAction
                            ))),
                            CardButton(content: AnyView(ActionCardButton(
                                title: getCardButtonTitle(type: .phone),
                                icon: Image.phoneIcon,
                                style: .secondary,
                                didTapAction: {callSupport()}
                            ))),
                        ], spacing: StackSpacing.semiquarck)
                    }
                } //: VStack
                .padding(.horizontal, StackSpacing.xxs)
                .padding(.top, InsetSpacing.xs)
                .padding(.bottom, InsetSpacing.tabBar + InsetSpacing.lg)

            } //: ScrollView
            .scrollIndicators(.hidden)
            .offset(y: InsetSpacing.xl)

            if viewModel.state == .loading {
                LoadingView()
            }

        } //: ZStack
        .padding(.bottom, InsetSpacing.tabBar)
        .ignoresSafeArea(edges: [.top])
        .onAppear {
            if viewModel.contactInformation.isEmpty {
                viewModel.getContactInformation()
            }
        }
        .alert("alert.defaultTitle".localized(lang), isPresented: $showUnavailableComunicationAlert) {
            Button("alert.defaultAction".localized(lang), role: .cancel) { }
        } message: {
            Text(alertText.localized(lang))
        }
        .onChange(of: viewModel.state) { newState in
            if newState == .requestFailed {
                alertText = viewModel.requestError
                showUnavailableComunicationAlert = true
            }
        }
    }

}

// MARK: - Private Methods
extension RentersChatView {

    public class MessageComposerDelegate: NSObject, MFMessageComposeViewControllerDelegate {
        public func messageComposeViewController(_ controller: MFMessageComposeViewController, didFinishWith result: MessageComposeResult) {
            controller.dismiss(animated: true)
        }
    }

    private func sendSmsMessage() {
        guard let smsInfo = viewModel.contactInformation.first(where: { $0.type == .sms }) else {
            alertText = "renters.chat.sms.contactInfo.unavailable.alert"
            showUnavailableComunicationAlert = true
            return
        }

        if MFMessageComposeViewController.canSendText() {
            let allScenes = UIApplication.shared.connectedScenes
            let scene = allScenes.first { $0.activationState == .foregroundActive }

            let controller = MFMessageComposeViewController()
            let messageBody = smsInfo.data.text
            var phoneNumber = smsInfo.data.number
            if !phoneNumber.hasPrefix("+") {
                phoneNumber = "+" + phoneNumber
            }

            controller.body = messageBody
            controller.recipients = [phoneNumber]
            controller.messageComposeDelegate = messageComposerDelegate

            if let windowScene = scene as? UIWindowScene {
                windowScene.keyWindow?.rootViewController?.present(controller, animated: true, completion: nil)
            }
        } else {
            alertText = "renters.chat.sms.unavailable.alert"
            showUnavailableComunicationAlert = true
        }
    }


    private func sendWhatsAppMessage() {
        guard let whatsAppInfo = viewModel.contactInformation.first(where: { $0.type == .whatsapp }) else {
            alertText = "renters.chat.whatsapp.contactInfo.unavailable.alert"
            showUnavailableComunicationAlert = true
            return
        }
        let messageBody = whatsAppInfo.data.text
        let phoneNumber = whatsAppInfo.data.number
        let urlWhats = "https://wa.me/\(phoneNumber)?text=\(messageBody)"

        if let urlString = urlWhats.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed),
           let whatsappURL = URL(string: urlString),
           let whatsappAppURL = URL(string: "whatsapp://") {
            if UIApplication.shared.canOpenURL(whatsappAppURL) {
                UIApplication.shared.open(whatsappURL, options: [:], completionHandler: nil)
            } else {
                alertText = "renters.chat.whatsapp.notInstalled.alert"
                showUnavailableComunicationAlert = true
            }
        }
    }

    private func callSupport() {
        guard let phoneNumber = viewModel.contactInformation.first(where: { $0.type == .phone }) else {
            alertText = "renters.chat.phone.contactInfo.unavailable.alert"
            showUnavailableComunicationAlert = true
            return
        }

        guard let url = URL(string: "tel://+\(phoneNumber.data.number)"),
              UIApplication.shared.canOpenURL(url) else {
            alertText = "renters.chat.phone.unavailable.alert"
            showUnavailableComunicationAlert = true
            return
        }
        UIApplication.shared.open(url)
    }

    private func getCardButtonTitle(type: ContactInformationType) -> String {
        switch type {
        case .text:
            guard let openingHours = viewModel.contactInformation.first(where: { $0.type == .text }) else {
                return "renters.chat.opening.hours.card".localized(lang)
            }

            return openingHours.data.text

        case .whatsapp:
            guard let whatsapp = viewModel.contactInformation.first(where: { $0.type == .whatsapp }) else {
                return "renters.chat.whatsapp.actionCard".localized(lang)
            }

            return whatsapp.action

        case .sms:
            guard let sms = viewModel.contactInformation.first(where: { $0.type == .sms }) else {
                return "renters.chat.message.actionCard".localized(lang)
            }

            return sms.action

        case .phone:
            guard let phoneNumber = viewModel.contactInformation.first(where: { $0.type == .phone }) else {
                return "renters.chat.call.actionCard".localized(lang)
            }

            return phoneNumber.action
        }
    }
}


struct RentersChatView_Previews: PreviewProvider {
    static var previews: some View {
        RentersChatView(
            viewModel: RentersChatViewModel(),
            didTapBannerAction: {},
            didTapEmailAction: {},
            didTapAirvetAction: {},
            showAirvetAction: true
        )
    }
}


