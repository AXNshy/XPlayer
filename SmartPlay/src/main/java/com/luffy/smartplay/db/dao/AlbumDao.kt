package com.luffy.smartplay.db.dao

import androidx.room.*
import com.luffy.smartplay.db.bean.AlbumData
import kotlinx.coroutines.flow.Flow

/**
 * Created by axnshy on 16/8/9.
 */
@Dao
interface AlbumDao {

    @Query("SELECT * FROM albums ORDER BY createData DESC")
    suspend fun queryAlbums(): List<AlbumData>


    @Query("SELECT * FROM albums ORDER BY createData DESC")
    fun queryAlbumsFlow(): Flow<List<AlbumData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createAlbum(album: AlbumData)

    @Delete
    suspend fun deleteAlbum(album: AlbumData)


    @Query("SELECT * FROM albums WHERE albumId=:albumId LIMIT 1")
    fun queryAlbumById(albumId: String): AlbumData
}