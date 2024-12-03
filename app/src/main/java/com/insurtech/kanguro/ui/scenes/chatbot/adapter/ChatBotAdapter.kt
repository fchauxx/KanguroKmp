package com.insurtech.kanguro.ui.scenes.chatbot.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutMessageClaimSummaryBinding
import com.insurtech.kanguro.databinding.MessageItemBinding
import com.insurtech.kanguro.ui.scenes.chatbot.*
import com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder.*
import com.squareup.moshi.Moshi

const val MESSAGE_TEXT_VIEW = 0
const val IMAGE_VIEW = 1
const val MAP_VIEW = 2
const val CLAIM_SUMMARY = 3
const val FILE_MESSAGE = 4

class ChatBotAdapter(private val moshi: Moshi, private val navigateToMap: () -> Unit) : ListAdapter<ChatBotMessage, RecyclerView.ViewHolder>(ChatBotMessageDiffUtil()) {

    companion object {

        class ChatBotMessageDiffUtil : DiffUtil.ItemCallback<ChatBotMessage>() {
            override fun areContentsTheSame(
                oldItem: ChatBotMessage,
                newItem: ChatBotMessage
            ): Boolean {
                return (oldItem as? TextMessage)?.message == (newItem as? TextMessage)?.message
            }

            override fun areItemsTheSame(
                oldItem: ChatBotMessage,
                newItem: ChatBotMessage
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            MESSAGE_TEXT_VIEW -> {
                val binding = MessageItemBinding.inflate(inflater, parent, false)
                MessageTextViewHolder(binding)
            }
            IMAGE_VIEW -> {
                val binding = MessageItemBinding.inflate(inflater, parent, false)
                ImageViewHolder(binding)
            }
            CLAIM_SUMMARY -> {
                val binding = LayoutMessageClaimSummaryBinding.inflate(inflater, parent, false)
                ClaimSummaryViewHolder(moshi, binding)
            }
            FILE_MESSAGE -> {
                val binding = MessageItemBinding.inflate(inflater, parent, false)
                FileTextViewHolder(binding)
            }
            else -> {
                val binding = MessageItemBinding.inflate(inflater, parent, false)
                MapViewHolder(binding, navigateToMap)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is ChatViewHolder -> {
                holder.bind(item, position == 0 || getItem(position - 1)?.sentBy != item.sentBy)
            }
            is ClaimSummaryViewHolder -> {
                holder.bind(getItem(position) as SummaryMessage)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TextMessage -> MESSAGE_TEXT_VIEW
            is ImageMessage -> IMAGE_VIEW
            is MapMessage -> MAP_VIEW
            is SummaryMessage -> CLAIM_SUMMARY
            is FileMessage -> FILE_MESSAGE
        }
    }
}
