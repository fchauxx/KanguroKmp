package com.insurtech.kanguro.ui.scenes.cloud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutFolderCloudItemBinding
import com.insurtech.kanguro.domain.model.CloudDocumentPolicy

class CloudPolicyAdapter(private val onPolicyPressed: (CloudDocumentPolicy) -> Unit) :
    ListAdapter<CloudDocumentPolicy, CloudPolicyAdapter.CloudPolicyViewHolder>(CloudPolicyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloudPolicyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CloudPolicyViewHolder(
            LayoutFolderCloudItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CloudPolicyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CloudPolicyViewHolder(private val binding: LayoutFolderCloudItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(documentPolicy: CloudDocumentPolicy) {
            binding.documentButton.text = documentPolicy.ciId.toString()

            binding.documentButton.setOnClickListener {
                onPolicyPressed(documentPolicy)
            }
        }
    }

    companion object {
        class CloudPolicyDiffUtil : DiffUtil.ItemCallback<CloudDocumentPolicy>() {
            override fun areContentsTheSame(
                oldItem: CloudDocumentPolicy,
                newItem: CloudDocumentPolicy
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: CloudDocumentPolicy,
                newItem: CloudDocumentPolicy
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
