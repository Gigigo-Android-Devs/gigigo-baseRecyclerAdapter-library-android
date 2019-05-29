package com.gigigo.baserecycleradapter.debouncedlisteners

import android.view.View
import com.gigigo.baserecycleradapter.viewholder.OnItemClickListener

internal interface DebouncedListener {
    fun onDebouncedClick(v: View, position: Int): Boolean
}

internal abstract class DebouncedOnClickListener protected constructor(
    millisIntervalToAvoidDoubleClick: Long? = null
) : OnItemClickListener, DebouncedListener {

    @Suppress("LeakingThis")
    private val debouncedClickHandler: DebouncedClickHandler =
        DebouncedClickHandler(this, millisIntervalToAvoidDoubleClick)

    override fun invoke(position: Int, view: View) {
        debouncedClickHandler.invokeDebouncedClick(position, view)
    }
}
