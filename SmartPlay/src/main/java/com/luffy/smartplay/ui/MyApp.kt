package com.luffy.smartplay.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.MySharedPre


/**
 * Created by axnshy on 16/8/5.
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        if (MySharedPre.getOpenFlag(this) == 0) {
            scanLocal()
            MySharedPre.setOpenFLAG(this)
        }
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
    }

    /*
   * 扫描本地音乐文件
   * */
    private fun scanLocal() {
        Thread { MusicDao.scanMusic(getApplicationContext()) }.start()
    }
}