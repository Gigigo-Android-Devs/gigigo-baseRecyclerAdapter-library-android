package com.gigigo.baserecycleradapter.debouncedlisteners

import android.view.View

import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder

internal abstract class DebouncedOnLongClickListener protected constructor() : DebouncedListener,
    BaseViewHolder.OnItemLongClickListener {

    @Suppress("LeakingThis")
    private val debouncedClickHandler: DebouncedClickHandler = DebouncedClickHandler(this)

    override fun onItemLongClicked(position: Int, view: View): Boolean =
        debouncedClickHandler.invokeDebouncedClick(position, view)
}
