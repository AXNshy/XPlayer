package com.luffy.smartplay.ui

import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.databinding.LoginBinding
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.viewmodel.LoginViewModel

/**
 * Created by axnshy on 16/5/25.
 */
class LoginActivity : BaseActivity<LoginBinding,LoginViewModel>(){


    override val viewModel: LoginViewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }

    override fun bindViewBinding(): LoginBinding {
        return LoginBinding.inflate(layoutInflater)
    }
}