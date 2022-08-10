package com.luffy.player

import android.media.browse.MediaBrowser

interface PlayerBase {
    fun setMediaItem(item: MediaBrowser.MediaItem)
    fun prepare()
    fun start()
    fun pause()
    fun stop()
    fun setPlaybackStateCallback()
}