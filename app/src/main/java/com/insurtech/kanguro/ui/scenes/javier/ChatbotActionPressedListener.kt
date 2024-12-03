package com.insurtech.kanguro.ui.scenes.javier

import com.insurtech.kanguro.domain.chatbot.ChatAction

interface ChatbotActionPressedListener {
    fun onButtonPressed(action: ChatAction)
    fun onUserInputSubmitted(text: String)
    fun onSelectImagesPressed(action: String)
    fun onSelectFilesPressed(action: String)
    fun onOpenCameraPressed(action: String)
    fun onFinishPressed()
}
