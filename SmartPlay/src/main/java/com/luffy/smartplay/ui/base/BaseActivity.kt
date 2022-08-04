package com.luffy.smartplay.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

/**
 * Created by axnshy on 16/8/9.
 */
abstract class BaseActivity<T : ViewBinding,M:ViewModel> : BasePlayerActivity() {

    lateinit var viewBinding : T
    abstract val viewModel : M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindViewBinding().apply {
            viewBinding = this
        }.root)
    }

    abstract fun bindViewBinding() : T
}