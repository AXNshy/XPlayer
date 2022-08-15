package com.luffy.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DataSource

class MusicPlayer(var context: Context) {

    var exoPlayer: ExoPlayer? = null
    var dataSourceFactory: DataSource.Factory
    lateinit var mediaSourceFactory: MediaSource.Factory
    var trackSelector: TrackSelector? = null

    private val mediaItems: MutableList<MediaItem> = mutableListOf()

    private var trackSelectionParameters: DefaultTrackSelector.Parameters? = null

    init {
        dataSourceFactory = Utils.getDataSourceFactory(context)!!
        trackSelectionParameters = DefaultTrackSelector.ParametersBuilder(context).build()
    }

    fun setMediaData(path: String) {
        mediaItems.clear()
        exoPlayer?.addMediaItem(MediaItem.fromUri(path))
        if (exoPlayer?.isPlaying == true) {
            exoPlayer?.seekToNextMediaItem()
        } else {
            exoPlayer?.prepare()
        }
    }

    fun initializePlayer() {
        trackSelector = DefaultTrackSelector(context)
        mediaSourceFactory = createMediaSourceFactory()
        exoPlayer = ExoPlayer.Builder(context)
            .setRenderersFactory(Utils.buildRenderersFactory(context))
            .setMediaSourceFactory(mediaSourceFactory)
            .setTrackSelector(trackSelector!!)
            .build()
//        exoPlayer = SimpleExoPlayer.Builder(context).build()
        exoPlayer?.apply {
            playWhenReady = true
            addListener(PlayerListener())
            setAudioAttributes(com.google.android.exoplayer2.audio.AudioAttributes.DEFAULT, true)
            trackSelectionParameters = this@MusicPlayer.trackSelectionParameters!!
//            setMediaItems(mediaItems)

        }
    }

    class PlayerListener : Player.Listener {

    }

    private fun createMediaSourceFactory(): MediaSource.Factory {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
    }

    private fun createExtractFactory(): ExtractorsFactory {
        return DefaultExtractorsFactory()
    }


    fun getCurrentMediaItem(): MediaItem? {
        return exoPlayer?.currentMediaItem
    }
}