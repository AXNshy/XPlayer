package com.luffy.smartplay.db.repo

import android.provider.MediaStore
import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.ui.MyApp
import com.luffy.smartplay.utils.Logger
import kotlinx.coroutines.flow.Flow

object MusicRepository {

    const val TAG = "MusicRepository"

    val dao : MusicDao by lazy { MyDatabase.getInstance().musicDao() }


    suspend fun insertToAlbum(albumId:String,data: MusicData){
        data.albumId = albumId
        dao.addMusicData(data)
    }


    suspend fun addMusic(data: List<MusicData>){
        dao.addMusicData(data)
    }

    suspend fun queryMusicsFlowByAlbumId(id:String) : Flow<List<MusicData>>{
        return dao.queryMusicsFlowByAlbumId(id)
    }

    suspend fun queryMusicsByAlbumId(id:String) : List<MusicData>{
        return dao.queryMusicsByAlbumId(id)
    }


    suspend fun queryLocalMusicsFlow() : Flow<List<MusicData>>{
        return dao.queryLocalMusicsFlow()
    }
    suspend fun queryLocalMusics() : List<MusicData>{
        return dao.queryLocalMusics()
    }



    suspend fun scanMusic(){
//        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        val queryUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_INTERNAL)
        val set = mutableListOf<MusicData>()
        MyApp.context.contentResolver.query(queryUri,null,selection,null,null)?.use {
            if(it.count == 0){
                Logger.d(TAG,"scanMusic fail, MediaStore data is empty")
                return
            }
            do {
                val data = MusicData().apply {
                    data =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))

                    artist =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

                    album =
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                    musicName
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))

                   duration =
                        it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))

                    size =
                        it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))

                    publish =
                        it.getLong(it.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR))

                }
                set.add(data)

            }while (it.moveToNext())
        }
        addMusic(set)
    }
}