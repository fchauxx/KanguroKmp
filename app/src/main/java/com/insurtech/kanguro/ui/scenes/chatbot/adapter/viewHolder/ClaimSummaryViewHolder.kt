package com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutMessageClaimSummaryBinding
import com.insurtech.kanguro.domain.claimsChatbot.ClaimSummary
import com.insurtech.kanguro.ui.scenes.chatbot.SummaryMessage
import com.squareup.moshi.Moshi

class ClaimSummaryViewHolder(
    private val moshi: Moshi,
    private val binding: LayoutMessageClaimSummaryBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val summaryAdapter = moshi.adapter(ClaimSummary::class.java)

    fun bind(message: SummaryMessage) {
        summaryAdapter.fromJson(message.message.content.orEmpty())?.let { summary ->
            binding.summary = summary
        }
    }
}
