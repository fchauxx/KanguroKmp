package com.insurtech.kanguro.ui.scenes.wellnessPreventive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.convertToCurrency
import com.insurtech.kanguro.databinding.CoveragesItemBinding
import com.insurtech.kanguro.domain.model.PreventiveCoverageInfo
import kotlin.math.roundToInt

class CoveragesItemAdapter(
    val onCoverageSelected: (String) -> Unit,
    val onCoverageDeselected: (String) -> Unit
) : ListAdapter<PreventiveCoverageInfo, CoveragesItemAdapter.CoveragesItemAdapterViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoveragesItemAdapterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CoveragesItemBinding.inflate(inflater, parent, false)
        return CoveragesItemAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CoveragesItemAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CoveragesItemAdapterViewHolder(private val binding: CoveragesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context

        fun bind(item: PreventiveCoverageInfo) {
            binding.coverageName.text = item.name

            val completed = item.isCompleted()
            binding.labelCompleted.isVisible = completed

            if (completed) {
                binding.checkboxButton.visibility = View.GONE

                binding.coveragesItemAnnualLimit.isVisible = false

                binding.availabilitySlider.isVisible = false
                binding.labelAvailability.isVisible = false

                binding.remainingValue.text = convertToCurrency(0.0)

                return
            }

            binding.remainingValue.text = item.remainingValue?.let { convertToCurrency(it) }

            binding.coveragesItemAnnualLimit.isVisible = true
            binding.coveragesItemAnnualLimit.text =
                context.getString(
                    R.string.up_to,
                    item.annualLimit?.let { convertToCurrency(it) }
                )

            when (item.usesLimit) {
                null, 1 -> setupNullOrOneUseLimit()
                else -> setupMultipleUses(item)
            }

            binding.checkboxButton.setOnCheckedChangeListener { _, isChecked ->
                item.name?.let {
                    if (isChecked) {
                        onCoverageSelected(it)
                    } else {
                        onCoverageDeselected(it)
                    }
                }
            }
        }

        private fun setupNullOrOneUseLimit() {
            binding.availabilitySlider.isVisible = false
            binding.labelAvailability.isVisible = false
        }

        private fun setupMultipleUses(item: PreventiveCoverageInfo) {
            binding.availabilitySlider.isVisible = true
            binding.availabilitySlider.setProgressCompat(
                (item.getAvailablePercentage() * 100).roundToInt(),
                true
            )

            binding.labelAvailability.isVisible = true
            binding.labelAvailability.text = context.getString(
                R.string.availableValue,
                item.remainingUses,
                item.uses
            )
        }
    }

    companion object {
        object DiffCallback : DiffUtil.ItemCallback<PreventiveCoverageInfo>() {
            override fun areContentsTheSame(
                oldItem: PreventiveCoverageInfo,
                newItem: PreventiveCoverageInfo
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: PreventiveCoverageInfo,
                newItem: PreventiveCoverageInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
