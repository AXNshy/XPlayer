package com.luffy.smartplay.ui.adapter

import com.luffy.smartplay.R
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.BaseAdapter
import android.content.*
import android.view.View
import com.luffy.smartplay.databinding.DrawerListviewItemBinding
import com.luffy.smartplay.ui.base.BaseBindingAdapter
import com.luffy.smartplay.ui.base.BindingViewHolder
import java.util.ArrayList

/**
 * Created by axnshy on 16/5/26.
 */
class DrawerListAdapter(val items: List<String> = mutableListOf()) : BaseBindingAdapter<DrawerListviewItemBinding>() {

    override fun onBindViewHolder(
        holder: BindingViewHolder<DrawerListviewItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        holder.holder.drawerlistTitle.text = items[position]
    }

    override fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): DrawerListviewItemBinding {
        return DrawerListviewItemBinding.inflate(layoutInflater,parent,false)
    }

    override fun getItemCount(): Int {return items.size
    }
}