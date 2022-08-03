package com.luffy.smartplay.db.bean

import android.os.Bundle
import android.content.*
import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by axnshy on 16/5/25.
 */
@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var username: String = "",
    var password: String = "",
    var nickName: String = "",
    var telephone: String = "",
    var email: String = "",
    var gender: Int = 0,
    var avatarUri: String = "",
    var registStart: String = "",
    var qq: String = "",
    var birthday: Long = 0,
    var address: String = "",
    var registerTime: Long = 0
)