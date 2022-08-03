package com.luffy.smartplay.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.AppSettings
import com.luffy.smartplay.db.repo.MusicRepository
import kotlinx.coroutines.coroutineScope


/**
 * Created by axnshy on 16/8/5.
 */
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        if (!AppSettings.firstEnter) {
            AppSettings.firstEnter = true
        }
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context
    }
}