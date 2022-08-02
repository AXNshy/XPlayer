package com.luffy.smartplay.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope

abstract class BaseBindingAdapter<T : ViewBinding>(val scope:CoroutineScope? = null) :
    RecyclerView.Adapter<BindingViewHolder<T>>() {

    var eventListener : EventListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<T> {
        return BindingViewHolder(
            createViewHolder(
                LayoutInflater.from(parent.context),
                parent,
                viewType
            )
        )
    }

    abstract fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): T


    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        eventListener?.onItemClickListener(holder.holder.root,position)
        eventListener?.onItemLongClickListener(holder.holder.root,position)
    }

    class EventListener {
        var itemClickListener: (view: View, position: Int) -> Unit = { _, _-> }
        var itemLongClickListener: (view: View, position: Int) -> Boolean =
            { _, _ -> false }

        fun onItemClickListener(view:View,position: Int){
            view.setOnClickListener {
                itemClickListener.invoke(it,position)
            }
        }

        fun onItemLongClickListener(view:View,position: Int){
            view.setOnLongClickListener{
                itemLongClickListener.invoke(it,position)
            }
        }
    }
}

