package com.luffy.smartplay.utils

import android.net.ConnectivityManager
import android.content.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by axnshy on 16/5/25.
 */
object HttpUtils {
    /*
    * 判断网络是否正常
    * */
    fun isServerConnection(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return if (info == null) {
            println("Null")
            false
        } else {
            true
        }
    }

    /*
    * 使用get的方式访问网络并获取到Json数据
    *
    * */
    fun doGet(uriString: String?): String {
        val sb = StringBuffer()
        val result: String
        try {
            val url = URL(uriString)
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 10 * 1000
            conn.readTimeout = 10 * 1000
            val `is` = conn.inputStream
            val br = BufferedReader(InputStreamReader(`is`))
            val line = br.readLine()
            println("line:$line")
            if (line != null) {
                sb.append(line)
            }
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        result = sb.toString()
        /*if(sb.toString().contains("weather_callback")){
            result=sb.toString().substring(sb.toString().indexOf("(")+1,sb.toString().indexOf(")"));
        }*/return result
    }
}