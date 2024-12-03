package com.insurtech.kanguro.ui.chatbot

import android.content.Context
import android.os.Build
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import com.insurtech.kanguro.core.utils.KeyboardUtils
import com.insurtech.kanguro.databinding.FragmentEnterTextActionBinding
import com.insurtech.kanguro.domain.chatbot.ChatAction
import java.util.*

class TextInputHelper(
    val context: Context,
    val actions: List<ChatAction>?,
    val isNumericalEntry: Boolean,
    val onTextSubmitted: (String) -> Unit
) : ChatbotInputHelper {

    private val inflater = LayoutInflater.from(context)

    override fun getLayout(): View {
        val layout = FragmentEnterTextActionBinding.inflate(inflater)
        layout.sendIcon.setOnClickListener {
            sendText(layout.messageEditText)
        }

        layout.messageEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                sendText(layout.messageEditText)
            }
            false
        }

        if (isNumericalEntry) {
            layout.messageEditText.apply {
                inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                keyListener = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DigitsKeyListener(Locale.getDefault())
                } else {
                    DigitsKeyListener()
                }
            }
        }

        return layout.root
    }

    private fun sendText(editText: EditText) {
        val text = editText.text.trim()
        if (text.isBlank()) return
        KeyboardUtils.hideKeyboard(editText)
        onTextSubmitted(text.toString())
    }
}
