# baseRecyclerAdapter
[![Build Status](https://travis-ci.org/Gigigo-Android-Devs/gigigo-baseRecyclerAdapter-library-android.svg?branch=master)](https://travis-ci.org/Gigigo-Android-Devs/gigigo-baseRecyclerAdapter-library-android.svg?branch=master)
[![](https://jitpack.io/v/Gigigo-Android-Devs/gigigo-imageLoader-library-android.svg)](https://jitpack.io/#Gigigo-Android-Devs/gigigo-baseRecyclerAdapter-library-android)

BaseAdapter for RecyclerView with ViewHolder Factory

## Version
2.2.2
### Gradle
```
implementation "com.github.Gigigo-Android-Devs:baserecycleradapter:2.2.2"
```

[EasyRecyclerAdapter]: <https://github.com/CarlosMChica/easyrecycleradapters>

## How to use it
### ViewHolder
``` 
class MyViewHolder1(
    private val context: Context,
    parent: ViewGroup,
    private val otherComponent: OtherComponent
) : BaseViewHolder<MyModel>(context, parent, R.layout.item_layout) {

    private val elementView = itemView.findViewById<View>(R.id.element_view)

    override fun bindTo(item: MyModel1, position: Int) {
        elementView?.setOnClickListener { view ->
            //in this case, we propagate click to viewholder
            onClick(view)
        }
        elementView?.setOnLongClickListener { view ->
            //never propagate to adapter due to imageview match-parent
            Toast.makeText(
                view.context, "Image long clicked position: $layoutPosition",
                Toast.LENGTH_SHORT
            ).show()

            true
        }
    }
}
```
---

### ViewHolderFactory
```
    class MyViewHolderFactory(
        context: Context, 
        private val otherComponent: OtherComponent
    ) : BaseViewHolderFactory(context) {

    override fun create(valueClass: Class<*>, parent: ViewGroup): BaseViewHolder<*> {
        return if (valueClass == MyModel1::class.java) {
            MyViewHolder1(context, parent, otherComponent)
        } else if (valueClass == MyModel1::class.java) {
            MyViewHolder2(context, parent)
        } else {
            null
        }
    }
}
```
---

### Adapter

Create adapter from a `Context`:
```
    val myAdapter = BaseRecyclerAdapter(context)
``` 
or from a `BaseViewHolderFactory` if you need more parameters to construct custom `BaseViewHolder`
```
    val otherComponent = OtherComponent() //
    val myViewHolderFactory = MyViewHolderFactory(context, otherComponent)
    val myAdapter = BaseRecyclerAdapter(myViewHolderFactory)
```

Bind `Model` to `ViewHolder`. Can bind many models to different viewholders. Model and viewholder classes go by reified types
```
    myAdapter.bind<MyModel, MyViewHolder>()
    myAdapter.bind<MyModel2, MyViewHolder2>()
``` 

#### Methods

Set click listeners for click events from viewholders (click, longclick, dragg):
```
    myAdapter.setItemClickListener { position: Int, view: View ->
        //block
    }
    myAdapter.setItemLongClickListener { position: Int, view: View ->
        //block
    }
    myAdapter.setItemDragListener { position: Int, view: View ->
        //block
    }
```

to modify adapter data: 
```
    fun add(item: Data)

    fun addAt(item: Data, position: Int)

    fun addAll(items: Collection<Data>) //Clear and add

    fun append(items: Collection<Data>) 

    fun remove(item: Data)

    fun removeAt(position: Int)

    fun clear()
```


## Based on
* [EasyRecyclerAdapter] to perform Adapters and ViewHolder factory

## License

Copyright 2016 Gigigo.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   <http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


