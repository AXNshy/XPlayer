package com.luffy.smartplay.db.repo

import com.luffy.smartplay.db.MyDatabase
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.dao.AlbumDao
import com.luffy.smartplay.db.dao.MusicDao

object AlbumRepository {
    val dao : AlbumDao by lazy { MyDatabase.getInstance().albumDao() }



}