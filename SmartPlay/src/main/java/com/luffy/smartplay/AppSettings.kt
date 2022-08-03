package com.luffy.smartplay

import android.content.*
import com.luffy.smartplay.db.bean.User
import com.luffy.smartplay.ui.MyApp
import java.lang.RuntimeException
import kotlin.reflect.KProperty

/**
 * Created by axnshy on 16/5/22.
 */
object AppSettings {
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val REPEATTAG = "repeatTag"
    const val SHUFFLETAG = "shuffleTag"
    var NAME :String = MyApp.context.packageName
    private var sp: SharedPreferences = MyApp.context.getSharedPreferences(NAME,Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sp.edit()

    //0:blue   1:red
    var APPTheme = 0
    var APPTheme_BLUE = 0
    var BLUE = "#4374f5"
    var RED = "#FA6E86"
    var APPTheme_RED = 1
    const val APP_COLOR = "app_color"
    const val FIRST = "first_enter"

    suspend fun getCurrentUser(context: Context): User? {
        return User().apply {
            username = this@AppSettings.username
            password = this@AppSettings.password
        }
    }

    fun unRegisterUser() {
        username = ""
        password = ""
    }

    var username by SettingDelegate(USERNAME,"")
    var password by SettingDelegate(PASSWORD,"")

    var firstEnter by SettingDelegate(FIRST,false)

    class SettingDelegate<T>(val key:String,var def:T){
        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
            return sp.get(key,def)
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            editor.set(key,def)
        }
    }

    fun <T> SharedPreferences.get(key: String,def:T) : T{
        when(def){
            is Int-> return getInt(key,def as Int) as T
            is Long-> return getLong(key,def as Long) as T
            is Boolean-> return getBoolean(key,def as Boolean) as T
            is Float-> return getFloat(key,def as Float) as T
            is String-> return getString(key,def as String) as T
            else -> {
                throw RuntimeException("def class type not support")
            }
        }
    }

    fun <T> SharedPreferences.Editor.set(key: String,def:T){
        when(def){
            is Int->  putInt(key,def as Int)
            is Long->  putLong(key,def as Long)
            is Boolean->  putBoolean(key,def as Boolean)
            is Float->  putFloat(key,def as Float)
            is String->  putString(key,def as String)
            else -> {
                throw RuntimeException("def class type not support")
            }
        }
    }


}