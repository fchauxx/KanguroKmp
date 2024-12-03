package com.insurtech.kanguro.ui.scenes.cloud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutFolderCloudItemBinding

class PolicyFilesTypeAdapter(private val onTypePressed: (PolicyFilesType) -> Unit) :
    ListAdapter<PolicyFilesType, PolicyFilesTypeAdapter.PolicyFilesTypeViewHolder>(
        CloudPolicyDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PolicyFilesTypeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PolicyFilesTypeViewHolder(
            LayoutFolderCloudItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PolicyFilesTypeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PolicyFilesTypeViewHolder(private val binding: LayoutFolderCloudItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(policyFilesType: PolicyFilesType) {
            binding.documentButton.text = binding.root.context.getString(policyFilesType.res)

            binding.documentButton.setOnClickListener {
                onTypePressed(policyFilesType)
            }
        }
    }

    companion object {
        class CloudPolicyDiffUtil : DiffUtil.ItemCallback<PolicyFilesType>() {
            override fun areContentsTheSame(
                oldItem: PolicyFilesType,
                newItem: PolicyFilesType
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: PolicyFilesType,
                newItem: PolicyFilesType
            ): Boolean {
                return oldItem.res == newItem.res
            }
        }
    }
}
