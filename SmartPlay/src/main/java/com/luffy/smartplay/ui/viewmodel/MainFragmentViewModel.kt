package com.luffy.smartplay.ui.viewmodel

import android.graphics.Bitmap
import com.luffy.smartplay.db.bean.AlbumData

class MainFragmentViewModel: BaseViewModel<MainFragmentViewModel.MainFragmentState>() {



    data class MainFragmentState(val albumList:List<AlbumData> = mutableListOf(),val favoriteBoxBg : Bitmap? = null,val favoriteMusicSize:Int=0) : UIState()

    override fun init(): MainFragmentState {
        return MainFragmentState()
    }

}