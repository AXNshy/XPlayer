package com.luffy.smartplay.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<T : State>:ViewModel() {
    abstract val _state:MutableStateFlow<T>
    val state: StateFlow<T> by lazy { _state.asStateFlow() }
}

open class State{

}