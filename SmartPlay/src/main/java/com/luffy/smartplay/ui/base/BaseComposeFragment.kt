package com.luffy.smartplay.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseComposeFragment <M: ViewModel> : Fragment() {

    companion object{
        val TAG : String = javaClass.simpleName
    }

    abstract val viewModel: M

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).also {
            onComposeViewCreate(it)
        }
    }



    abstract fun onComposeViewCreate(view:ComposeView)
}