package com.luffy.smartplay.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding,M: ViewModel> : Fragment() {
    abstract val viewModel: M
    lateinit var viewBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindViewBinding(inflater, container).apply {
            viewBinding = this
        }.root
    }

    abstract fun bindViewBinding(inflater: LayoutInflater,
                                      container: ViewGroup?) : T
}