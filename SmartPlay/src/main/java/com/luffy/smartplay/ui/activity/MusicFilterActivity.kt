package com.luffy.smartplay.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Tab
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.ActivityMusicFilterBinding
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.fragment.MusicFilterFragment
import com.luffy.smartplay.ui.viewmodel.MusicFilterViewModel
import java.lang.RuntimeException

class MusicFilterActivity : BaseActivity<ActivityMusicFilterBinding, MusicFilterViewModel>() {
    override val viewModel: MusicFilterViewModel by lazy { ViewModelProvider(this)[MusicFilterViewModel::class.java] }

    val tabItems = mutableListOf<TabLayout.Tab>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.apply {
            setSupportActionBar(toolbar)
            toolbar.collapseIcon = getDrawable(R.drawable.back_48px)
            tabItems.add(tablayout.newTab().apply {
                text = getString(R.string.music_filter_music)
            })
            tabItems.add(tablayout.newTab().apply {
                text = getString(R.string.music_filter_singer)
            })
            tabItems.add(tablayout.newTab().apply {
                text = getString(R.string.music_filter_album)
            })
            tabItems.add(
                tablayout.newTab().apply {
                    text = getString(R.string.music_filter_folder)
                })
            for (tab in tabItems) {
                tablayout.addTab(tab)
            }
            tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewpager.currentItem = tab?.position ?: 0
                    title = tab?.text
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })

            viewpager.adapter = fragmentPageAdapter
        }
    }

    val fragmentPageAdapter = object : FragmentStateAdapter(this) {

        override fun getItemCount(): Int {
            return tabItems.size
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    MusicFilterFragment()
                }
                1 -> {
                    MusicFilterFragment()
                }
                2 -> {
                    MusicFilterFragment()
                }
                3 -> {
                    MusicFilterFragment()
                }
                else -> {
                    throw RuntimeException("getItem position not exist")
                }
            }
        }

    }

    override fun bindViewBinding(): ActivityMusicFilterBinding {
        return ActivityMusicFilterBinding.inflate(layoutInflater)
    }
}