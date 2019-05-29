package com.gigigo.baserecycleradapter_demoapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.gigigo.baserecycleradapter.adapter.BaseRecyclerAdapter
import com.gigigo.baserecycleradapter.viewholder.BaseViewHolder
import com.gigigo.baserecycleradapter_demoapp.entities.ImageCell
import com.gigigo.baserecycleradapter_demoapp.entities.TextCell
import com.gigigo.baserecycleradapter_demoapp.factory.CustomViewHolderFactory
import com.gigigo.baserecycleradapter_demoapp.viewholder.ImageViewHolder
import com.gigigo.baserecycleradapter_demoapp.viewholder.TextViewHolder
import com.gigigo.ui.imageloader.ImageLoader
import com.gigigo.ui.imageloader.glide.GlideImageLoaderImp

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var adapter: BaseRecyclerAdapter<Any>? = null

    var imageLoader: ImageLoader? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val buttonFill = findViewById<View>(R.id.button_fill) as Button
        val buttonClear = findViewById<View>(R.id.button_clear) as Button

        buttonFill.setOnClickListener { fillData(DataGenerator.generateRandomDataList(25)) }

        buttonClear.setOnClickListener { clearData() }
        imageLoader = GlideImageLoaderImp(this)

        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        val customViewHolderFactory = CustomViewHolderFactory(this, imageLoader!!)
        adapter = BaseRecyclerAdapter(customViewHolderFactory)
        adapter?.run {
            bind<ImageCell, ImageViewHolder>()
            bind<TextCell, TextViewHolder>()
            setMillisIntervalToAvoidDoubleClick(1500)

            setItemClickListener { position, view ->
                val element = getItem(position)
                Toast.makeText(
                    this@MainActivity,
                    "Item " + view.javaClass.simpleName + " clicked: " + position,
                    Toast.LENGTH_SHORT
                ).show()
            }

            setItemLongClickListener { position, view ->
                Toast.makeText(
                    this@MainActivity,
                    "Item " + view.javaClass.simpleName + " long clicked: " + position,
                    Toast.LENGTH_SHORT
                ).show()
                true
            }

            setItemDragListener { position, view ->
                Toast.makeText(
                    this@MainActivity,
                    "Item " + view.javaClass.simpleName + " dragged: " + position,
                    Toast.LENGTH_SHORT
                ).show()
                true
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView!!.adapter = adapter
        recyclerView!!.layoutManager = GridLayoutManager(this, 3)

    }

    fun clearData() {
        adapter!!.clear()
    }

    private fun fillData(data: List<Any>) {
        adapter!!.append(data)
    }
}
