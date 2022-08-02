package com.luffy.smartplay.db.repo

import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.dao.MusicDao

object MusicRepository {
    val dao : MusicDao by lazy { MyDatabase.getInstance().musicDao() }


    suspend fun insertToAlbum(albumId:String,data: MusicData){
        data.albumId = albumId
        dao.addMusicData(data)
    }
}