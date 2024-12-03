package com.insurtech.kanguro.domain.chatbot.enums

enum class ChatbotInputType {
    ButtonList, BankAccountInput, // This last one should be removed
    UploadPicture,
    TextInput,
    DateInput,
    NumberInput,
    CurrencyInput,
    Finish, // This should be removed in the future, treated as [ButtonList] for now
    UploadPetPicture
}
