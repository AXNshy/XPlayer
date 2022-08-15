package com.luffy.smartplay.ui.base

import android.content.ComponentName
import android.media.AudioManager
import android.media.MediaMetadata
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.player.PlaybackService
import com.luffy.smartplay.ui.widget.PlaybackControllerState
import com.luffy.smartplay.utils.Logger

abstract class BasePlayerActivity : AppCompatActivity() {
    companion object {
        const val TAG = "BasePlayerActivity"
    }

    private lateinit var mediaBrowser: MediaBrowserCompat
    private lateinit var mediaController: MediaControllerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create MediaBrowserServiceCompat
        mediaBrowser = MediaBrowserCompat(
            this,
            ComponentName(this, PlaybackService::class.java),
            connectionCallbacks,
            null // optional Bundle
        )
    }

    val playbackState: MutableState<PlaybackControllerState> =
        mutableStateOf(PlaybackControllerState())

    public override fun onStart() {
        super.onStart()
        mediaBrowser.connect()
    }

    public override fun onResume() {
        super.onResume()
        volumeControlStream = AudioManager.STREAM_MUSIC
    }

    public override fun onStop() {
        super.onStop()
        // (see "stay in sync with the MediaSession")
        MediaControllerCompat.getMediaController(this)?.unregisterCallback(controllerCallback)
        mediaBrowser.disconnect()
    }

    private val connectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {

            // Get the token for the MediaSession
            mediaBrowser.sessionToken.also { token ->

                // Create a MediaControllerCompat
                mediaController = MediaControllerCompat(
                    this@BasePlayerActivity, // Context
                    token
                )
                // Save the controller
                MediaControllerCompat.setMediaController(this@BasePlayerActivity, mediaController)
            }

            // Finish building the UI
            buildTransportControls()
        }

        override fun onConnectionSuspended() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
        }

        override fun onConnectionFailed() {
            // The Service has refused our connection
        }
    }

    private var controllerCallback = object : MediaControllerCompat.Callback() {

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            Logger.d(TAG, "onMetadataChanged ${metadata?.mediaMetadata}")
            playbackState.value = playbackState.value.copy(
                name = (metadata?.mediaMetadata as MediaMetadata).getString(MediaMetadata.METADATA_KEY_TITLE),
                singer = (metadata?.mediaMetadata as MediaMetadata).getString(MediaMetadata.METADATA_KEY_ARTIST)
            )
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
            Logger.d(TAG, "onPlaybackStateChanged ${state?.playbackState}")

        }
    }

    fun playOrPause() {
        val pbState = mediaController.playbackState?.state
        if (pbState == PlaybackStateCompat.STATE_PLAYING) {
            mediaController.transportControls.pause()
        } else {
            mediaController.transportControls.play()
        }
    }

    fun getCurrentMusicName(): String {
        return mediaController.metadata.getString("name")
    }


    fun getCurrentMusicSinger(): String {
        return mediaController.metadata.getString("singer")
    }

    fun setMusicData(data: MusicData) {
        mediaController.transportControls.playFromUri(data.data.toUri(), null)
    }

    fun buildTransportControls() {
        mediaController = MediaControllerCompat.getMediaController(this)
        // Grab the view for the play/pause button

        // Display the initial state
        val metadata = mediaController.metadata
        val pbState = mediaController.playbackState

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback)
    }

}