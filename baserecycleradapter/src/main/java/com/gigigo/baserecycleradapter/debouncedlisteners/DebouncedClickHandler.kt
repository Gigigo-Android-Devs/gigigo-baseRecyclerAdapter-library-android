package com.gigigo.baserecycleradapter.debouncedlisteners

import android.os.SystemClock
import android.view.View

internal class DebouncedClickHandler(
    private val debouncedOnClickListener: DebouncedListener,
    intervalMillis: Long? = MINIMUM_INTERVAL_MILLIS
) {

    private var intervalMillis = MINIMUM_INTERVAL_MILLIS
    private var previousClickTimestamp: Long = 0

    init {
        intervalMillis?.let { this.intervalMillis = it }
    }

    fun invokeDebouncedClick(position: Int, view: View): Boolean {
        val currentTimestamp = SystemClock.uptimeMillis()
        var handled = false
        if (currentTimestamp - previousClickTimestamp > intervalMillis) {
            handled = debouncedOnClickListener.onDebouncedClick(view, position)
            previousClickTimestamp = currentTimestamp
        }
        return handled
    }

    companion object {
        private const val MINIMUM_INTERVAL_MILLIS: Long = 300
    }
}
