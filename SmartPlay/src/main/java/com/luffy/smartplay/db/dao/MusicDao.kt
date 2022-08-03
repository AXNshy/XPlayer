/**
 * Created by axnshy on 16/5/20.
 */
package com.luffy.smartplay.db.dao

import com.luffy.smartplay.db.bean.MusicData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MusicDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMusicData(musicData: MusicData)

    @Delete
    suspend fun removeMusic(musicData: MusicData)

    @Query("SELECT * FROM musics WHERE albumId=:albumId ORDER BY musicName DESC")
    suspend fun queryMusicsByAlbumId(albumId:String) : List<MusicData>

    @Query("SELECT * FROM musics WHERE albumId=:albumId ORDER BY musicName DESC")
    fun queryMusicsFlowByAlbumId(albumId:String) : Flow<List<MusicData>>
}