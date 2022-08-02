package com.luffy.smartplay

import android.os.Bundle
import android.content.*
import android.os.Parcelable
import android.os.Parcel
import android.os.Parcelable.Creator
import java.util.*

/**
 * Created by axnshy on 16/5/25.
 */
data class User(
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var nickName: String? = null,
    var telephone: String? = null,
    var email: String? = null,
    var gender: Int? = null,
    var avatarUri: String? = null,
    var registStart: String? = null,
    var qq: String? = null,
    var birthday: Date? = null,
    var address: String? = null,
)