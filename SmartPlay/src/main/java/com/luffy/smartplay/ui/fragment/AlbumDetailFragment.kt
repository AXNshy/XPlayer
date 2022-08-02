package com.luffy.smartplay.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.databinding.ArtworkBinding
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.ArtWorkViewModel

/**
 * Created by axnshy on 16/7/31.
 */
class AlbumDetailFragment : BaseFragment<ArtworkBinding, ArtWorkViewModel>() {
    var mMusicInfo: MusicData? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =  ArtworkBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mMusicInfo = arguments?.getParcelable("MusicInfo")
    }
}