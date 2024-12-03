package com.insurtech.kanguro.core.base

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected var dataList = ArrayList<T>()

    protected var onSelectItem: ((Int) -> Unit)? = null

    @LayoutRes
    protected abstract fun getLayoutIdForPosition(position: Int): Int

    protected abstract fun onCreateVH(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return onCreateVH(parent, viewType)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        if (position in dataList.indices) {
            holder.bindItem(getItem(position))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private fun getItem(position: Int): T {
        return dataList[position]
    }

    fun getItemAtPosition(position: Int): T? {
        return dataList.getOrNull(position)
    }

    fun setItemClickListener(action: ((Int) -> Unit)?) {
        onSelectItem = action
    }

    fun setList(list: List<T>) {
        dataList = ArrayList(list)
        notifyDataSetChanged()
    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun addItem(item: T) {
        dataList.add(item)
        notifyItemInserted(dataList.lastIndex)
    }
}
