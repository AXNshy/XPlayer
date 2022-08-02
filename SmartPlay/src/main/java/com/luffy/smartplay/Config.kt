package com.luffy.smartplay

/**
 * Created by axnshy on 16/5/15.
 */
interface Config {
    companion object {
        const val BROADCAST_NAME = "com.axnshy.music.broadcast"
        const val SERVICE_NAME = "com.axnshy.music.service.MediaService"
        const val BROADCAST_QUERY_COMPLETE_NAME = "com.axnshy.music.querycomplete.broadcast"
        const val BROADCAST_CHANGEBG = "com.axnshy.music.changebg"
        const val BROADCAST_SHAKE = "com.axnshy.music.shake"
        const val MUSIC_LIST_ALL = "com.axnshy.player.action.allmusic"
        const val MUSIC_LIST_FAVORITE = "com.axnshy.player.action.favoritemusic"
        const val MUSIC_LIST_DOWNLOAD = "com.axnshy.player.action.download"
        const val MUSIC_LIST_RECENT = "com.axnshy.player.action.recentplay"
        const val PLAYERACTIVITY_UPDATE = "com.axnshy.action.PlayerActivityReceiver"
        const val PLAYERSERVICE_UPDATE = "com.axnshy.action.PlayerServiceReceiver"
        const val MUSIC_UPDATE = "com.axnshy.action.MusicReceiver"

        //com.ken.android.SmartPlay.
        const val ServiceAbsName = "RadioPlayerService"
        const val URL = "pathOfMusic"
        const val ID = "resourceId"
        const val USER_PREFER = "userPrefer"
        const val SERVICE_UPDATE = "service_update"
        const val CIRCLE = "circle"
        const val CIRCLE_DE = 1
        const val PREVIOUS = "previous"
        const val PREVIOUS_DE = 1
        const val PLAY = "play"
        const val PLAY_DE = 1
        const val NEXT = "next"
        const val NEXT_DE = 1
        const val LOGIN_SUCCESS = true
        const val LOGIN = 1
        const val WEB_SERVER_LOGIN = "http://192.168.1.105:8080/CloudMusic/user/loginUser?"
        const val WEB_SERVER_REGISTER = "http://192.168.1.105:8080/CloudMusic/user/registerUser?"
        const val WEB_SERVER_ADDMUSIC_COLLECT =
            "http://192.168.1.105:8080/CloudMusic/user/addCollectMusic?"

        /*
* 1: MUSIC_INFO
* */
        const val MSG = -1

        // 播放状态
        const val NOFILE = -1 // 无音乐文件
        const val MPS_INVALID = 0 // 当前音乐文件无效
        const val MPS_PREPARE = 1 // 准备就绪
        const val MPS_PLAYING = 2 // 播放中
        const val MPS_PAUSE = 3 // 暂停

        // 播放模式
        const val MPM_LIST_LOOP_PLAY = 0 // 列表循环
        const val MPM_ORDER_PLAY = 1 // 顺序播放
        const val MPM_RANDOM_PLAY = 2 // 随机播放
        const val MPM_SINGLE_LOOP_PLAY = 3 // 单曲循环

        //歌手和专辑列表点击都会进入MyMusic 此时要传递参数表明是从哪里进入的
        const val LIST = "list"
        const val START_FROM_LOCAL = 0
        const val START_FROM_FAVORITE = 1
        const val START_FROM_EAST = 2
        const val START_FROM_ANIME = 3
        const val totalNum = 0
    }
}