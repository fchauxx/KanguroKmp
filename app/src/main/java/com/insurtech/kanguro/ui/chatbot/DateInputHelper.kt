package com.insurtech.kanguro.ui.chatbot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutDatePickerChatbotBinding
import com.insurtech.kanguro.domain.chatbot.ChatAction
import java.util.*

class DateInputHelper(
    val context: Context,
    val actions: List<ChatAction>?,
    val onTextSubmitted: (String) -> Unit
) : ChatbotInputHelper {

    private val inflater = LayoutInflater.from(context)

    override fun getLayout(): View {
        val layout = LayoutDatePickerChatbotBinding.inflate(inflater)
        layout.datePicker.maxDate = Calendar.getInstance().timeInMillis
        layout.submitLayout.onClick = {
            onSubmitDatePressed(
                layout.datePicker.dayOfMonth,
                layout.datePicker.month + 1,
                layout.datePicker.year
            )
        }
        return layout.root
    }

    private fun onSubmitDatePressed(day: Int, month: Int, year: Int) {
        onTextSubmitted.invoke(context.getString(R.string.chatbot_date, day, month, year))
    }
}
