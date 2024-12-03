package com.insurtech.kanguro.ui.scenes.cloud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutFolderCloudItemBinding
import com.insurtech.kanguro.domain.model.CloudClaimDocument

class CloudClaimAdapter(private val onClaimPressed: (CloudClaimDocument) -> Unit) :
    ListAdapter<CloudClaimDocument, CloudClaimAdapter.CloudClaimViewHolder>(CloudPolicyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloudClaimViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CloudClaimViewHolder(
            LayoutFolderCloudItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CloudClaimViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CloudClaimViewHolder(private val binding: LayoutFolderCloudItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cloudClaimDocument: CloudClaimDocument) {
            val context = binding.root.context
            binding.documentButton.text =
                context.getString(R.string.claim_number, cloudClaimDocument.claimPrefixId)

            binding.documentButton.setOnClickListener {
                onClaimPressed(cloudClaimDocument)
            }
        }
    }

    companion object {
        class CloudPolicyDiffUtil : DiffUtil.ItemCallback<CloudClaimDocument>() {
            override fun areContentsTheSame(
                oldItem: CloudClaimDocument,
                newItem: CloudClaimDocument
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: CloudClaimDocument,
                newItem: CloudClaimDocument
            ): Boolean {
                return oldItem.claimId == newItem.claimId
            }
        }
    }
}
