package com.insurtech.kanguro.ui.scenes.vetAdvice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.AdviceListItemBinding
import com.insurtech.kanguro.domain.model.KanguroParameter

class VetAdvicesAdapter :
    ListAdapter<KanguroParameter, VetAdvicesAdapter.ItemViewHolder>(DiffCallback) {

    var openIndex: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            AdviceListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: AdviceListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: KanguroParameter) {
            binding.advice = item
            binding.root.setOnClickListener {
                val closeIndex = openIndex
                openIndex =
                    if (openIndex == bindingAdapterPosition) {
                        null
                    } else {
                        bindingAdapterPosition
                    }
                notifyItemChanged(bindingAdapterPosition)
                closeIndex?.let { it -> notifyItemChanged(it) }
            }
            updateAccordionBackground()
            binding.executePendingBindings()
        }

        private fun updateAccordionBackground() {
            if (openIndex == bindingAdapterPosition) {
                binding.accordionBackground = R.drawable.bg_card_options_open
                binding.accordionArrow = R.drawable.ic_up
                binding.isAccordionVisible = true
            } else {
                binding.accordionBackground = R.drawable.bg_card_options
                binding.accordionArrow = R.drawable.ic_down
                binding.isAccordionVisible = false
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<KanguroParameter>() {
        override fun areItemsTheSame(oldItem: KanguroParameter, newItem: KanguroParameter): Boolean {
            return oldItem.description == newItem.description
        }

        override fun areContentsTheSame(oldItem: KanguroParameter, newItem: KanguroParameter): Boolean {
            return oldItem == newItem
        }
    }
}
