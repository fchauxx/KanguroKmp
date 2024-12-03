package com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.updatePadding
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.MessageItemBinding
import com.insurtech.kanguro.databinding.TextMessageBinding
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.FileMessage

class FileTextViewHolder(private val binding: MessageItemBinding) :
    ChatViewHolder(binding) {

    private val message = TextMessageBinding.inflate(LayoutInflater.from(itemView.context))

    override fun bind(item: ChatBotMessage, isFirstMessage: Boolean) {
        super.bind(item, isFirstMessage)

        if (item is FileMessage) {
            with(binding.contentFrame) {
                message.messageText.text = item.message.content

                removeAllViews()
                addView(message.root)

                if (item.isDeletable) {
                    binding.deleteIcon.visibility = View.VISIBLE
                    binding.root.updatePadding(
                        top = itemView.resources.getDimension(R.dimen.spacing_nano).toInt()
                    )
                }

                binding.deleteIcon.setOnClickListener {
                    item.uri?.let { uri -> item.callback?.invoke(uri) }
                }
            }
        }
    }
}
