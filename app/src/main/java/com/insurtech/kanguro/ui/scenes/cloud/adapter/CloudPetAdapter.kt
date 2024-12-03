package com.insurtech.kanguro.ui.scenes.cloud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.databinding.LayoutFolderCloudItemBinding
import com.insurtech.kanguro.domain.model.CloudPet

class CloudPetAdapter(private val onPetPressed: (CloudPet) -> Unit) :
    ListAdapter<CloudPet, CloudPetAdapter.CloudPetViewHolder>(CloudPetDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloudPetViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CloudPetViewHolder(
            LayoutFolderCloudItemBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CloudPetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CloudPetViewHolder(private val binding: LayoutFolderCloudItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cloudPet: CloudPet) {
            binding.documentButton.text = cloudPet.name

            binding.documentButton.setOnClickListener {
                onPetPressed(cloudPet)
            }
        }
    }

    companion object {
        class CloudPetDiffUtil : DiffUtil.ItemCallback<CloudPet>() {
            override fun areContentsTheSame(
                oldItem: CloudPet,
                newItem: CloudPet
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: CloudPet,
                newItem: CloudPet
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
