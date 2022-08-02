package com.luffy.smartplay.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Created by axnshy on 16/8/9.
 */
abstract class BaseActivity<T : ViewBinding,M:ViewModel> : AppCompatActivity() {

    abstract val viewBinding : T
    abstract val viewModel : M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}