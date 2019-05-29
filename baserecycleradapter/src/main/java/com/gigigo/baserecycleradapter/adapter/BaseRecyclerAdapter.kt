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

class BaseRecyclerAdapter<Data : Any>(val viewHolderFactory: BaseViewHolderFactory) :
    RecyclerView.Adapter<BaseViewHolder<Data>>() {

    val valueClassTypes = ArrayList<Class<*>>()
    private val _data = ArrayList<Data>()
    val data: Collection<Data>
        get() = _data

    private var itemClickListener: BaseViewHolder.OnItemClickListener? = null
    private var itemLongClickListener: BaseViewHolder.OnItemLongClickListener? = null
    private var itemDragListener: BaseViewHolder.OnItemDragListener? = null
    private var millisIntervalToAvoidDoubleClick: Long = 0

    constructor(context: Context) : this(BaseViewHolderFactory(context)) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Data> {
        var index = viewType
        if (!isValidIndex(viewType)) {
            index = 0
            Log.w("BaseRecyclerAdapter", "onCreateViewHolder() invalid type")
        }
        val viewHolder =
            viewHolderFactory.create(valueClassTypes[index], parent) as BaseViewHolder<Data>
        bindListeners(viewHolder)
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<Data>, position: Int) {
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

    inline fun <reified Data : Any, reified ViewHolder : BaseViewHolder<Data>> bind() {
        valueClassTypes.add(Data::class.java)
        viewHolderFactory.bind<Data, ViewHolder>()
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

    fun add(item: Data) {
        val lastPosition = itemCount - 1
        _data.add(item)
        notifyItemInserted(lastPosition)
    }

    fun addAt(item: Data, position: Int) {
        val validIndex = isValidIndex(position)
        if (validIndex) {
            _data.add(position, item)
            notifyItemInserted(position)
        }
    }

    fun addAll(items: Collection<Data>) {
        _data.clear()
        append(items)
    }

    fun append(items: Collection<Data>) {
        _data.addAll(items)
        notifyDataSetChanged()
    }

    fun remove(item: Data): Boolean {
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


    fun getIndex(item: Data): Int = _data.indexOf(item)

    fun getItem(position: Int): Data? {
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

    private fun isValidIndex(position: Int): Boolean = position in 0..itemCount

    private fun bindListeners(viewHolder: BaseViewHolder<Data>?) {
        viewHolder?.apply {
            setItemClickListener(itemClickListener)
            setItemLongClickListener(itemLongClickListener)
            setItemDragListener(itemDragListener)
        }
    }
}
