package com.insurtech.kanguro.ui.chatbot

import android.content.Context
import com.insurtech.kanguro.domain.chatbot.ChatInteractionStep
import com.insurtech.kanguro.domain.chatbot.enums.ButtonOrientation
import com.insurtech.kanguro.domain.chatbot.enums.ChatbotInputType
import com.insurtech.kanguro.ui.scenes.javier.ChatbotActionPressedListener

class ChatbotInputHelperFactory(val context: Context, val listener: ChatbotActionPressedListener) {

    fun getInputForInteractionStep(step: ChatInteractionStep) = when (val type = step.type) {
        ChatbotInputType.ButtonList, ChatbotInputType.BankAccountInput, ChatbotInputType.Finish -> {
            if (step.orientation == ButtonOrientation.Vertical) {
                VerticalButtonInputHelper(context, step.actions) { listener.onButtonPressed(it) }
            } else {
                HorizontalButtonInputHelper(context, step.actions) { listener.onButtonPressed(it) }
            }
        }
        ChatbotInputType.UploadPicture -> {
            FileSelectInputHelper(
                context = context,
                action = type.toString(),
                onOpenCameraPressed = listener::onOpenCameraPressed,
                onSelectFilePressed = listener::onSelectFilesPressed,
                onSelectImagePressed = listener::onSelectImagesPressed
            )
        }
        // Finish no longer exists. Finish will only exist as an Action
//        ChatbotInputType.Finish -> {
//            HorizontalButtonInputHelper(
//                context,
//                listOf(ChatAction(label = type.toString()))
//            ) { listener.onFinishPressed() }
//        }
        ChatbotInputType.TextInput, ChatbotInputType.NumberInput -> {
            TextInputHelper(
                context = context,
                actions = step.actions,
                isNumericalEntry = type != ChatbotInputType.TextInput
            ) { listener.onUserInputSubmitted(it) }
        }
        ChatbotInputType.CurrencyInput -> {
            CurrencyInputHelper(
                context = context,
                actions = step.actions
            ) { listener.onUserInputSubmitted(it) }
        }
        ChatbotInputType.DateInput -> {
            DateInputHelper(context, step.actions) { listener.onUserInputSubmitted(it) }
        }

        ChatbotInputType.UploadPetPicture -> {
            PictureSelectInputHelper(
                context = context,
                action = type.toString(),
                onOpenCameraPressed = listener::onOpenCameraPressed,
                onSelectImagePressed = listener::onSelectImagesPressed
            )
        }
        else -> null
    }
}
