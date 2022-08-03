/**
 * Created by axnshy on 16/5/20.
 */
package com.luffy.smartplay.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.MyDatabase.Companion.DB_VERSION
import com.luffy.smartplay.db.dao.AlbumDao
import com.luffy.smartplay.db.dao.MusicDao
import com.luffy.smartplay.ui.MyApp

@Database(entities=[MusicData::class, AlbumData::class],version =DB_VERSION, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    companion object {
        const val DB_VERSION = 3
        const val DB_NAME = "music_player"
        const val TABLE_MUSIC = "music_info"
        const val TABLE_USER = "user_info"
        const val TABLE_LISTS = "music_list_info"
        const val TABLE_MUSIC_LIST_RELATIONSHIP = "music_list_relationship"
        // For Singleton instantiation
        @Volatile private var instance: MyDatabase? = null

        fun getInstance(): MyDatabase {
            return getInstance(MyApp.context)
        }

        fun getInstance(context: Context): MyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MyDatabase {
            return Room.databaseBuilder(context, MyDatabase::class.java, DB_NAME).build()
        }
    }

    abstract fun musicDao() : MusicDao
    abstract fun albumDao() : AlbumDao
}