package com.insurtech.kanguro.ui.scenes.claims

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.*
import com.insurtech.kanguro.domain.model.ClaimDocument

class ClaimDocumentAdapter(private val onDocumentPressed: (ClaimDocument) -> Unit) :
    ListAdapter<ClaimDocument, ClaimDocumentAdapter.ClaimDocumentViewHolder>(ClaimDocumentDiffUtil()) {

    companion object {
        private const val KILOBYTE = 1024

        class ClaimDocumentDiffUtil : DiffUtil.ItemCallback<ClaimDocument>() {
            override fun areContentsTheSame(
                oldItem: ClaimDocument,
                newItem: ClaimDocument
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: ClaimDocument,
                newItem: ClaimDocument
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClaimDocumentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ClaimDocumentViewHolder(
            LayoutClaimAttachmentItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClaimDocumentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ClaimDocumentViewHolder(private val binding: LayoutClaimAttachmentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(claimDocument: ClaimDocument) {
            val context = binding.root.context

            binding.fileName.text = claimDocument.fileName

            val fileSize = claimDocument.fileSize?.div(KILOBYTE)
            binding.fileSize.text =
                context.resources.getString(R.string.file_size, fileSize.toString())

            binding.root.setOnClickListener {
                onDocumentPressed(claimDocument)
            }
        }
    }
}
