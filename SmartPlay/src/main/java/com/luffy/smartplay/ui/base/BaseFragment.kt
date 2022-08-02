package com.luffy.smartplay.ui.base

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding,M: ViewModel> : Fragment() {
    lateinit var viewModel: M
    lateinit var viewBinding: T
}