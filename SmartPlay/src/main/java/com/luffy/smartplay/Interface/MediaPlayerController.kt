package com.luffy.smartplay.Interface

import com.luffy.smartplay.db.bean.MusicData

/**
 * Created by axnshy on 16/8/9.
 */
interface MediaPlayerController {
    fun play(musicInfo: MusicData?)
    fun pause()
    fun previous()
    operator fun next()
    fun repeat()
    fun shuffle()
}