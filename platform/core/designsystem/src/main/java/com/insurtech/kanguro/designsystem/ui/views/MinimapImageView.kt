package com.insurtech.kanguro.designsystem.ui.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.imageview.ShapeableImageView
import com.insurtech.kanguro.designsystem.R

class MinimapImageView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) : ShapeableImageView(context, attributeSet) {

    private var layers = ArrayList<Bitmap>()
    private val paint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.white)
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimension(R.dimen.font_xxs)

        val plain = ResourcesCompat.getFont(context, R.font.lato)
        typeface = plain
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val mapSize = resources.getDimension(R.dimen.chatbot_message_map_size)

        val layerSize = resources.getDimension(R.dimen.chatbot_message_layer_map_size)
        val size = (mapSize - layerSize) / 2

        val textWidth = (mapSize - resources.getDimension(R.dimen.chatbot_message_layer_text_width)) / 2
        val textHeight = (mapSize - resources.getDimension(R.dimen.chatbot_message_layer_text_height)) / 2
        val textSpanHeight = (paint.fontMetrics.bottom * 1.5).toFloat()

        val textX = mapSize / 2
        val textY = mapSize / 2 + textSpanHeight

        canvas.apply {
            drawBitmap(layers[0], size, size, null)
            drawBitmap(layers[1], textWidth, textHeight, null)
        }

        canvas.drawText("See map", textX, textY, paint)
    }

    fun addLayer(bitmap: Bitmap) {
        layers.add(bitmap)
        invalidate()
    }
}
