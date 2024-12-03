package com.insurtech.kanguro.core.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget

/**
 * A target for display [GifDrawable] or [Drawable] objects in [ImageView]s.
 */
class GifDrawableImageViewTarget : ImageViewTarget<Drawable?> {

    private var mLoopCount: Int = GifDrawable.LOOP_FOREVER

    constructor(view: ImageView?, loopCount: Int) : super(view) {
        mLoopCount = loopCount
    }

    override fun setResource(resource: Drawable?) {
        if (resource is GifDrawable) {
            resource.setLoopCount(mLoopCount)
            resource.start()
        }
        view.setImageDrawable(resource)
    }
}
