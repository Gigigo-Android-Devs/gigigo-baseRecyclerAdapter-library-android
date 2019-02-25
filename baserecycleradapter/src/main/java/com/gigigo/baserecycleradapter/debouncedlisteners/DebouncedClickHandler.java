package com.gigigo.baserecycleradapter.debouncedlisteners;

import android.os.SystemClock;
import android.view.View;

public class DebouncedClickHandler {

    private final static long MINIMUM_INTERVAL_MILLIS = 300;

    private long intervalMillis = MINIMUM_INTERVAL_MILLIS;
    private long previousClickTimestamp;
    private final DebouncedListener debouncedOnClickListener;

    public DebouncedClickHandler(DebouncedListener debouncedOnClickListener) {
        this.debouncedOnClickListener = debouncedOnClickListener;
    }

    public DebouncedClickHandler(DebouncedListener debouncedOnClickListener, long intervalMillis) {
        this.debouncedOnClickListener = debouncedOnClickListener;
        this.intervalMillis = intervalMillis;
    }

    public boolean invokeDebouncedClick(int position, View view) {
        long currentTimestamp = SystemClock.uptimeMillis();
        boolean handled = false;
        if (currentTimestamp - previousClickTimestamp > intervalMillis) {
            handled = debouncedOnClickListener.onDebouncedClick(view, position);
            previousClickTimestamp = currentTimestamp;
        }
        return handled;
    }
}
