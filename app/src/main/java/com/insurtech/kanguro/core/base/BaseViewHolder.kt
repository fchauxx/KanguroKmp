package com.insurtech.kanguro.core.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View, val itemClick: ((Int) -> Unit)? = null) : RecyclerView.ViewHolder(view) {

    abstract fun bindItem(item: T)
}
