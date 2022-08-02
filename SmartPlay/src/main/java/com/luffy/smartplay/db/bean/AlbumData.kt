package com.luffy.smartplay.db.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by axnshy on 16/8/9.
 */
@Entity(tableName = "albums")
data class AlbumData (
    @PrimaryKey(autoGenerate = true)
    var Id:Long,
    var albumId:String ="",
    var albumName: String? = null,
    var modifiedData :Long = 0,
    var createData :Long = 0,
    var backgroundPath: String? = null,
    var count: Int = 0,
    var description: String = "",
)