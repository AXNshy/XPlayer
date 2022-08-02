package com.luffy.smartplay

import android.content.*

/**
 * Created by axnshy on 16/5/22.
 */
object MySharedPre {
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val REPEATTAG = "repeatTag"
    const val SHUFFLETAG = "shuffleTag"
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    //0:blue   1:red
    var APPTheme = 0
    var APPTheme_BLUE = 0
    var BLUE = "#4374f5"
    var RED = "#FA6E86"
    var APPTheme_RED = 1
    const val APP_COLOR = "app_color"
    const val FIRST = "first_enter"
    fun getSharedPreferences(context: Context): SharedPreferences? {
        sharedPreferences =
            context.getSharedPreferences(Config.Companion.USER_PREFER, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        return sharedPreferences
    }

    fun setAppTheme(context: Context, color: String?) {
        getSharedPreferences(context)
        editor!!.putString(APP_COLOR, color)
        editor!!.commit()
        if (APPTheme == APPTheme_BLUE) {
            APPTheme = APPTheme_RED
        } else {
            APPTheme = APPTheme_BLUE
        }
    }

    fun getAPPTheme(context: Context): String? {
        getSharedPreferences(context)
        val color = sharedPreferences!!.getString(APP_COLOR, "NO")
        if (color === "NO") {
            if (color === "#FA6E86") {
                APPTheme = 1
            } else APPTheme = 0
        }
        return color
    }

    fun getCurrentUser(context: Context): User? {
        getSharedPreferences(context)
        val username = sharedPreferences!!.getString(USERNAME, null)
        val password = sharedPreferences!!.getString(PASSWORD, null)
        if (username != null && password != null) {
            val user = User(username, password)
            User.Companion.mUser = user
            return user
        }
        return null
    }

    fun updateCurrentUser(context: Context, username: String?, password: String?) {
        getSharedPreferences(context)
        val editor = sharedPreferences!!.edit()
        editor.putString(USERNAME, username)
        editor.putString(PASSWORD, password)
        editor.commit()
    }

    fun unRegisterUser(context: Context) {
        getSharedPreferences(context)
        val editor = sharedPreferences!!.edit()
        editor.remove(USERNAME)
        editor.remove(PASSWORD)
        editor.commit()
    }

    fun setRepeatAndShuffleTag(context: Context, repeatTag: Int, shuffleTag: Int) {
        getSharedPreferences(context)
        //        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor!!.putInt(REPEATTAG, repeatTag)
        editor!!.putInt(SHUFFLETAG, shuffleTag)
        editor!!.commit()
    }

    fun setOpenFLAG(context: Context) {
        getSharedPreferences(context)
        //        SharedPreferences
        editor!!.putInt(FIRST, 1)
        editor!!.commit()
    }

    fun getOpenFlag(context: Context): Int {
        getSharedPreferences(context)
        return sharedPreferences!!.getInt(FIRST, 0)
    }
}