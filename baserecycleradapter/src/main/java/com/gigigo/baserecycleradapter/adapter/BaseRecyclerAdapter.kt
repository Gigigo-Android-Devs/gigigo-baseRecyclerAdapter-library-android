package com.gigigo.baserecycleradapter.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gigigo.baserecycleradapter.debouncedlisteners.DebouncedOnClickListener
import com.gigigo.baserecycleradapter.debouncedlisteners.DebouncedOnLongClickListener
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolderFactory
import java.util.*

class BaseRecyclerAdapter<V : Any>(private val viewHolderFactory: BaseViewHolderFactory) :
    RecyclerView.Adapter<BaseViewHolder<Any>>() {

    private val valueClassTypes = ArrayList<Class<*>>()
    private val _data = ArrayList<V>()
    val data: Collection<V>
        get() = _data

    private var itemClickListener: BaseViewHolder.OnItemClickListener? = null
    private var itemLongClickListener: BaseViewHolder.OnItemLongClickListener? = null
    private var itemDragListener: BaseViewHolder.OnItemDragListener? = null
    private var millisIntervalToAvoidDoubleClick: Long = 0

    constructor(context: Context) : this(BaseViewHolderFactory(context)) {}

    constructor(
        context: Context, valueClass: Class<*>,
        viewHolderClass: Class<out BaseViewHolder<Any>>
    ) : this(context) {
        bind(valueClass, viewHolderClass)
    }

    constructor(
        baseBaseViewHolderFactory: BaseViewHolderFactory, valueClass: Class<*>,
        viewHolderClass: Class<out BaseViewHolder<Any>>
    ) : this(baseBaseViewHolderFactory) {
        bind(valueClass, viewHolderClass)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Any> {
        var index = viewType
        if (!isValidIndex(viewType)) {
            index = 0
            Log.w("BaseRecyclerAdapter", "onCreateViewHolder() invalid type")
        }
        val viewHolder = viewHolderFactory.create(valueClassTypes[index], parent)
        bindListeners(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Any>, position: Int) {
        try {
            holder.bindTo(_data[position], position)
        } catch (e: Exception) {
            Log.e("BaseRecyclerAdapter", "onBindViewHolder()", e)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isValidIndex(position)) {
            valueClassTypes.indexOf(_data[position].javaClass)
        } else {
            Log.w("BaseRecyclerAdapter", "getItemViewType invalid index position $position")
            0
        }
    }

    override fun getItemCount(): Int = _data.size

    fun bind(valueClass: Class<*>, viewHolderClass: Class<out BaseViewHolder<Any>>) {
        valueClassTypes.add(valueClass)
        viewHolderFactory.bind(valueClass, viewHolderClass)
    }

    fun setItemClickListener(itemClickListener: BaseViewHolder.OnItemClickListener?) {
        this.itemClickListener =
            object : DebouncedOnClickListener(millisIntervalToAvoidDoubleClick) {
                override fun onDebouncedClick(v: View, position: Int): Boolean {
                    itemClickListener?.let {
                        return if (isValidIndex(position)) {
                            it.onItemClick(position, v)
                            true
                        } else {
                            false
                        }
                    }
                    return false
                }
            }
    }

    fun setItemLongClickListener(itemLongClickListener: BaseViewHolder.OnItemLongClickListener?) {
        this.itemLongClickListener = object : DebouncedOnLongClickListener() {
            override fun onDebouncedClick(v: View, position: Int): Boolean {
                itemLongClickListener?.let {
                    return if (isValidIndex(position)) {
                        it.onItemLongClicked(position, v)
                        true
                    } else {
                        false
                    }
                }
                return false
            }
        }
    }

    fun setItemDragListener(itemDragListener: BaseViewHolder.OnItemDragListener?) {
        itemDragListener?.let {
            this.itemDragListener = it
        }
    }

    fun add(item: V) {
        val lastPosition = itemCount - 1
        _data.add(item)
        notifyItemInserted(lastPosition)
    }

    fun addAt(item: V, position: Int) {
        val validIndex = isValidIndex(position)
        if (validIndex) {
            _data.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun addAll(items: Collection<V>) {
        _data.clear()
        append(items)
    }

    fun append(items: Collection<V>) {
        _data.addAll(items)
        notifyDataSetChanged()
    }

    fun remove(item: V): Boolean {
        val position = getIndex(item)
        val validIndex = isValidIndex(position)
        return if (validIndex) {
            removeAt(position)
            notifyItemRemoved(position)
            true
        } else {
            false
        }
    }

    fun removeAt(position: Int): Boolean {
        val validIndex = isValidIndex(position)
        if (validIndex) {
            _data.removeAt(position)
            notifyItemRemoved(position)
        }
        return validIndex
    }

    fun clear() {
        _data.clear()
        notifyDataSetChanged()
    }


    fun getIndex(item: V): Int = _data.indexOf(item)

    fun getItem(position: Int): V? {
        val validIndex = isValidIndex(position)
        return if (validIndex) {
            _data[position]
        } else {
            null
        }
    }

    fun setMillisIntervalToAvoidDoubleClick(millisIntervalToAvoidDoubleClick: Long) {
        this.millisIntervalToAvoidDoubleClick = millisIntervalToAvoidDoubleClick
    }

    private fun isValidIndex(position: Int): Boolean = position in 0 until itemCount

    private fun bindListeners(viewHolder: BaseViewHolder<Any>?) {
        viewHolder?.apply {
            setItemClickListener(itemClickListener)
            setItemLongClickListener(itemLongClickListener)
            setItemDragListener(itemDragListener)
        }
    }
}
