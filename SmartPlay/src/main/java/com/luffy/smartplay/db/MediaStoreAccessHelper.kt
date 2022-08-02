package com.luffy.smartplay.db

import android.content.*
import android.database.Cursor
import android.provider.MediaStore

object MediaStoreAccessHelper {
    fun getAllSongsWithSelection(
        context: Context,
        selection: String?,
        projection: Array<String?>?,
        sortOrder: String?
    ): Cursor? {
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        return contentResolver.query(uri, projection, selection, null, sortOrder)
    }

    /**
     * public static Cursor getAllSongs(Context context,String[] projection,String sortOrder)
     *
     * Queries MediaStore and returns a cursor with all songs.
     */
    fun getAllSongUri(
        context: Context,
        sortOrder: String?
    ): Cursor? {
        val contentResolver = context.contentResolver
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"
        return contentResolver.query(uri, null, selection, null, sortOrder)
    }
}