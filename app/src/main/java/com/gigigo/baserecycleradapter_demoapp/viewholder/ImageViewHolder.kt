package com.gigigo.baserecycleradapter_demoapp.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder
import com.gigigo.baserecycleradapter_demoapp.R
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell
import com.gigigo.ui.imageloader.ImageLoader

class ImageViewHolder(
    private val context: Context,
    parent: ViewGroup,
    private val imageLoader: ImageLoader
) : BaseViewHolder<ImageCell>(context, parent, R.layout.widget_image_content_item) {

    private val imageView = itemView.findViewById<View>(R.id.image_view) as ImageView


    override fun bindTo(item: ImageCell, position: Int) {
        imageView?.setOnClickListener { view ->
            //in this case, we propagate click to viewholder
            onClick(view)
        }
        imageView?.setOnLongClickListener { view ->
            //never propagate to adapter due to imageview match-parent
            Toast.makeText(
                view.context, "Image long clicked position: $layoutPosition",
                Toast.LENGTH_SHORT
            ).show()

            true
        }

        imageLoader?.load(item.url)
            ?.placeholder(R.color.colorAccent)
            ?.error(R.color.colorAccent)
            ?.into(imageView)
    }
}
