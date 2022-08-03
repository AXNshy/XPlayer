package com.luffy.smartplay.db.dao

import android.content.Context
import android.database.Cursor
import android.util.Log
import androidx.room.Dao
import androidx.room.Query
import com.luffy.smartplay.db.bean.AlbumData
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList

/**
 * Created by axnshy on 16/8/9.
 */
@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums ORDER BY createData DESC")
    suspend fun queryAlbums() :List<AlbumData>


    @Query("SELECT * FROM albums ORDER BY createData DESC")
    fun queryAlbumsFlow() : Flow<List<AlbumData>>
}