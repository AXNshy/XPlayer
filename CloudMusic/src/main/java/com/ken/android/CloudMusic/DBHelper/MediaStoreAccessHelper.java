package com.ken.android.CloudMusic.DBHelper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

public class MediaStoreAccessHelper {


	public static Cursor getAllSongsWithSelection(Context context, 
												  String selection, 
												  String[] projection, 
												  String sortOrder) {
		
		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		
		return contentResolver.query(uri, projection, selection, null, sortOrder);
		
	}
	
	/**
	 * public static Cursor getAllSongs(Context context,String[] projection,String sortOrder)
	 *
	 * Queries MediaStore and returns a cursor with all songs.
	 */
	public static Cursor getAllSongUri(Context context,

									  String sortOrder) {

		ContentResolver contentResolver = context.getContentResolver();
		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";

		return contentResolver.query(uri,null,selection,null,sortOrder);
	}

}
