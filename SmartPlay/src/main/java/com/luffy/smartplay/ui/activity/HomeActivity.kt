package com.luffy.smartplay.ui.activity

import android.os.Bundle
import com.luffy.smartplay.R
import com.luffy.smartplay.player.PlayerService
import android.content.ComponentName
import android.content.Intent
import com.luffy.smartplay.ui.fragment.MainFragment
import androidx.core.view.GravityCompat
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.luffy.smartplay.databinding.ActivityLaunchBinding
import com.luffy.smartplay.db.repo.MusicRepository
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.viewmodel.HomeViewModel
import com.luffy.smartplay.ui.widget.PlaybackControllerCallback
import com.luffy.smartplay.ui.widget.PlaybackControllerUI
import com.luffy.smartplay.utils.AccountUtils
import com.luffy.smartplay.utils.Logger
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity<ActivityLaunchBinding, HomeViewModel>(),
    Toolbar.OnMenuItemClickListener{


    private var PlayerBarToken = false

    companion object{
        const val TAG = "HomeActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!AccountUtils.isUserExit(this)) {
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        setContentView(viewBinding.root)
        initView()
    }

    private fun initView() {
        PlayerBarToken = true
        viewBinding.apply {
//            mToolbar!!.title = ""
//            mToolbar!!.subtitle = ""
//            setSupportActionBar(mToolbar)
//            mDrawerToggle = ActionBarDrawerToggle(
//                this@HomeActivity,
//                drawerlayout,
//                mToolbar,
//                R.string.drawer_open,
//                R.string.drawer_close
//            )
//            drawerlayout.addDrawerListener(mDrawerToggle)
            val home = MainFragment()
            supportFragmentManager.beginTransaction().replace(R.id.id_home_container, home).commit()
            viewBinding.composeContainer.setContent {
                PlaybackControllerUI(object : PlaybackControllerCallback{
                    override fun onRepeat(value: Int) {
                        Logger.d(TAG,"onRepeat mode: $value")
                        lifecycleScope.launch {
                            viewModel.switchRepeatMode()
                        }
                    }

                    override fun onPrevious() {
                        Logger.d(TAG,"onPrevious")
                    }

                    override fun onPlayOrPause() {
                        Logger.d(TAG,"onPlayOrPause")
                        playOrPause()
                    }

                    override fun onNext() {
                        Logger.d(TAG,"onNext")
                    }

                    override fun onShuffle(value: Int) {
                        Logger.d(TAG,"onShuffle")
                        lifecycleScope.launch {
                            viewModel.switchShuffleMode()
                        }
                    }

                })
            }
        }

        //initDrawer();
    }

    /*
    * 扫描本地音乐文件
    * */
    private fun scanLocal() {
        lifecycleScope.launch {
            MusicRepository.scanMusic()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_menu_scan -> {
                lifecycleScope.launch {
                    MusicRepository.scanMusic()
                }
            }
        }
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        //        getMenuInflater().inflate(R.menu.drawer,navigationView.createContextMenu());
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> viewBinding.drawerlayout.openDrawer(GravityCompat.START)
            R.id.it_drawer_menu3 -> {
                val intent = Intent()
                intent.component =
                    ComponentName(this@HomeActivity, UserInfoShowActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUI() {
        //PlayerState代表播放状态
        if (PlayerBarToken == true && PlayerService.Companion.PlayerState == PlayerService.Companion.MediaPlayer_PAUSE) {

            PlayerBarToken = false
        }
    }

    private fun initDrawer() {
    }

    override val viewModel: HomeViewModel by lazy { ViewModelProvider(this)[HomeViewModel::class.java] }
    override fun bindViewBinding(): ActivityLaunchBinding {
        return ActivityLaunchBinding.inflate(layoutInflater)
    }
}