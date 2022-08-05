package com.luffy.smartplay.utils

import android.util.Log

object Logger {
    private var TAG_PREFIX : String = "SmartPlay"

    private fun tag(tag: String) :String{
        return "$TAG_PREFIX-$tag"
    }

    fun d(tag:String,msg:String){
        Log.d(tag(tag),msg)
    }
    fun i(tag:String,msg:String){
        Log.i(tag(tag),msg)
    }
    fun w(tag:String,msg:String){
        Log.w(tag(tag),msg)
    }
    fun e(tag:String,msg:String,throws: Throwable? = null){
        Log.e(tag(tag),msg,throws)
    }
    fun v(tag:String,msg:String){
        Log.v(tag(tag),msg)
    }
}