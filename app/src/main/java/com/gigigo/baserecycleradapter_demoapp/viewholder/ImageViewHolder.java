package com.gigigo.baserecycleradapter_demoapp.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;
import com.gigigo.baserecycleradapter_demoapp.MainActivity;
import com.gigigo.baserecycleradapter_demoapp.R;
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell;
import com.gigigo.ui.imageloader.ImageLoader;


public class ImageViewHolder extends BaseViewHolder<ImageCell> {

  private ImageLoader imageLoader;
  private ImageView imageView;

  public ImageViewHolder(Context context, ViewGroup parent) {
    super(context, parent, R.layout.widget_image_content_item);

    imageLoader = ((MainActivity)itemView.getContext()).getImageLoader();
    imageView = (ImageView) itemView.findViewById(R.id.image_view);

    bindListeners();
  }

  public ImageViewHolder(Context context, ViewGroup parent, ImageLoader imageLoader) {
    super(context, parent, R.layout.widget_image_content_item);

    this.imageLoader = imageLoader;
    imageView = (ImageView) itemView.findViewById(R.id.image_view);

    bindListeners();
  }

  public void bindListeners() {
    setItemClickListener(new OnItemClickListener() {
      @Override public void onItemClick(int position, View view) {
        Toast.makeText(view.getContext(), "Clicked position: " + getLayoutPosition(),
            Toast.LENGTH_SHORT).show();
      }
    });
    setItemLongClickListener(new OnItemLongClickListener() {
      @Override public boolean onItemLongClicked(int position, View view) {
        Toast.makeText(view.getContext(), "Long clicked position: " + getLayoutPosition(),
            Toast.LENGTH_SHORT).show();
        return false;
      }
    });
    setItemDragListener(new OnItemDragListener() {
      @Override public boolean OnItemDragged(int position, View view) {
        Toast.makeText(view.getContext(), "Dragged position: " + getLayoutPosition(),
            Toast.LENGTH_SHORT).show();
        return false;
      }
    });
  }

  @Override public void bindTo(ImageCell item, int position) {
    imageLoader.load(item.getUrl()).placeholder( R.color.colorAccent).error( R.color.colorAccent).into(imageView);
  }
}
