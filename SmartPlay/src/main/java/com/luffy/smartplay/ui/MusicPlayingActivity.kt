package com.luffy.smartplay.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.Nullable
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.MusicPlayBinding
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.fragment.AlbumDetailFragment
import com.luffy.smartplay.ui.viewmodel.PlayerViewModel
import java.util.*

/**
 * Created by axnshy on 16/7/29.
 */
class MusicPlayingActivity : BaseActivity<MusicPlayBinding,PlayerViewModel>(), Observer {

    private fun OnClicked(view: View) {
        //判断Service是否存在
        when (view.id) {
            R.id.iv_repeat_play -> {
                viewBinding.ivRepeatPlay.setBackgroundResource(R.drawable.basecopy)
//            } else {
//                viewBinding.ivRepeatPlay.setBackgroundResource(R.drawable.base)
            }
            R.id.iv_shuffle_play -> {
//
//                } if (mService.shuffleTag == 0) {
                viewBinding.ivShufflePlay.setBackgroundResource(R.drawable.basecopy)
//            } else {
//                viewBinding.ivShufflePlay.setBackgroundResource(R.drawable.base)
            }
            R.id.iv_previous_play -> {

            }
            R.id.iv_next_play -> {
            }
            R.id.iv_play -> {
            }
            R.id.iv_back -> onBackPressed()
        }
        updateUI()
    }


    private var fragmentArrayList: List<AlbumDetailFragment>? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
                setContentView(viewBinding.root)
        window.statusBarColor = resources.getColor(R.color.colorIndego)
        initView()
    }

    private fun initView() {
        fragmentArrayList = ArrayList<AlbumDetailFragment>()
    }

    override fun update(observable: Observable, o: Any) {
        updateUI()
    }

    private fun updateUI() {
    }

    override val viewBinding: MusicPlayBinding by lazy { MusicPlayBinding.inflate(layoutInflater) }
    override val viewModel: PlayerViewModel by lazy { ViewModelProvider(this)[PlayerViewModel::class.java] }
}