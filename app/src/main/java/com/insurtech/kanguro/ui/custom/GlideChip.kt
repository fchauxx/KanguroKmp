package com.insurtech.kanguro.ui.custom

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.chip.Chip

class GlideChip : Chip {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    fun setIconUrl(glideUrl: GlideUrl?, placeholder: Int) {
        loadAndSetIcon(glideUrl, placeholder)
    }

    fun setIconUrl(url: String?, placeholder: Int) {
        loadAndSetIcon(url, placeholder)
    }

    private fun loadAndSetIcon(url: Any?, placeholder: Int) {
        Glide.with(this)
            .load(url)
            .circleCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable,
                    model: Any,
                    target: Target<Drawable>?,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    isChipIconVisible = true
                    chipIcon = resource
                    return false
                }
            })
            .preload()
    }
}
