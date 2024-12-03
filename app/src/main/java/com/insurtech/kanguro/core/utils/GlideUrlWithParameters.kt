package com.insurtech.kanguro.core.utils

import androidx.core.net.toUri
import com.bumptech.glide.load.model.GlideUrl

class GlideUrlWithParameters(private val url: String) : GlideUrl(url) {

    override fun getCacheKey(): String {
        return url.toUri().buildUpon().clearQuery().toString()
    }
}
