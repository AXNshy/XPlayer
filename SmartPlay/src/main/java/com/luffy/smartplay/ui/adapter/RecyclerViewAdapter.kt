package com.luffy.smartplay.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.MusicListViewBinding
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.ui.base.BaseBindingAdapter
import com.luffy.smartplay.ui.base.BindingViewHolder

/**
 * Created by axnshy on 16/8/9.
 */
class RecyclerViewAdapter(val list: List<AlbumData> = mutableListOf()) : BaseBindingAdapter<MusicListViewBinding>() { //@ContentView(R.layout.music_list_view)

    override fun onBindViewHolder(holder: BindingViewHolder<MusicListViewBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.holder.apply {
            tvMusiclistName.text = list[position].albumName
            tvMusiclistCount.text = list[position].count.toString()

            layoutListAvatar.background = if (list[position].albumAvatar != null) {
                Drawable.createFromPath(list[position].albumAvatar)
            } else holder.itemView.context.resources.getDrawable(R.drawable.h1)
        }

    }

    override fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): MusicListViewBinding {
        return MusicListViewBinding.inflate(layoutInflater,parent,false)
    }

    override fun getItemCount(): Int {
        return list.size
    }

}