package com.luffy.smartplay.db.repo

import android.content.ContentValues
import android.provider.MediaStore
import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.ui.MyApp
import kotlinx.coroutines.flow.Flow

object MusicRepository {
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


    suspend fun queryLocalMusics() : List<MusicData>{
        return dao.queryLocalMusics()
    }



    suspend fun scanMusic(){
        val queryUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val set = mutableListOf<MusicData>()
        MyApp.context.contentResolver.query(queryUri,null,null,null,null)?.use {
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
                        it.getString(it.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR))

                }
                set.add(data)

            }while (it.moveToNext())
        }
        addMusic(set)
    }
}