package com.luffy.smartplay.ui.viewmodel

import com.luffy.smartplay.db.bean.AlbumData
import kotlinx.coroutines.flow.MutableStateFlow

class MainFragmentViewModel: BaseViewModel<MainFragmentViewModel.MainFragmentState>() {



    class MainFragmentState(val albumList:List<AlbumData> = mutableListOf()) : State() {

    }

    override val _state: MutableStateFlow<MainFragmentState> = MutableStateFlow(MainFragmentState())
        get
}