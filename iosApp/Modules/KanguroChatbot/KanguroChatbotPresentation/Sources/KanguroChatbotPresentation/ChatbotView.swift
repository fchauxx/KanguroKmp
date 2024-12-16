import SwiftUI
import KanguroDesignSystem
import KanguroChatbotDomain

public enum ChatbotViewMessages: String {
    
    case writing = "...WRITING..."
}

public struct ChatbotView: View {
    
    // MARK: - Wrapped Properties
    @ObservedObject var viewModel: ChatbotViewModel
    @ObservedObject var choosenDate: DateViewData {
        willSet {
            viewModel.choosenDate = newValue.selected
        }
    }
    
    // MARK: - Computed Properties
    var datePickerRange: DatePickerRange {
        ChatInputTypeMapper.map(viewModel.userInputType) ?? .anyDate
    }
    
    // MARK: - Initializers
    public init(
        viewModel: ChatbotViewModel,
        choosenDate: DateViewData
    ) {
        self.viewModel = viewModel
        self.choosenDate = choosenDate
    }
    
    public var body: some View {
        ScrollViewReader { value in
            VStack(spacing: 0) {
                ScrollView(showsIndicators: false) {
                    VStack(spacing: 0) {
                        ForEach(Array(viewModel.messages.enumerated()), id: \.1.id) { (index, e) in
                            makeMessage(index: index)
                        }
                        
                        let isUserMessage = viewModel.messages.last?.sender == ChatMessageSender.user
                        
                        if viewModel.isLoadingNextStep && isUserMessage {
                            makeLoadingMessage().padding(.leading, InsetSpacing.nano)
                        }
                        
                    } // :VStack
                    .id("scrollToBottom")
                } // :ScrollView
                .onChange(of: viewModel.messages.count) { _ in
                    DispatchQueue.main.asyncAfter(deadline: .now()) {
                        withAnimation(.easeInOut(duration: 0.2)) {
                            value.scrollTo("scrollToBottom", anchor: .bottom)
                        }
                    }
                }
                .accessibilityElement(children: .combine)
                .transition(.opacity)
                .padding([.horizontal], InsetSpacing.xs)
            }
            
            Spacer()
            
            if viewModel.canChatbotInputShow && !viewModel.isLoadingNextStep{
                makeInputView()
            }
        } // :ScrollViewReader
        .onAppear {
            if viewModel.chatbotSessionId == nil {
                viewModel.createChatbotSession() {
                    viewModel.getFirstChatbotStep()
                }
            }
        }
    }
    
    @ViewBuilder
    private func makeMessage(index: Int) -> some View {
        let message = viewModel.messages[index]
        let lastSender = index > 0 ? viewModel.messages[index-1].sender : ChatMessageSender.bot
        let differentSender = viewModel.messages[index].sender != lastSender
        
        Divider()
            .accessibilityHidden(true)
            .frame(height: differentSender ? StackSpacing.xxxs : StackSpacing.quarck)
            .overlay(Color.white)
        
        if message.message == ChatbotViewMessages.writing.rawValue {
            if index == viewModel.messages.count-1 {
                makeLoadingMessage()
                    .padding(.leading, InsetSpacing.nano)
            }
        } else {
            setupChatMessageView(message)
        }
    }
    
    @ViewBuilder
    private func makeLoadingMessage() -> some View {
        HStack {
            TypingMessageChatView()
                .frame(width: 75, height: 35)
            Spacer()
        }
    }
    
    @ViewBuilder
    private func makeInputView() -> some View {
        switch viewModel.userInputType {
        case .choiceButton:
            ChatMultipleChoiceButtons(
                choices: viewModel.userChoices,
                fontColor: .secondaryDarkest,
                backgroundColor: Color.secondaryLightest,
                borderColor: .neutralLightest
            )
        case .anyDate, .pastDate, .futureDate:
            ChatDatePicker(
                fontColor: .secondaryDarkest,
                backgroundColor: Color.secondaryLightest,
                datePickerRange: datePickerRange,
                didSelectedDate: { selectedDate in
                    self.viewModel.userResponse(selectedDate)
                }
            ).environmentObject(choosenDate)
        case .freeText:
            ChatTextField(placeholder: "", didEndEditingAction: { text in
                self.viewModel.userResponse(text)
            })
        }
    }
    
    @ViewBuilder
    private func setupChatMessageView(_ message: ChatMessage) -> some View {
        let isFromBot = message.sender == .bot
        var style: ChatMessageStyle {
            switch message.sender {
            case .bot:
                message.isFirstMessage ? .roundedButTopLeft : .allRounded
            case .user:
                message.isFirstMessage ? .roundedButBottomRight : .allRounded
            }
        }
        
        ChatMessageView(
            style: style,
            alignment: isFromBot ? .left : .right,
            message: message.message,
            backgroundColor: isFromBot ? .neutralBackground : .secondaryLightest,
            avatar: isFromBot ? (message.isFirstMessage ? Image("javier") : nil) : nil
        )
    }
}
