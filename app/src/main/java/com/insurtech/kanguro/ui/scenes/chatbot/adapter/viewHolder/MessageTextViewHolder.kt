package com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder

import android.view.LayoutInflater
import androidx.core.content.res.ResourcesCompat
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.MessageItemBinding
import com.insurtech.kanguro.databinding.TextMessageBinding
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.TextMessage

class MessageTextViewHolder(private val binding: MessageItemBinding) :
    ChatViewHolder(binding) {

    private val message = TextMessageBinding.inflate(LayoutInflater.from(itemView.context))

    override fun bind(item: ChatBotMessage, isFirstMessage: Boolean) {
        super.bind(item, isFirstMessage)

        if (item is TextMessage) {
            with(binding.contentFrame) {
                message.messageText.text = item.message.content

                removeAllViews()
                addView(message.root)

                if (item.isPending) {
                    val drawable = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_continue_small, null)
                    message.messageText.compoundDrawablePadding = 12
                    message.messageText.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        drawable,
                        null
                    )
                } else {
                    message.messageText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
                }
            }
        }
    }
}
