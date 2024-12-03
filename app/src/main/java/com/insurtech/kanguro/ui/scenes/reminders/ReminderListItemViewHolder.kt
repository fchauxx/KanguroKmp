package com.insurtech.kanguro.ui.scenes.reminders

import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.insurtech.kanguro.R
import com.insurtech.kanguro.core.utils.getCacheableImage
import com.insurtech.kanguro.core.utils.getPlaceholderImage
import com.insurtech.kanguro.core.utils.setGlideUrlWithPlaceholder
import com.insurtech.kanguro.databinding.LayoutReminderListItemBinding
import com.insurtech.kanguro.domain.dashboard.Reminder
import com.insurtech.kanguro.domain.dashboard.ReminderType

@EpoxyModelClass(layout = R.layout.layout_reminder_list_item)
abstract class ReminderListItemViewHolder : DataBindingEpoxyModel() {

    @EpoxyAttribute
    var reminder: Reminder? = null

    @EpoxyAttribute
    var onClickAction: ((Reminder) -> Unit)? = null

    override fun setDataBindingVariables(binding: ViewDataBinding?) {
        (binding as? LayoutReminderListItemBinding)?.let { viewBinding ->
            this@ReminderListItemViewHolder.reminder?.let { reminder ->
                val context = viewBinding.root.context

                viewBinding.root.background =
                    ContextCompat.getDrawable(
                        context,
                        ReminderListItemUtils.getBackground(reminder.type)
                    )

                viewBinding.root.setOnClickListener {
                    onClickAction?.invoke(reminder)
                }

                viewBinding.reminderTitle.text = context.resources.getString(reminder.type.label)

                viewBinding.reminderSubtitle.text =
                    ReminderListItemUtils.getSubtitle(context, reminder)
                viewBinding.reminderSubtitle.setTextColor(
                    ContextCompat.getColor(
                        context,
                        ReminderListItemUtils.getSubtitleColor(reminder.type)
                    )
                )

                viewBinding.petName.text = reminder.pet.name

                viewBinding.claimWarningIcon.isVisible = reminder.type == ReminderType.Claim

                val petCacheableImage = reminder.pet.getCacheableImage()
                val petPlaceHolderImage = reminder.pet.getPlaceholderImage()
                viewBinding.headerImage.setGlideUrlWithPlaceholder(
                    petCacheableImage,
                    petPlaceHolderImage
                )
            }
        }
    }
}
