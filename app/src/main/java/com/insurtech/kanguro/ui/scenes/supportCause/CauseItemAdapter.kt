package com.insurtech.kanguro.ui.scenes.supportCause

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.CauseListItemBinding

class CauseItemAdapter(val onCharitySelected: (CharityListItem) -> Unit) :
    ListAdapter<CharityListItem, CauseItemAdapter.CharityViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CauseListItemBinding.inflate(inflater, parent, false)
        return CharityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CharityViewHolder(private val binding: CauseListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharityListItem) {
            val context = binding.root.context

            binding.charityInitials.text = item.charityResponse.attributes?.abbreviatedTitle
            binding.charityTitle.text = item.charityResponse.attributes?.title
            binding.charityDescription.text = item.charityResponse.attributes?.description

            if (item.isExpanded) {
                binding.summaryIcon.setImageResource(R.drawable.ic_up)
                binding.charityInfo.visibility = View.VISIBLE
            } else {
                binding.summaryIcon.setImageResource(R.drawable.ic_down)
                binding.charityInfo.visibility = View.GONE
            }

            if (item.isLoading) {
                binding.chooseCauseButton.text = null
                binding.chooseCauseButton.icon = null
                binding.isLoading.isVisible = true
            } else {
                binding.chooseCauseButton.text = context.getString(R.string.choose_cause)
                binding.chooseCauseButton.icon = ContextCompat.getDrawable(context, R.drawable.ic_heart_search_white)
                binding.isLoading.isVisible = false
            }

            binding.headerTitle.setOnClickListener {
                item.isExpanded = !item.isExpanded
                notifyItemChanged(this.bindingAdapterPosition)
            }

            binding.chooseCauseButton.setOnClickListener {
                onCharitySelected(item)
                item.isLoading = true
                notifyItemChanged(this.bindingAdapterPosition)
            }
        }
    }

    companion object {

        object DiffCallback : DiffUtil.ItemCallback<CharityListItem>() {
            override fun areContentsTheSame(
                oldItem: CharityListItem,
                newItem: CharityListItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: CharityListItem,
                newItem: CharityListItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
