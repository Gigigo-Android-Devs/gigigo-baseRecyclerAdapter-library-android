package com.gigigo.baserecycleradapter_demoapp.viewholder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder
import com.gigigo.baserecycleradapter_demoapp.R
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell

class TextViewHolder(
    private val context: Context,
    parent: ViewGroup
) :
    BaseViewHolder<TextCell>(context, parent, R.layout.widget_text_content_item) {

    private val textView = itemView.findViewById<View>(R.id.text_view) as TextView

    override fun bindTo(item: TextCell, position: Int) {
        textView.text = item.text

        //Note: besides viewholder onClick, textview has an own onClick
        textView.setOnClickListener { v ->
            Toast.makeText(
                v.context, "Text clicked: " + (v as TextView).text,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
