package com.luffy.smartplay.db.repo

import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.db.dao.AlbumDao

object AlbumRepository {
    private val dao: AlbumDao by lazy { MyDatabase.getInstance().albumDao() }


    suspend fun createCustomAlbum(album: AlbumData) {
        dao.createAlbum(album)
    }


}