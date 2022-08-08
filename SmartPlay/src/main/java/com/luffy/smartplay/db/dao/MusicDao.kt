/**
 * Created by axnshy on 16/5/20.
 */
package com.luffy.smartplay.db.dao

import androidx.room.*
import com.luffy.smartplay.db.bean.MusicData
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMusicData(musicData: MusicData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMusicData(musicData: List<MusicData>)

    @Delete
    suspend fun removeMusic(musicData: MusicData)

    @Query("SELECT * FROM musics WHERE albumId=:albumId ORDER BY title DESC")
    suspend fun queryMusicsByAlbumId(albumId:String) : List<MusicData>

    @Query("SELECT * FROM musics WHERE albumId=:albumId ORDER BY title DESC")
    fun queryMusicsFlowByAlbumId(albumId:String) : Flow<List<MusicData>>

    @Query("SELECT * FROM musics ORDER BY title DESC")
    fun queryLocalMusics() : List<MusicData>

    @Query("SELECT * FROM musics ORDER BY title DESC")
    fun queryLocalMusicsFlow() : Flow<List<MusicData>>
}