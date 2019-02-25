package com.gigigo.baserecycleradapter.debouncedlisteners;

import android.view.View;

import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;

public abstract class DebouncedOnLongClickListener
        implements DebouncedListener, BaseViewHolder.OnItemLongClickListener {

    private final DebouncedClickHandler debouncedClickHandler;

    protected DebouncedOnLongClickListener() {
        this.debouncedClickHandler = new DebouncedClickHandler(this);
    }

    @Override
    public boolean onItemLongClicked(int position, View view) {
        return debouncedClickHandler.invokeDebouncedClick(position, view);
    }
}
