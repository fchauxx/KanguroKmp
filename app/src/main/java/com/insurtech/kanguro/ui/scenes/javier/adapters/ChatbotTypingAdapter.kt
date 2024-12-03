package com.insurtech.kanguro.ui.scenes.javier.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutChatbotTypingBinding

class ChatbotTypingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isTyping = false

    override fun getItemCount(): Int {
        return if (isTyping) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutChatbotTypingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatbotTypingViewHolder(binding)
    }

    fun setIsTyping(isTyping: Boolean) {
        this.isTyping = isTyping
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }
}

class ChatbotTypingViewHolder(val binding: LayoutChatbotTypingBinding) : RecyclerView.ViewHolder(binding.root)
