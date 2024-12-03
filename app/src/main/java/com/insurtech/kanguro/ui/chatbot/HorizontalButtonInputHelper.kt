package com.insurtech.kanguro.ui.chatbot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutChatButtonBinding
import com.insurtech.kanguro.domain.chatbot.ChatAction

class HorizontalButtonInputHelper(
    val context: Context,
    val actions: List<ChatAction>?,
    val onActionSelected: (ChatAction) -> Unit
) : ChatbotInputHelper {

    private val inflater = LayoutInflater.from(context)

    private val dividerSize = context.resources.getDimension(R.dimen.chat_divider_size).toInt()

    override fun getLayout(): View {
        val linear = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                resources.getDimension(R.dimen.chatbot_action_panel).toInt()
            )
        }

        actions?.forEachIndexed { index, action ->
            val button = LayoutChatButtonBinding.inflate(inflater, linear, false).apply {
                root.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1f
                )
                text = action.label
                onClick = { onActionSelected(action) }
            }
            linear.addView(button.root)
            if (index < actions.lastIndex) {
                val separator = View(context).apply {
                    setBackgroundResource(R.color.neutral_lightest)
                    layoutParams = ViewGroup.LayoutParams(dividerSize, ViewGroup.LayoutParams.MATCH_PARENT)
                }
                linear.addView(separator)
            }
        }

        return linear
    }
}
