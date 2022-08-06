package com.luffy.smartplay.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.databinding.ArtworkBinding
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.ArtWorkViewModel
import com.luffy.smartplay.ui.viewmodel.MainFragmentViewModel

/**
 * Created by axnshy on 16/7/31.
 */
class AlbumDetailFragment : BaseFragment<ArtworkBinding, ArtWorkViewModel>() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun bindViewBinding(inflater: LayoutInflater, container: ViewGroup?): ArtworkBinding {
        return ArtworkBinding.inflate(inflater,container,false)
    }

    override val viewModel: ArtWorkViewModel by lazy{ViewModelProvider(this)[ArtWorkViewModel::class.java]}

}