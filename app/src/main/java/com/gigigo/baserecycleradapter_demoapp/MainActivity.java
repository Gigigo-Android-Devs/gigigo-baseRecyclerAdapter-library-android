package com.gigigo.baserecycleradapter_demoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.gigigo.baserecycleradapter.adapter.BaseRecyclerAdapter;
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder;
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell;
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell;
import com.gigigo.baserecycleradapter_demoapp.factory.CustomViewHolderFactory;
import com.gigigo.baserecycleradapter_demoapp.viewholder.ImageViewHolder;
import com.gigigo.baserecycleradapter_demoapp.viewholder.TextViewHolder;
import com.gigigo.ui.imageloader.ImageLoader;

import com.gigigo.ui.imageloader.glide.GlideImageLoaderImp;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private BaseRecyclerAdapter adapter;

  private ImageLoader imageLoader;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initView();
  }

  private void initView() {
    Button buttonFill = (Button) findViewById(R.id.button_fill);
    Button buttonClear = (Button) findViewById(R.id.button_clear);

    buttonFill.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        fillData(DataGenerator.generateRandomDataList(25));
      }
    });

    buttonClear.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        clearData();
      }
    });
    imageLoader = new GlideImageLoaderImp(this);

    initAdapter();
    initRecyclerView();
  }

  private void initAdapter() {
    CustomViewHolderFactory customViewHolderFactory = new CustomViewHolderFactory(this, imageLoader);
    adapter = new BaseRecyclerAdapter(customViewHolderFactory);
    adapter.bind(ImageCell.class, ImageViewHolder.class);
    adapter.bind(TextCell.class, TextViewHolder.class);
    adapter.setMillisIntervalToAvoidDoubleClick(1500);
    adapter.setItemClickListener(new BaseViewHolder.OnItemClickListener() {
      @Override public void onItemClick(int position, View view) {
        Toast.makeText(MainActivity.this, "Pulsado: "+ position, Toast.LENGTH_SHORT).show();
      }
    });
  }

  private void initRecyclerView() {
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerView.setAdapter(adapter);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

  }

  public void clearData() {
    adapter.clear();
  }

  @SuppressWarnings("unchecked") private void fillData(List<Object> data) {
    adapter.append(data);
  }

  public ImageLoader getImageLoader() {
    return imageLoader;
  }
}
