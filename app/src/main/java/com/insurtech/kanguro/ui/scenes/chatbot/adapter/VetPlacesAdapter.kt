package com.insurtech.kanguro.ui.scenes.chatbot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.common.enums.PlaceStatus
import com.insurtech.kanguro.databinding.LayoutVetsCloserItemBinding
import com.insurtech.kanguro.domain.model.VetPlace

class VetPlacesAdapter(
    val onPhoneSelected: (VetPlace) -> Unit
) : ListAdapter<VetPlace, VetPlacesAdapter.ViewHolder>(VetPlacesDiffUtil()) {

    companion object {
        class VetPlacesDiffUtil : DiffUtil.ItemCallback<VetPlace>() {
            override fun areItemsTheSame(oldItem: VetPlace, newItem: VetPlace): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VetPlace, newItem: VetPlace): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(LayoutVetsCloserItemBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: LayoutVetsCloserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(vetPlace: VetPlace) {
            val context = binding.root.context

            val formattedDistance = String.format("%.2f", vetPlace.distanceFromUser)
            val addressText =
                "$formattedDistance mi - ${vetPlace.address.replaceFirstChar { it.uppercase() }}"

            binding.name.text = vetPlace.name
            binding.address.text = addressText

            vetPlace.status?.let { placeStatus ->
                binding.status.visibility = View.VISIBLE
                binding.status.text = context.resources.getString(placeStatus.value)
                binding.status.setTextColor(
                    ContextCompat.getColor(
                        context,
                        placeStatus.color
                    )
                )
            }

            vetPlace.operatingHour?.let {
                binding.operatingHours.visibility = View.VISIBLE
                binding.dash.visibility = View.VISIBLE

                when (vetPlace.status) {
                    PlaceStatus.CLOSED -> {
                        binding.operatingHours.text =
                            context.resources.getString(R.string.opens_at, vetPlace.operatingHour)
                    }
                    PlaceStatus.OPEN -> {
                        binding.operatingHours.text =
                            context.resources.getString(R.string.closes_at, vetPlace.operatingHour)
                    }
                    else -> {
                        binding.operatingHours.visibility = View.GONE
                    }
                }
            }

            vetPlace.phone?.let {
                binding.phoneButton.visibility = View.VISIBLE
            }

            binding.phoneButton.setOnClickListener {
                onPhoneSelected(vetPlace)
            }
        }
    }
}
