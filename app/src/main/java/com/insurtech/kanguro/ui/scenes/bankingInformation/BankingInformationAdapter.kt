package com.insurtech.kanguro.ui.scenes.bankingInformation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.BanksListItemBinding
import com.insurtech.kanguro.domain.model.BankInfo

class BankingInformationAdapter(val onBankSelected: (String) -> Unit) :
    ListAdapter<BankInfo, BankingInformationAdapter.BankingInformationViewHolder>(
        DiffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BankingInformationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = BanksListItemBinding.inflate(inflater, parent, false)
        return BankingInformationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BankingInformationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BankingInformationViewHolder(private val binding: BanksListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BankInfo) {
            binding.banks = item
            binding.root.setOnClickListener { onBankSelected(item.name) }
            binding.executePendingBindings()
        }
    }

    companion object {

        object DiffCallback : DiffUtil.ItemCallback<BankInfo>() {
            override fun areContentsTheSame(
                oldItem: BankInfo,
                newItem: BankInfo
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: BankInfo,
                newItem: BankInfo
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
