package com.gigigo.baserecycleradapter.viewholder

import android.content.Context
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<Data> : RecyclerView.ViewHolder, View.OnLongClickListener,
    View.OnClickListener, View.OnDragListener {

    private var itemClickListener: OnItemClickListener? = null
    private var itemLongClickListener: OnItemLongClickListener? = null
    private var itemDragListener: OnItemDragListener? = null

    constructor(context: Context, parent: ViewGroup, layoutId: Int) : super(
        LayoutInflater.from(
            context
        ).inflate(layoutId, parent, false)
    ) {
        bindListeners()
    }

    constructor(itemView: View) : super(itemView) {}

    private fun bindListeners() {
        itemView.setOnClickListener(this)
        itemView.setOnLongClickListener(this)
        itemView.setOnDragListener(this)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener?) {
        this.itemClickListener = itemClickListener
    }

    fun setItemLongClickListener(itemLongClickListener: OnItemLongClickListener?) {
        this.itemLongClickListener = itemLongClickListener
    }

    fun setItemDragListener(itemDragClickListener: OnItemDragListener?) {
        this.itemDragListener = itemDragClickListener
    }

    override fun onClick(v: View) {
        itemClickListener?.onItemClick(layoutPosition, v)
    }

    override fun onLongClick(v: View): Boolean {
        return itemLongClickListener?.let { longClickListener ->
            longClickListener.onItemLongClicked(layoutPosition, itemView)
            true
        } ?: false
    }

    override fun onDrag(v: View, event: DragEvent): Boolean {
        return itemDragListener?.let { dragListener ->
            dragListener.onItemDragged(layoutPosition, v)
            true
        } ?: false
    }

    abstract fun bindTo(value: Data, position: Int)

    interface OnItemClickListener {
        fun onItemClick(position: Int, view: View)
    }

    interface OnItemLongClickListener {
        fun onItemLongClicked(position: Int, view: View): Boolean
    }

    interface OnItemDragListener {
        fun onItemDragged(position: Int, view: View): Boolean
    }
}
