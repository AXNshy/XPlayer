package com.luffy.smartplay.ui

import android.os.Bundle
import com.luffy.smartplay.R
import com.luffy.smartplay.db.dao.MusicDao
import androidx.appcompat.app.ActionBarDrawerToggle
import com.luffy.smartplay.player.PlayerService
import android.content.ComponentName
import android.content.Intent
import com.luffy.smartplay.ui.fragment.HomeFragment
import androidx.core.view.GravityCompat
import android.animation.ObjectAnimator
import com.luffy.smartplay.ui.fragment.AlbunFragment
import android.util.Log
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.player.Service
import com.luffy.smartplay.databinding.ActivityLaunchBinding
import com.luffy.smartplay.ui.base.BaseActivity
import com.luffy.smartplay.ui.viewmodel.HomeViewModel
import com.luffy.smartplay.utils.AccountUtils

class HomeActivity : BaseActivity<ActivityLaunchBinding, HomeViewModel>(), Toolbar.OnMenuItemClickListener,
    AlbunFragment.OnItemClickListener, View.OnClickListener {


    private var mToolbar: Toolbar? = null
    private var PlayerBarToken = false

    //绑定service与activity
    //绑定service与activity
    private var mService: PlayerService? = null

    private lateinit var mDrawerToggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!AccountUtils.isUserExit(this)) {
            val intent = Intent(this@HomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        setContentView(viewBinding.root)
        initView()
        initEvent()
        launchService()
    }

    private fun initView() {
        PlayerBarToken = true
        viewBinding.apply{
            mToolbar!!.title = ""
            mToolbar!!.subtitle = ""
            setSupportActionBar(mToolbar)
            mDrawerToggle = ActionBarDrawerToggle(
                this@HomeActivity,
                drawerlayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close
            )
            drawerlayout.addDrawerListener(mDrawerToggle)
            val home = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.id_home_container, home).commit()
        }

        //initDrawer();
    }

    private fun initEvent() {
        viewBinding.controller.apply {
            ivMediaPlay.setOnClickListener(this@HomeActivity)
            ivMediaPrevious.setOnClickListener(this@HomeActivity)
            ivMediaNext.setOnClickListener(this@HomeActivity)
            ivMediaRepeat.setOnClickListener(this@HomeActivity)
            ivMediaShuffle.setOnClickListener(this@HomeActivity)
        }
    }

    /*
    * 扫描本地音乐文件
    * */
    private fun scanLocal() {
        MusicDao.Companion.scanMusic(this)
    }

    /*
    * 启动Service
    * */
    private fun launchService() {
        startService(Intent(this@HomeActivity, PlayerService::class.java))
        /* new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHelper.getInstance(Launch.this);
            }
        }).start();*/
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        mDrawerToggle!!.syncState() // 这个必须要，没有的话进去的默认是个箭头。。正常应该是三横杠的
        super.onPostCreate(savedInstanceState)
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.it_menu_scan -> {
                MusicDao.Companion.getAllMusic(this)
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

    override fun updateToolbar(string: String?) {}
    override fun onClick(v: View) {
        //判断Service是否存在
        if (!Service.isMyServiceRunning(this, PlayerService::class.java)) {
            val serviceintent = Intent(this, PlayerService::class.java)
            startService(serviceintent)
            Log.e("TAGS", "MediaPlayerService does not existed")
        } else {
            Log.e("TAGS", "MediaPlayerService existed")
        }
        when (v.id) {
            R.id.iv_media_repeat -> if (mService!!.repeatTag == 0) {
                viewBinding.controller.ivMediaRepeat.setBackgroundResource(R.drawable.basecopy)
                mService!!.repeatTag = 1
            } else {
                viewBinding.controller.ivMediaRepeat.setBackgroundResource(R.drawable.base)
                mService!!.repeatTag = 0
            }
            R.id.iv_media_shuffle -> if (mService!!.shuffleTag == 0) {
                viewBinding.controller.ivMediaShuffle.setBackgroundResource(R.drawable.basecopy)
                mService!!.shuffleTag = 1
            } else {
                viewBinding.controller.ivMediaShuffle.setBackgroundResource(R.drawable.base)
                mService!!.shuffleTag = 0
            }
            R.id.iv_media_previous -> {
            }
            R.id.iv_media_next -> {
            }
            R.id.iv_media_play -> {
            }
            R.id.iv_back_list_fragment -> onBackPressed()
        }
        updateUI()
    }

    private fun updateUI() {
        //PlayerState代表播放状态
        if (PlayerBarToken == true && PlayerService.Companion.PlayerState == PlayerService.Companion.MediaPlayer_PAUSE) {
            ObjectAnimator.ofFloat(viewBinding.controller, "translationY", 0f, 180f).setDuration(0).start()
            PlayerBarToken = false
        }
    }

    private fun initDrawer() {
    }

    override val viewBinding: ActivityLaunchBinding by lazy{ ActivityLaunchBinding.inflate(layoutInflater) }
    override val viewModel: HomeViewModel by lazy{ ViewModelProvider(this)[HomeViewModel::class.java]}
}