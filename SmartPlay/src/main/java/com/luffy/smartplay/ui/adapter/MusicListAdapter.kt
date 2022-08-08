package com.luffy.smartplay.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luffy.smartplay.databinding.CustomListItemBinding
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.ui.base.BaseBindingAdapter
import com.luffy.smartplay.ui.base.BindingViewHolder

/**
 * Created by axnshy on 16/5/21.
 */
class MusicListAdapter(val list: List<MusicData> = mutableListOf()) :
    BaseBindingAdapter<CustomListItemBinding>() {
    var menuListener: OnItemMenuClickListener? = null

    override fun onBindViewHolder(holder: BindingViewHolder<CustomListItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.holder.apply {
            idMusicName.text = list[position].title
                idMusicArtist.setText(list[position].artist)
            ivPlayIndicator.visibility = View.INVISIBLE
            ivItemMenu.setOnClickListener { menuListener?.showItemMenu(list[position]) }
        }

    }

    interface OnItemMenuClickListener {
        fun showItemMenu(music: MusicData)
    }

    override fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): CustomListItemBinding {
        return CustomListItemBinding.inflate(layoutInflater,parent,false)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}