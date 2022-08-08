package com.luffy.smartplay.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract  class  BaseViewModel<T : UIState>:ViewModel() {
    val TAG = javaClass.simpleName
    protected val _state: MutableStateFlow<T> = MutableStateFlow(init())
    val state: StateFlow<T> by lazy { _state.asStateFlow() }
    val uiState by mutableStateOf(_state.value)

    abstract fun init() :T
}

open class UIState