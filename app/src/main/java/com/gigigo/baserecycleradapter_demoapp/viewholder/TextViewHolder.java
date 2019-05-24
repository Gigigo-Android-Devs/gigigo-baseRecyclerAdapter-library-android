package com.gigigo.baserecycleradapter_demoapp.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;
import com.gigigo.baserecycleradapter_demoapp.R;
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell;

public class TextViewHolder extends BaseViewHolder<TextCell> {

    private TextView textView;

    public TextViewHolder(Context context, ViewGroup parent) {
        super(context, parent, R.layout.widget_text_content_item);

        textView = (TextView) itemView.findViewById(R.id.text_view);

        //Note: besides viewholder onClick, textview has an own onClick
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Text clicked: " + ((TextView) v).getText(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void bindTo(TextCell item, int position) {
        textView.setText(item.getText());
    }
}
