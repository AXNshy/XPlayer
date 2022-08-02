package com.luffy.smartplay.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BindingViewHolder<T:ViewBinding>(val holder:T) : RecyclerView.ViewHolder(holder.root)