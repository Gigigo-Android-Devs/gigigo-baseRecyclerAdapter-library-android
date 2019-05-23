package com.gigigo.baserecycleradapter_demoapp.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast

import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder
import com.gigigo.baserecycleradapter_demoapp.MainActivity
import com.gigigo.baserecycleradapter_demoapp.R
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell
import com.gigigo.ui.imageloader.ImageLoader


class ImageViewHolder : BaseViewHolder<ImageCell> {

    private var imageLoader: ImageLoader? = null
    private var imageView: ImageView? = null

    constructor(context: Context, parent: ViewGroup) : super(
        context,
        parent,
        R.layout.widget_image_content_item
    ) {

        imageLoader = (itemView.context as MainActivity).imageLoader
        imageView = itemView.findViewById<View>(R.id.image_view) as ImageView

        bindListeners()
    }

    constructor(context: Context, parent: ViewGroup, imageLoader: ImageLoader) : super(
        context,
        parent,
        R.layout.widget_image_content_item
    ) {

        this.imageLoader = imageLoader
        imageView = itemView.findViewById<View>(R.id.image_view) as ImageView

        imageView!!.setOnClickListener { v ->
            this@ImageViewHolder.onClick(v)
            /*onClick(Integer(2))*/
        }

        bindListeners()
    }

    fun bindListeners() {
        setItemClickListener(object : OnItemClickListener {
            override fun onItemClick(position: Int, view: View) {
                Toast.makeText(
                    view.context, "Clicked position: $layoutPosition",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        setItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClicked(position: Int, view: View): Boolean {
                Toast.makeText(
                    view.context, "Long clicked position: $layoutPosition",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        })
        setItemDragListener(object : OnItemDragListener {
            override fun OnItemDragged(position: Int, view: View): Boolean {
                Toast.makeText(
                    view.context, "Dragged position: $layoutPosition",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        })
    }

    override fun bindTo(item: ImageCell, position: Int) {
        imageLoader!!.load(item.url).placeholder(R.color.colorAccent).error(R.color.colorAccent)
            .into(imageView)
    }
}
