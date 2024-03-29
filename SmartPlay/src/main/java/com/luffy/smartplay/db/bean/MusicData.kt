/**
 * Created by axnshy on 16/5/20.
 */
package com.luffy.smartplay.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "musics")
data class MusicData(
    /**
     * 数据库中的_id
     */
    @PrimaryKey(autoGenerate = true)
    var Id: Long = 0,
    var musicId: Int = -1,
    var album: String = "",
    var albumId: String = "",
    var duration: Long = 0,
    var title: String = "",
    var artist: String = "",
    var data: String = "",

    //歌曲文件名称
    var displayName: String = "",

    //发布日期
    var publish: Long = 0,

    //文件大小
    var size :Long= 0,

    //  0    1    2    3    4    5
    var category :Int= 0,

    //  0    1    2    3    4    5
    var filetype :Int= 0,
){
    fun getMusicTimeTotal(): String {
        val head = duration / 60000
        val foot = (duration % 60000) / 1000
        return "$head:$foot"
    }
}