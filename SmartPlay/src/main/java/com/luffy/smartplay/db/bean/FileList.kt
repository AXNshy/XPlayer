/**
 * Created by axnshy on 16/5/20.
 */
package com.luffy.smartplay.db.bean

import android.content.*
import android.provider.MediaStore
import java.util.ArrayList

/**
 * Created by axnshy on 16/5/20.
 */
class FileList(context: Context) : ArrayList<Any?>() {
    companion object {
        /* private static Context getContext(){
        return this.context;
    }*/
        fun getFileList(context: Context): ArrayList<MusicData>? {
            /* if(mFolderInfoDao == null) {
            mFolderInfoDao = new FolderInfoDao(context);
        }
        SPStorage sp = new SPStorage(context);*/
            val uri = MediaStore.Files.getContentUri("external")
            val contentResolver = context.contentResolver
            val cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER
            )
            var i = 0
            val cursorCount = cursor!!.count
            if (cursorCount > 0) {
                cursor.moveToFirst()
                while (i < cursorCount) {
                    //歌曲ID：MediaStore.Audio.Media._ID
                    val id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))


                    //歌曲的名称 ：MediaStore.Audio.Media.TITL
                    val tilte =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))

                    //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                    val album =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM))

                    //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                    val artist =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST))

                    //歌曲文件的全路径 ：MediaStore.Audio.Media.DATA
                    val url =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA))

                    //歌曲文件的名称：MediaStroe.Audio.Media.DISPLAY_NAME
                    val display_name =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))

                    //歌曲文件的发行日期：MediaStore.Audio.Media.YEAR
                    val year =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR))


                    //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                    val duration =
                        cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION))

                    //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                    val size =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))
                    if (url.toLowerCase().indexOf("指定的歌曲路径") > 0) {
                        //DatabaseHelper.getInstance(context).execSQL("");
                    }
                    i++
                    cursor.moveToNext()
                }
                cursor.close()
            }
            return null
        }
    }

    init {
        getFileList(context)
    }
}