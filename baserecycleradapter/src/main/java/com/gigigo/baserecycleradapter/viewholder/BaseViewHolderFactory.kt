package com.gigigo.baserecycleradapter.viewholder

import android.content.Context
import android.view.ViewGroup
import java.lang.reflect.Constructor
import java.util.*

open class BaseViewHolderFactory(protected var context: Context) {

    private val boundViewHolders = HashMap<Class<*>, Class<out BaseViewHolder<Any>>>()

    open fun create(valueClass: Class<*>, parent: ViewGroup): BaseViewHolder<Any> {
        try {
            val viewHolderClass = boundViewHolders[valueClass]
            val constructor: Constructor<out BaseViewHolder<Any>>
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

    fun bind(valueClass: Class<*>, viewHolderClass: Class<out BaseViewHolder<Any>>) {
        boundViewHolders[valueClass] = viewHolderClass
    }
}
