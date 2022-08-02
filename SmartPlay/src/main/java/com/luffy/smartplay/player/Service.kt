package com.luffy.smartplay.player

import android.app.ActivityManager
import android.content.*

/**
 * Created by axnshy on 16/7/30.
 */
object Service {
    /*
    *判断service有没有运行
    * */
    fun isMyServiceRunning(context: Context?, serviceClass: Class<*>): Boolean {
        val manager = context!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}