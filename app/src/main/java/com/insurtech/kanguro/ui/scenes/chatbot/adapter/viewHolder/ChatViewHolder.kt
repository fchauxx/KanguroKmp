package com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.RecyclerView
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.MessageItemBinding
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.types.SentBy

abstract class ChatViewHolder(private val binding: MessageItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    open fun bind(item: ChatBotMessage, isFirstMessage: Boolean) {
        binding.parentFrame.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topMargin = itemView.resources.getDimension(
                if (isFirstMessage) {
                    R.dimen.spacing_xxxs
                } else {
                    R.dimen.spacing_quark
                }
            ).toInt()
        }

        binding.deleteIcon.visibility = View.GONE
        binding.root.updatePadding(
            top = itemView.resources.getDimension(R.dimen.spacing_nano).toInt()
        )

        when (item.sentBy) {
            SentBy.User -> {
                with(binding) {
                    botIcon.isVisible = false
                    parentFrame.updatePadding(
                        left = itemView.resources.getDimension(R.dimen.spacing_xs).toInt(),
                        right = 0
                    )

                    with(contentFrame) {
                        if (item.isEditing) {
                            setBackgroundResource(R.drawable.sent_last_editing_message_background)
                        } else if (item.isPending) {
                            setBackgroundResource(R.drawable.sent_last_pending_message_background)
                        } else {
                            setBackgroundResource(R.drawable.sent_last_message_background)
                        }

                        updateLayoutParams<FrameLayout.LayoutParams> {
                            gravity = Gravity.END
                        }
                    }
                }
            }

            SentBy.Bot -> {
                with(binding) {
                    botIcon.isInvisible = !isFirstMessage
                    parentFrame.updatePadding(
                        right = itemView.resources.getDimension(R.dimen.spacing_xs).toInt(),
                        left = 0
                    )

                    with(contentFrame) {
                        setBackgroundResource(
                            if (isFirstMessage) {
                                R.drawable.received_first_message_background
                            } else {
                                R.drawable.received_message_background
                            }
                        )

                        updateLayoutParams<FrameLayout.LayoutParams> {
                            gravity = Gravity.START
                        }
                    }
                }
            }
        }
    }
}
