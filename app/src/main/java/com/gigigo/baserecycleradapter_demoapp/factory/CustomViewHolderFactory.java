package com.gigigo.baserecycleradapter_demoapp.factory;

import android.content.Context;
import android.view.ViewGroup;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolderFactory;
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell;
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell;
import com.gigigo.baserecycleradapter_demoapp.viewholder.ImageViewHolder;
import com.gigigo.baserecycleradapter_demoapp.viewholder.TextViewHolder;
import com.gigigo.ui.imageloader.ImageLoader;

/**
 * Created by rui.alonso on 8/11/16.
 */

public class CustomViewHolderFactory extends BaseViewHolderFactory {

  private ImageLoader imageLoader;

  public CustomViewHolderFactory(Context context, ImageLoader imageLoader) {
    super(context);
    this.imageLoader = imageLoader;
  }

  @Override public BaseViewHolder create(Class valueClass, ViewGroup parent) {
    if (valueClass == ImageCell.class) {
      return new ImageViewHolder(context, parent, imageLoader);
    } else if (valueClass == TextCell.class) {
      return new TextViewHolder(context, parent);
    } else {
      return null;
    }
  }
}
