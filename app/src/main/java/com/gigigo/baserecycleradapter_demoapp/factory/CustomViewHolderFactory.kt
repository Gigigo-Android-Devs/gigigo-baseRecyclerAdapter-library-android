package com.gigigo.baserecycleradapter_demoapp.factory

import android.content.Context
import android.view.ViewGroup

import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolderFactory
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell
import com.gigigo.baserecycleradapter_demoapp.viewholder.ImageViewHolder
import com.gigigo.baserecycleradapter_demoapp.viewholder.TextViewHolder
import com.gigigo.ui.imageloader.ImageLoader

/**
 * Created by rui.alonso on 8/11/16.
 */

class CustomViewHolderFactory(context: Context, private val imageLoader: ImageLoader) :
    BaseViewHolderFactory(context) {

    override fun create(valueClass: Class<*>, parent: ViewGroup): BaseViewHolder<*> {
        return when (valueClass) {
            ImageCell::class.java -> ImageViewHolder(context, parent, imageLoader) as BaseViewHolder<Any>
            TextCell::class.java -> TextViewHolder(context, parent) as BaseViewHolder<Any>
            else -> super.create(valueClass, parent)
        }
    }
}
