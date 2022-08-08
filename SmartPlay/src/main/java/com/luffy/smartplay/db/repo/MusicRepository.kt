package com.luffy.smartplay.db.repo

import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.ui.MyApp
import com.luffy.smartplay.utils.Logger
import kotlinx.coroutines.flow.Flow

object MusicRepository {

    const val TAG = "MusicRepository"

    val dao: MusicDao by lazy { MyDatabase.getInstance().musicDao() }
    val ringPrefix: String =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)?.path ?: ""
    val rootPrefix: String = Environment.getExternalStorageDirectory()?.path ?: ""


    suspend fun insertToAlbum(albumId: String, data: MusicData) {
        data.albumId = albumId
        dao.addMusicData(data)
    }


    suspend fun addMusic(data: List<MusicData>) {
        dao.addMusicData(data)
    }

    suspend fun queryMusicsFlowByAlbumId(id:String) : Flow<List<MusicData>>{
        return dao.queryMusicsFlowByAlbumId(id)
    }

    suspend fun queryMusicsByAlbumId(id:String) : List<MusicData>{
        return dao.queryMusicsByAlbumId(id)
    }


    fun queryLocalMusicsFlow(): Flow<List<MusicData>> {
        return dao.queryLocalMusicsFlow()
    }

    suspend fun queryLocalMusics(): List<MusicData> {
        return dao.queryLocalMusics()
    }



    suspend fun scanMusic() {
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val queryUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Audio.Media.getContentUri(
                MediaStore.VOLUME_EXTERNAL
            )
        } else {
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }
        val set = mutableListOf<MusicData>()
        val projects = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.DISPLAY_NAME
        )

        MyApp.context.contentResolver.query(queryUri, projects, null, null, null)?.use {
            if (it.count == 0) {
                Logger.d(TAG, "scanMusic fail, MediaStore data is empty")
                return
            }
            it.moveToFirst()
            do {

                val data = MusicData().apply {
                    data =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA))

                    title
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE))
                    displayName
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME))

                    artist =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST))

                    album =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM))

                    duration =
                        it.getInt(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION))
                            .toLong()

                    size =
                        it.getInt(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.SIZE))
                            .toLong()

                    publish =
                        it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.YEAR))

                }
                if (data.data.startsWith("$ringPrefix/ringtone") ||
                    data.data.startsWith("$ringPrefix/Recordings") ||
                    data.data.startsWith("$rootPrefix/netease/cloudmusic/Cache/")
                ) {
                    Logger.d(TAG, "data is ringtone,")
                    continue
                }
                set.add(data)

            }while (it.moveToNext())
        }
        Logger.d(TAG, "scanMusic finish,result = ${set}")
        addMusic(set)
    }
}