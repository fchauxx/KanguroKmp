package com.insurtech.kanguro.ui.scenes.vetAdvice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.FrequentlyAskedQuestionsCardBinding

class FrequentlyAskedQuestionsAdapter : RecyclerView.Adapter<FrequentlyAskedQuestionsAdapter.FrequentlyAskedQuestionsViewHolder>() {

    override fun getItemCount(): Int {
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrequentlyAskedQuestionsViewHolder {
        val binding = FrequentlyAskedQuestionsCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FrequentlyAskedQuestionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FrequentlyAskedQuestionsViewHolder, position: Int) {
    }

    class FrequentlyAskedQuestionsViewHolder(private val binding: FrequentlyAskedQuestionsCardBinding) : RecyclerView.ViewHolder(binding.root)
}
