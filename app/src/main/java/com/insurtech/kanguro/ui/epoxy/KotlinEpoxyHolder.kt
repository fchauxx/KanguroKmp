package com.insurtech.kanguro.ui.epoxy

import android.view.View
import com.airbnb.epoxy.EpoxyHolder

abstract class KotlinEpoxyHolder : EpoxyHolder() {
    private lateinit var view: View

    override fun bindView(itemView: View) {
        view = itemView
    }

    protected fun <T : View> bind(id: Int): Lazy<T> = lazy { view.findViewById(id) }
}
