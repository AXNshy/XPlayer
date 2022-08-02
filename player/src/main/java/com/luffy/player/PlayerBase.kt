package com.luffy.player

import android.net.Uri

interface PlayerBase {
    fun setMediaSource(uri : Uri)
    fun prepare()
    fun start()
    fun pause()
    fun stop()
}