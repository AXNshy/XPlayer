package com.luffy.smartplay.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.databinding.HomeBinding
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.HomeFragmentViewModel

/**
 * Created by axnshy on 16/4/18.
 */
class HomeFragment : BaseFragment<HomeBinding, HomeFragmentViewModel>(), View.OnClickListener {
    //private ExpandableListView mExpandableListView;
    private val topList: List<String>? = null
    private val defaultChildList: List<String>? = null
    private val map: Map<String, String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        viewBinding = HomeBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    private fun initView() {

    }

    override fun onClick(v: View) {
        //判断Service是否存在
    }

}
