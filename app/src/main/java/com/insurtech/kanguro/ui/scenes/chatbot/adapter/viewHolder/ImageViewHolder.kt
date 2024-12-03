package com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.updatePadding
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.ImageMessageBinding
import com.insurtech.kanguro.databinding.MessageItemBinding
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.ImageMessage

class ImageViewHolder(private val binding: MessageItemBinding) :
    ChatViewHolder(binding) {
    private var image: ShapeableImageView? = null

    private val cornerRadius = itemView.resources.getDimension(R.dimen.spacing_quark)

    override fun bind(item: ChatBotMessage, isFirstMessage: Boolean) {
        super.bind(item, isFirstMessage)
        if (image == null) {
            image = ImageMessageBinding.inflate(
                LayoutInflater.from(itemView.context),
                binding.contentFrame,
                false
            ).image
            binding.contentFrame.addView(image)
        }

        if (item is ImageMessage) {
            image?.shapeAppearanceModel = image!!.shapeAppearanceModel
                .toBuilder()
                .setTopLeftCorner(CornerFamily.ROUNDED, cornerRadius)
                .setTopRightCorner(CornerFamily.ROUNDED, cornerRadius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, cornerRadius)
                .build()

            Glide.with(image!!).load(item.localImage).into(image!!)

            if (item.isDeletable) {
                binding.deleteIcon.visibility = View.VISIBLE
                binding.root.updatePadding(
                    top = itemView.resources.getDimension(R.dimen.spacing_nano).toInt()
                )
            }

            binding.deleteIcon.setOnClickListener {
                item.localImage?.let { uri -> item.callback?.invoke(uri) }
            }
        }
    }
}
