package com.insurtech.kanguro.ui.scenes.javier.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutChatbotClaimsHeaderBinding

class ChatbotHeaderAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutChatbotClaimsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}
}

class HeaderViewHolder(val binding: LayoutChatbotClaimsHeaderBinding) : RecyclerView.ViewHolder(binding.root)
