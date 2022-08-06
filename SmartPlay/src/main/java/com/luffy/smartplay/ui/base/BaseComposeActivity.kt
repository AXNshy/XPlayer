package com.luffy.smartplay.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseComposeActivity <M: ViewModel> : BasePlayerActivity() {

    abstract val viewModel : M
}