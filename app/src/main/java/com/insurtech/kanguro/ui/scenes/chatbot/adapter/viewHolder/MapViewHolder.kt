package com.insurtech.kanguro.ui.scenes.chatbot.adapter.viewHolder

import android.graphics.Bitmap
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.insurtech.kanguro.R
import com.insurtech.kanguro.databinding.MessageItemBinding
import com.insurtech.kanguro.databinding.MinimapMessageBinding
import com.insurtech.kanguro.designsystem.ui.views.MinimapImageView
import com.insurtech.kanguro.ui.scenes.chatbot.ChatBotMessage
import com.insurtech.kanguro.ui.scenes.chatbot.MapMessage

const val MAP_SIZE = 120

class MapViewHolder(private val binding: MessageItemBinding, private val navigateToMap: () -> Unit) :
    ChatViewHolder(binding) {

    private var image: MinimapImageView? = null

    override fun bind(item: ChatBotMessage, isFirstMessage: Boolean) {
        super.bind(item, isFirstMessage)
        if (image == null) {
            image = MinimapMessageBinding.inflate(
                LayoutInflater.from(itemView.context),
                binding.contentFrame,
                false
            ).image

            val layerSize =
                itemView.resources.getDimension(R.dimen.chatbot_message_layer_map_size).toInt()

            val layerTextWidth = itemView.resources.getDimension(R.dimen.chatbot_message_layer_text_width).toInt()
            val layerTextHeight = itemView.resources.getDimension(R.dimen.chatbot_message_layer_text_height).toInt()

            val drawableLayer =
                ContextCompat.getDrawable(itemView.context, R.drawable.minimap_background_layer)

            val layerToBitmap = drawableLayer?.toBitmap(
                layerSize,
                layerSize,
                Bitmap.Config.ARGB_8888
            )

            val textLayerToBitmap = drawableLayer?.toBitmap(
                layerTextWidth,
                layerTextHeight,
                Bitmap.Config.ARGB_8888
            )

            image?.apply {
                addLayer(layerToBitmap!!)
                addLayer(textLayerToBitmap!!)

                binding.contentFrame.addView(image)
            }
        }

        if (item is MapMessage) {
            val imageUrl =
                "https://maps.googleapis.com/maps/api/staticmap?center=Berkeley,CA&zoom=14&size=${MAP_SIZE}x$MAP_SIZE&key=${
                itemView.resources.getString(
                    R.string.maps_api_key
                )
                }"

            val cornerRadius = itemView.resources.getDimension(R.dimen.spacing_quark)

            image?.shapeAppearanceModel = image!!.shapeAppearanceModel
                .toBuilder()
                .setTopRightCorner(CornerFamily.ROUNDED, cornerRadius)
                .setBottomLeftCorner(CornerFamily.ROUNDED, cornerRadius)
                .setBottomRightCorner(CornerFamily.ROUNDED, cornerRadius)
                .build()

            Glide.with(itemView.context).load(imageUrl).into(image!!)

            itemView.setOnClickListener {
                navigateToMap.invoke()
            }
        }
    }
}
