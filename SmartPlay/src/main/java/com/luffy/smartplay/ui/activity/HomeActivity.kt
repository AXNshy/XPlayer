package com.luffy.smartplay.ui.activity

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.ActivityLaunchBinding
import com.luffy.smartplay.db.repo.MusicRepository
import com.luffy.smartplay.player.PlayerService
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.fragment.MainFragment
import com.luffy.smartplay.ui.viewmodel.HomeViewModel
import com.luffy.smartplay.ui.widget.PlaybackControllerCallback
import com.luffy.smartplay.ui.widget.PlaybackControllerUI
import com.luffy.smartplay.utils.AccountUtils
import com.luffy.smartplay.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity<ActivityLaunchBinding, HomeViewModel>(),
    Toolbar.OnMenuItemClickListener {


    private val permissions: Array<String> = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private var PlayerBarToken = false

    companion object {
        const val TAG = "HomeActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        for (perm in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permissions[0]
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                initView()
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1)
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (perm in permissions.indices) {
                if (grantResults[perm] == PackageManager.PERMISSION_DENIED) {
                    Logger.d(TAG, "permission query fail")
                    finish()
                    return
                }
            }
            initView()
        }
    }


    private fun initView() {
        if (!AccountUtils.isUserExit(this)) {
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
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
            supportFragmentManager.beginTransaction()
                .replace(R.id.id_home_container, MainFragment()).commit()
            viewBinding.composeContainer.setContent {
                PlaybackControllerUI(object : PlaybackControllerCallback {
                    override fun onRepeat(value: Int) {
                        Logger.d(TAG, "onRepeat mode: $value")
                        lifecycleScope.launch {
                            viewModel.switchRepeatMode()
                        }
                    }

                    override fun onPrevious() {
                        Logger.d(TAG, "onPrevious")
                    }

                    override fun onPlayOrPause() {
                        Logger.d(TAG, "onPlayOrPause")
                        playOrPause()
                    }

                    override fun onNext() {
                        Logger.d(TAG, "onNext")
                    }

                    override fun onShuffle(value: Int) {
                        Logger.d(TAG, "onShuffle")
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
        lifecycleScope.launch(Dispatchers.IO) {
            MusicRepository.scanMusic()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_menu_scan -> {
                lifecycleScope.launch(Dispatchers.IO) {
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