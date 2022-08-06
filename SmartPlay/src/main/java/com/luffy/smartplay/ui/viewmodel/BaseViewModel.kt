package com.luffy.smartplay.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract  class  BaseViewModel<T : UIState>:ViewModel() {
    protected val _state:MutableStateFlow<T> = MutableStateFlow(init())
    val state: StateFlow<T> by lazy { _state.asStateFlow() }


    abstract fun init() :T
}

open class UIState{

}