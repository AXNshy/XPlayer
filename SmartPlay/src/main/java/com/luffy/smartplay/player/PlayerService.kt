package com.luffy.smartplay.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.luffy.player.PlayerBase
import com.luffy.smartplay.Config
import com.luffy.smartplay.R
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.utils.Logger
import java.io.IOException
import java.util.*

/**
 * Created by axnshy on 16/4/19.
 */
class PlayerService : MediaBrowserServiceCompat(), Config, MediaPlayer.OnPreparedListener {
    var repeatTag = 0
    var shuffleTag = 0
    private var mList: ArrayList<MusicData?>? = null

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

    override fun onPrepared(mp: MediaPlayer) {}


    private val MY_MEDIA_ROOT_ID = "media_root_id"
    private val MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id"

    lateinit var mediaSession : MediaSessionCompat
    private lateinit var stateBuilder: PlaybackStateCompat.Builder

    override fun onCreate() {
        super.onCreate()
        // Create a MediaSessionCompat
        mediaSession = MediaSessionCompat(this, LOG_TAG).apply {

            // Enable callbacks from MediaButtons and TransportControls
            setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS
                    or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
            )

            // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
            stateBuilder = PlaybackStateCompat.Builder()
                .setActions(PlaybackStateCompat.ACTION_PLAY
                        or PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
            setPlaybackState(stateBuilder.build())

            // MySessionCallback() has methods that handle callbacks from a media controller
            setCallback(mCallback)

            // Set the session's token so that client activities can communicate with it.
            setSessionToken(sessionToken)
        }

        // Given a media session and its context (usually the component containing the session)
        // Create a NotificationCompat.Builder

        // Get the session's metadata
        val controller = mediaSession.controller
        val mediaMetadata = controller.metadata
        val description = mediaMetadata?.description

        val channelId = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(packageName,PlayerService.javaClass.name)
        }else {
            ""
        }

        val builder = NotificationCompat.Builder(this, channelId!!).apply {
            // Add the metadata for the currently playing track
            setContentTitle(description?.title?:"1111")
            setContentText(description?.subtitle?:"2222")
            setSubText(description?.description?:"33333")
            setLargeIcon(description?.iconBitmap)

            // Enable launching the player by clicking the notification
            setContentIntent(controller.sessionActivity)

            // Stop the service when the notification is swiped away
            setDeleteIntent(
                MediaButtonReceiver.buildMediaButtonPendingIntent(
                    this@PlayerService,
                    PlaybackStateCompat.ACTION_STOP
                )
            )

            // Make the transport controls visible on the lockscreen
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

            // Add an app icon and set its accent color
            // Be careful about the color
            setSmallIcon(R.drawable.appicon)
            color = ContextCompat.getColor(this@PlayerService, R.color.colorPrimary)

            // Add a pause button
            addAction(
                NotificationCompat.Action(
                    R.drawable.pause,
                    getString(R.string.pause),
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this@PlayerService,
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                    )
                )
            )

            // Take advantage of MediaStyle features
            setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setMediaSession(mediaSession.sessionToken)
                .setShowActionsInCompactView(0)

                // Add a cancel button
                .setShowCancelButton(true)
                .setCancelButtonIntent(
                    MediaButtonReceiver.buildMediaButtonPendingIntent(
                        this@PlayerService,
                        PlaybackStateCompat.ACTION_STOP
                    )
                )
            )
        }

        // Display the notification and place the service in the foreground
        startForeground(1, builder.build())
    }

    private val mCallback : MediaSessionCompat.Callback = object : MediaSessionCompat.Callback() {
        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            super.onCommand(command, extras, cb)
            Logger.d(LOG_TAG,"onCommand $command")
        }

        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            Logger.d(LOG_TAG,"onMediaButtonEvent $mediaButtonEvent")
            return super.onMediaButtonEvent(mediaButtonEvent)
        }

        override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
            super.onPrepareFromUri(uri, extras)
            Logger.d(LOG_TAG,"onPrepareFromUri $uri")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String? {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }
    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        // (Optional) Control the level of access for the specified package name.
        // You'll need to write your own logic to do this.
        return if (allowBrowsing(clientPackageName, clientUid)) {
            // Returns a root ID that clients can use with onLoadChildren() to retrieve
            // the content hierarchy.
            BrowserRoot(MY_MEDIA_ROOT_ID, null)
        } else {
            // Clients can connect, but this BrowserRoot is an empty hierachy
            // so onLoadChildren returns nothing. This disables the ability to browse for content.
            BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null)
        }
    }

    private fun allowBrowsing(clientPackageName: String, clientUid: Int): Boolean {
        return true
    }

    override fun onLoadChildren(
        parentMediaId: String,
        result: MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>
    ) {
        //  Browsing not allowed
        if (MY_EMPTY_MEDIA_ROOT_ID == parentMediaId) {
            result.sendResult(null)
            return
        }

        // Assume for example that the music catalog is already loaded/cached.

        val mediaItems = emptyList<MediaBrowserCompat.MediaItem>()

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID == parentMediaId) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems)
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
        try {
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
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
        return mediaPlayer
    }

    companion object {
        const val LOG_TAG = "SmartPlay"
        var PlayerState = 1
        var MediaPlayer_PLAY = 2
        var MediaPlayer_PAUSE = 1
    }
}