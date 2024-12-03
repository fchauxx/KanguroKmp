package com.insurtech.kanguro.ui.scenes.cloud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.LayoutFileCloudItemBinding
import com.insurtech.kanguro.ui.scenes.cloud.CloudFileDocument

class CloudFileDocumentAdapter(private val onFilePressed: (CloudFileDocument) -> Unit) :
    ListAdapter<CloudFileDocument, CloudFileDocumentAdapter.CloudFileDocumentViewHolder>(
        CloudFileDocumentDiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloudFileDocumentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CloudFileDocumentViewHolder(
            LayoutFileCloudItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CloudFileDocumentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CloudFileDocumentViewHolder(private val binding: LayoutFileCloudItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(file: CloudFileDocument) {
            val context = binding.root.context
            binding.fileName.text = file.visibleName

            val fileSize = file.fileSize?.div(KILOBYTE)
            binding.fileSize.text = context.getString(R.string.file_size, fileSize.toString())
            binding.fileSize.isVisible = fileSize != null

            binding.root.setOnClickListener {
                onFilePressed(file)
            }
        }
    }

    companion object {
        private const val KILOBYTE = 1024

        class CloudFileDocumentDiffUtil : DiffUtil.ItemCallback<CloudFileDocument>() {
            override fun areContentsTheSame(
                oldItem: CloudFileDocument,
                newItem: CloudFileDocument
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: CloudFileDocument,
                newItem: CloudFileDocument
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
