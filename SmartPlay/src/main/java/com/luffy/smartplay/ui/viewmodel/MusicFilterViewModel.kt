package com.luffy.smartplay.ui.viewmodel

import com.luffy.smartplay.db.bean.MusicData

class MusicFilterViewModel: BaseViewModel<MusicFilterState>() {
    override fun init(): MusicFilterState {
       return MusicFilterState()
    }
}


data class MusicFilterState(val data:List<MusicData> = mutableListOf()):UIState()