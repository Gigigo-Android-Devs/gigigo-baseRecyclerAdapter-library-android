package com.gigigo.baserecycleradapter.debouncedlisteners

import android.os.SystemClock
import android.view.View

open class DebouncedClickHandler(
    intervalMillis: Long? = MINIMUM_INTERVAL_MILLIS,
    private val debouncedOnClickListener: (Int, View) -> Boolean
) {

    var intervalMillis = MINIMUM_INTERVAL_MILLIS
    var previousClickTimestamp: Long = 0

    init {
        intervalMillis?.let { this.intervalMillis = it }
    }

    fun invoke(position: Int, view: View): Boolean {
        val currentTimestamp = SystemClock.uptimeMillis()
        var handled = false
        if (currentTimestamp - previousClickTimestamp > intervalMillis) {
            handled = debouncedOnClickListener.invoke(position, view)
            previousClickTimestamp = currentTimestamp
        }
        return handled
    }

    companion object {
        private const val MINIMUM_INTERVAL_MILLIS: Long = 300
    }
}
