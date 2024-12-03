package com.insurtech.kanguro.ui.scenes.reminders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.core.utils.getCacheableImage
import com.insurtech.kanguro.core.utils.getPlaceholderImage
import com.insurtech.kanguro.core.utils.setGlideUrlWithPlaceholder
import com.insurtech.kanguro.databinding.LayoutReminderWideBinding
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.ReminderType

class RemindersAdapter(val onReminderSelected: (Reminder) -> Unit) :
    ListAdapter<Reminder, RemindersAdapter.ReminderViewHolder>(RemindersDiffUtil()) {

    companion object {

        class RemindersDiffUtil : DiffUtil.ItemCallback<Reminder>() {
            override fun areContentsTheSame(
                oldItem: Reminder,
                newItem: Reminder
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: Reminder,
                newItem: Reminder
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ReminderViewHolder(LayoutReminderWideBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ReminderViewHolder(private val binding: LayoutReminderWideBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reminder: Reminder) {
            val context = binding.root.context

            binding.root.background =
                ContextCompat.getDrawable(
                    context,
                    ReminderListItemUtils.getBackgroundStroke(reminder.type)
                )

            binding.root.setOnClickListener { onReminderSelected(reminder) }

            binding.reminderTitle.text = context.resources.getString(reminder.type.label)

            binding.reminderSubtitle.text = ReminderListItemUtils.getSubtitle(context, reminder)

            binding.reminderSubtitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    ReminderListItemUtils.getSubtitleColor(reminder.type)
                )
            )

            binding.petName.text = reminder.pet.name

            val petCacheableImage = reminder.pet.getCacheableImage()
            val petPlaceHolderImage = reminder.pet.getPlaceholderImage()
            binding.headerImage.setGlideUrlWithPlaceholder(
                petCacheableImage,
                petPlaceHolderImage
            )

            binding.claimWarningIcon.isVisible = reminder.type == ReminderType.Claim
        }
    }
}
