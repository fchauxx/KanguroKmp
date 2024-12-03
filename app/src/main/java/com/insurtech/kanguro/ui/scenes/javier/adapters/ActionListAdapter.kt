package com.insurtech.kanguro.ui.scenes.javier.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutActionItemBinding

class ActionListAdapter(private val list: List<String>) : RecyclerView.Adapter<ActionListAdapter.ActionListViewHolder>() {

    inner class ActionListViewHolder(private val binding: LayoutActionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.actionLabel.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutActionItemBinding.inflate(inflater, parent, false)

        return ActionListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ActionListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}
