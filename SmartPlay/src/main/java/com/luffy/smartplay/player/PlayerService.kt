package com.luffy.smartplay.player

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import androidx.annotation.Nullable
import com.luffy.player.PlayerBase
import com.luffy.smartplay.Config
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.db.bean.MusicData
import java.io.IOException
import java.util.*

/**
 * Created by axnshy on 16/4/19.
 */
class PlayerService : Service(), Config, MediaPlayer.OnPreparedListener {
    private var mObservable: MyObservable? = null

    var repeatTag = 0
    var shuffleTag = 0
    private var mList: ArrayList<MusicData?>? = null
    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> {
                    mList = msg.obj as ArrayList<MusicData?>
                    currentMusic = mList!![0]
                    Log.e("TAG", "List 以获取")
                    mPlayer = createMediaPlayer()
                        mPlayer?.setOnPreparedListener(this@PlayerService)
                    mPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                }
                0 -> {
                    Log.e("TAG", " --------------- currentMusic -------------$currentMusic")
                    mObservable!!.notifyChanged(currentMusic)
                }
            }
        }
    }

    /*
        * 当前播放资源的绝对路径
        * */
    private val uri: String? = null

    /*
    * 当前播放资源的Id
    *
    * */
    var currentMusic: MusicData? = null
    private var mPlayer: PlayerBase? = null

    //private Context context;
    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        Log.d(LOG_TAG, "onBind")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.d(LOG_TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onPrepared(mp: MediaPlayer) {}

    override fun onCreate() {
        super.onCreate()
        mObservable = MyObservable()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        //super.onStartCommand();
        //初始化media player
        initEvent()
        return START_STICKY
    }

    private fun initEvent() {
        //getList(PlayerService.this);
    }

    fun getList(context: Context?) {
        Thread {
            val message = handler.obtainMessage(1, MusicDao.Companion.getAllMusic(context))
            handler.sendMessage(message)
        }.start()
    }

    fun getMyList(): ArrayList<MusicData?>? {
        return mList
    }

    fun getPreviousMusic(): MusicData? {
        val location = getMyList()!!.indexOf(currentMusic)
        return if (location == 0) {
            getMyList()!![getMyList()!!.size - 1]
        } else getMyList()!![location - 1]
    }

    fun getNextMusic(): MusicData? {
        val location = getMyList()!!.indexOf(currentMusic)
        return if (location == getMyList()!!.size - 1) {
            getMyList()!![0]
        } else getMyList()!![location + 1]
    }

    fun getPlayerState(): Int {
        return if (PlayerState > 0) PlayerState else -1
    }

    fun setPlayerState(state: Int) {
        PlayerState = state
    }

    fun setPlayerList(list: ArrayList<MusicData?>?) {
        mList = list
        currentMusic = mList!![0]
    }

    fun play(music: MusicData?) {
        getPlayerState()
        try {
            mPlayer?.reset()
            mPlayer?.setDataSource(music?.data)
            mPlayer?.prepare()
            mPlayer?.start()
            currentMusic = music
            PlayerState = MediaPlayer_PLAY
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun pause() {
//        getPlayerInstance().pause()
        //handler.sendMessage(handler.obtainMessage(0, currentMusic));
    }

    private fun openPlayer(){

    }

    private fun initPlayer(player: MediaPlayer){
        player.reset()
        if (repeatTag == 0)  player.isLooping = false
        if (repeatTag == 1)  player.isLooping = true
    }

    private fun createMediaPlayer(): MediaPlayer {
        val mediaPlayer = MediaPlayer()
        mediaPlayer.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
            override fun onCompletion(mp: MediaPlayer) {
                try {
                    currentMusic = if (shuffleTag == 0) {
                        val music: MusicData? = mList!![mList!!.indexOf(currentMusic) + 1]
                        mp.reset()
                        mp.setDataSource(music?.data)
                        mp.prepare()
                        mp.start()
                        music
                    } else {
                        val double1 = Math.random()
                        val random = (double1 * mList!!.size).toInt()
                        val music: MusicData? = mList!![random]
                        mp.reset()
                        mp.setDataSource(music?.data)
                        mp.prepare()
                        mp.start()
                        music
                    }
                    PlayerState = MediaPlayer_PLAY
                    handler.sendMessage(handler.obtainMessage(0, currentMusic))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
        return mediaPlayer
    }

    /**
     * 添加观察者
     * @param observer
     */
    fun addObserver(observer: Observer?) {
        mObservable!!.addObserver(observer)
    }

    inner class MyObservable : Observable() {
        fun notifyChanged(`object`: Any?) {
            setChanged()
            this.notifyObservers()
        }
    }

    companion object {
        private const val TAG = "SmartPlay"
        var PlayerState = 1
        var MediaPlayer_PLAY = 2
        var MediaPlayer_PAUSE = 1
        const val LOG_TAG = "LOG_TAG"
    }
}