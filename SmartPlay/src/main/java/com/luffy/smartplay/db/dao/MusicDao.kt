/**
 * Created by axnshy on 16/5/20.
 */
package com.luffy.smartplay.db.dao

import com.luffy.smartplay.db.bean.MusicData
import androidx.room.*

@Dao
interface MusicDao {

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun addMusicData(musicData: MusicData)

    @Delete
    fun removeMusic(musicData: MusicData)

    @Query("SELECT * FROM musics WHERE albumId=:albumId ORDER BY musicName DESC")
    fun queryMusicByAlbumId(albumId:String)


}