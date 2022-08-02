package com.luffy.smartplay.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.DialogCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.databinding.DialogCollectBinding
import com.luffy.smartplay.databinding.DialogListItemBinding
import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.repo.MusicRepository
import com.luffy.smartplay.ui.base.BaseBindingAdapter
import com.luffy.smartplay.ui.base.BindingViewHolder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by axnshy on 16/8/11.
 */
class CollectDialog(var data: List<AlbumData>, val music: MusicData,
                    context: Context,
                    themeResId: Int
) : Dialog(context, themeResId) {
    private var recycler: RecyclerView? = null
    private var mAdapter: Adapter? = null
    private lateinit var viewBinding : DialogCollectBinding
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(DialogCollectBinding.inflate(layoutInflater).apply {
            viewBinding = this
        }.root)

        mAdapter = Adapter(data)
        mAdapter?.eventListener = BaseBindingAdapter.EventListener().apply {
            itemClickListener = {
                    view,position->
                GlobalScope.launch {
                    MusicRepository.insertToAlbum(data[position].albumId,music)
                }
                dismiss()
            }
        }
        viewBinding.recyclerDialogList.adapter = mAdapter
        viewBinding.recyclerDialogList.layoutManager  = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }
}

internal class Adapter(val list: List<AlbumData>) :
    BaseBindingAdapter<DialogListItemBinding>() {

    override fun onBindViewHolder(holder: BindingViewHolder<DialogListItemBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.holder.apply {
            ivDialogListAvatar.setImageBitmap(BitmapFactory.decodeFile(list[position].backgroundPath))
            tvDialogListName.text = list[position].albumName
        }
    }


    override fun createViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): DialogListItemBinding {
        return DialogListItemBinding.inflate(layoutInflater,parent,false)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}