package com.gigigo.baserecycleradapter.viewholder

import android.content.Context
import android.view.ViewGroup
import java.lang.reflect.Constructor
import java.util.*

open class BaseViewHolderFactory(protected var context: Context) {

    val boundViewHolders = HashMap<Class<*>, Class<out BaseViewHolder<*>>>()

    open fun create(valueClass: Class<*>, parent: ViewGroup): BaseViewHolder<*> {
        try {
            val viewHolderClass = boundViewHolders[valueClass]
            val constructor: Constructor<out BaseViewHolder<*>>
            constructor =
                viewHolderClass!!.getDeclaredConstructor(Context::class.java, ViewGroup::class.java)
            return constructor.newInstance(context, parent)
        } catch (ex: Throwable) {
            throw RuntimeException(
                "Unable to create ViewHolder for $valueClass. ${ex.cause?.message}",
                ex
            )
        }
    }

    inline fun <reified Data: Any, reified ViewHolder: BaseViewHolder<Data>> bind() {
        boundViewHolders[Data::class.java] = ViewHolder::class.java
    }
}
