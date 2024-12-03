package com.insurtech.kanguro.designsystem.ui.composables.chatbot.model

// TODO: This model is temporary
data class ChatbotMessageModelUi(
    val message: String,
    val sender: Sender
)

fun getChatMessageModelListMock() = listOf(
    ChatbotMessageModelUi(
        message = "Hi! I'm Javier and I will be helping you onboard today.",
        sender = Sender.CHATBOT
    ),
    ChatbotMessageModelUi(
        message = "Before we start, let me ask you a few questions.",
        sender = Sender.CHATBOT
    ),
    ChatbotMessageModelUi(
        message = "Do you have a spouse?",
        sender = Sender.CHATBOT
    ),
    ChatbotMessageModelUi(
        message = "Yes",
        sender = Sender.USER
    ),
    ChatbotMessageModelUi(
        message = "What's the name of you spouse?",
        sender = Sender.CHATBOT
    ),
    ChatbotMessageModelUi(
        message = "Lauren Ipsum",
        sender = Sender.USER
    ),
    ChatbotMessageModelUi(
        message = "02/16/1989",
        sender = Sender.USER
    ),
    ChatbotMessageModelUi(
        message = "Do you have children?",
        sender = Sender.CHATBOT
    )
)

enum class Sender {
    USER, CHATBOT
}
