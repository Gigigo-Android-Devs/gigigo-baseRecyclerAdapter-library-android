package com.gigigo.baserecycleradapter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.gigigo.baserecycleradapter.debouncedlisteners.DebouncedOnClickListener;
import com.gigigo.baserecycleradapter.debouncedlisteners.DebouncedOnLongClickListener;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

@SuppressWarnings("rawtypes")
public class BaseRecyclerAdapter<V>
        extends RecyclerView.Adapter<BaseViewHolder> {

    private BaseViewHolderFactory viewHolderFactory;
    private List<Class> valueClassTypes = new ArrayList<>();

    private List<V> data = new ArrayList<>();

    private BaseViewHolder.OnItemClickListener itemClickListener;
    private BaseViewHolder.OnItemLongClickListener itemLongClickListener;
    private BaseViewHolder.OnItemDragListener itemDragListener;
    private long millisIntervalToAvoidDoubleClick;

    public BaseRecyclerAdapter(Context context, Class valueClass,
                               Class<? extends BaseViewHolder> viewHolderClass) {
        this(context);
        bind(valueClass, viewHolderClass);
    }

    public BaseRecyclerAdapter(Context context) {
        this(new BaseViewHolderFactory(context));
    }

    public BaseRecyclerAdapter(BaseViewHolderFactory baseBaseViewHolderFactory, Class valueClass,
                               Class<? extends BaseViewHolder> viewHolderClass) {
        this(baseBaseViewHolderFactory);
        bind(valueClass, viewHolderClass);
    }

    public BaseRecyclerAdapter(BaseViewHolderFactory baseBaseViewHolderFactory) {
        this.viewHolderFactory = baseBaseViewHolderFactory;
    }

    public void bind(Class valueClass, Class<? extends BaseViewHolder> viewHolderClass) {
        valueClassTypes.add(valueClass);
        viewHolderFactory.bind(valueClass, viewHolderClass);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int index = viewType;
        if (!isValidIndex(viewType)) {
            index = 0;
        }
        BaseViewHolder viewHolder = viewHolderFactory.create(valueClassTypes.get(index), parent);
        bindListeners(viewHolder);
        return viewHolder;
    }

    private void bindListeners(BaseViewHolder viewHolder) {
        if (viewHolder != null) {
            viewHolder.setItemClickListener(itemClickListener);
            viewHolder.setItemLongClickListener(itemLongClickListener);
            viewHolder.setItemDragListener(itemDragListener);
        }
    }

    public void setItemClickListener(final BaseViewHolder.OnItemClickListener itemClickListener) {
        this.itemClickListener = new DebouncedOnClickListener(millisIntervalToAvoidDoubleClick) {

            @Override
            public boolean onDebouncedClick(View v, int position) {
                if (itemClickListener != null) {
                    boolean validIndex = isValidIndex(position);
                    if (validIndex) {
                        itemClickListener.onItemClick(position, v);
                    }
                }
                return true;
            }
        };
    }

    public void setItemLongClickListener(
            final BaseViewHolder.OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = new DebouncedOnLongClickListener() {
            @Override
            public boolean onDebouncedClick(View v, int position) {
                if (itemLongClickListener != null) {
                    return itemLongClickListener.onItemLongClicked(position, v);
                }
                return false;
            }
        };
    }

    public void setItemDragListener(BaseViewHolder.OnItemDragListener itemDragListener) {
        this.itemDragListener = itemDragListener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        try {
            holder.bindTo(data.get(position), position);
        } catch (Exception e) {
            Log.e("BaseRecyclerAdapter", "onBindViewHolder()", e);
        }
    }

    @Override
    public int getItemViewType(int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            return valueClassTypes.indexOf(data.get(position).getClass());
        } else {
            Log.e("BaseRecyclerAdapter", "getItemViewType()");
            return 0;
        }
    }

    public void add(V item) {
        data.add(item);
        notifyDataSetChanged();
    }

    public void addAt(V item, int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            data.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void addAll(List<V> items) {
        data.clear();
        append(items);
    }

    public void append(List<V> items) {
        data.addAll(items);
        notifyDataSetChanged();
    }

    public boolean remove(V item) {
        int position = getIndex(item);
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            return removeAt(position);
        } else {
            return false;
        }
    }

    public boolean removeAt(int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            data.remove(position);
            notifyItemRemoved(position);
        }
        return validIndex;
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }

    private boolean isValidIndex(int position) {
        return (position >= 0 && position < getItemCount());
    }

    public int getIndex(V item) {
        return data.indexOf(item);
    }

    public V getItem(int position) {
        boolean validIndex = isValidIndex(position);
        if (validIndex) {
            return data.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setMillisIntervalToAvoidDoubleClick(long millisIntervalToAvoidDoubleClick) {
        this.millisIntervalToAvoidDoubleClick = millisIntervalToAvoidDoubleClick;
    }
}
