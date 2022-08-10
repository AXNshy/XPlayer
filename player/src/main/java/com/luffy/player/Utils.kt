package com.luffy.player

import android.content.Context
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.RenderersFactory
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider
import com.google.android.exoplayer2.offline.DownloadManager
import com.google.android.exoplayer2.ui.DownloadNotificationHelper
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.google.android.exoplayer2.upstream.cache.Cache
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

object Utils {
    private var dataSourceFactory: DataSource.Factory? = null
    private var httpDataSourceFactory: HttpDataSource.Factory? = null

    private var databaseProvider: DatabaseProvider? = null

    private var downloadDirectory: File? = null

    private var downloadCache: Cache? = null

    private var downloadManager: DownloadManager? = null

    private var downloadNotificationHelper: DownloadNotificationHelper? = null

    private const val DOWNLOAD_CONTENT_DIRECTORY = "downloads"

    fun buildRenderersFactory(
        context: Context
    ): RenderersFactory {
        val extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        return DefaultRenderersFactory(context.applicationContext)
            .setExtensionRendererMode(extensionRendererMode)
    }


    /** Returns a [DataSource.Factory].  */
    @Synchronized
    fun getDataSourceFactory(context: Context): DataSource.Factory? {
        var context = context
        if (dataSourceFactory == null) {
            context = context.applicationContext
            val upstreamFactory = DefaultDataSource.Factory(
                context,
                getHttpDataSourceFactory(context)!!
            )
            dataSourceFactory =
                buildReadOnlyCacheDataSource(
                    upstreamFactory,
                    getDownloadCache(context)!!
                )
        }
        return dataSourceFactory
    }


    @Synchronized
    private fun getDownloadCache(context: Context): Cache? {
        if (downloadCache == null) {
            val downloadContentDirectory: File = File(
                getDownloadDirectory(context),
                DOWNLOAD_CONTENT_DIRECTORY
            )
            downloadCache = SimpleCache(
                downloadContentDirectory,
                NoOpCacheEvictor(),
                getDatabaseProvider(context)!!
            )
        }
        return downloadCache
    }

    @Synchronized
    fun getHttpDataSourceFactory(context: Context): HttpDataSource.Factory? {
        if (httpDataSourceFactory == null) {
            // We don't want to use Cronet, or we failed to instantiate a CronetEngine.
            val cookieManager = CookieManager()
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER)
            CookieHandler.setDefault(cookieManager)
            httpDataSourceFactory =
                DefaultHttpDataSource.Factory()
        }
        return httpDataSourceFactory
    }

    @Synchronized
    private fun getDatabaseProvider(context: Context): DatabaseProvider? {
        if (databaseProvider == null) {
            databaseProvider =
                StandaloneDatabaseProvider(context)
        }
        return databaseProvider
    }

    @Synchronized
    private fun getDownloadDirectory(context: Context): File? {
        if (downloadDirectory == null) {
            downloadDirectory =
                context.getExternalFilesDir( /* type= */null)
            if (downloadDirectory == null) {
                downloadDirectory = context.filesDir
            }
        }
        return downloadDirectory
    }

    private fun buildReadOnlyCacheDataSource(
        upstreamFactory: DataSource.Factory, cache: Cache
    ): CacheDataSource.Factory? {
        return CacheDataSource.Factory()
            .setCache(cache)
            .setUpstreamDataSourceFactory(upstreamFactory)
            .setCacheWriteDataSinkFactory(null)
            .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)
    }
}