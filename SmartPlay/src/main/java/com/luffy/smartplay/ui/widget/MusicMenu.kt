package com.luffy.smartplay.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.luffy.smartplay.R
import com.luffy.smartplay.db.bean.MusicData


@Composable
fun MusicMenuDialog(
    dataState: MutableState<MusicData?>,
    callback: MenuCallback = DefaultCallback()
) {
    if (dataState.value != null) {
        Dialog(onDismissRequest = { dataState.value = null }) {
            MusicMenu(dataState, callback)
        }
    }
}

@Composable
fun MusicMenu(dataState: MutableState<MusicData?>, callback: MenuCallback = DefaultCallback()) {
//    val data = dataState.value!!
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(800.dp)
            .background(
                color = colorResource(id = R.color.white), shape = RoundedCornerShape(10.dp)
            )
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp, 8.dp)) {
            Text(text = "歌曲:")
            Text(text = "${dataState.value?.album}")
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = colorResource(id = R.color.black_60))
        )
        MenuItem(
            stringResource(id = R.string.playnext),
            R.drawable.rightsquared,
            click = {
                callback.onSkipToNext(dataState.value!!).also {
                    dataState.value = null
                }
            })
        MenuItem(
            stringResource(id = R.string.collectToCollection),
            R.drawable.musicfolder,
            click = { callback.onCollect(dataState.value!!) })
        MenuItem(
            stringResource(id = R.string.download),
            R.drawable.download1,
            click = { callback.onDownload(dataState.value!!) })
        MenuItem(
            stringResource(id = R.string.comments),
            R.drawable.comments,
            click = { callback.onComments(dataState.value!!) })
        MenuItem(
            stringResource(id = R.string.share),
            R.drawable.share,
            click = { callback.onShare(dataState.value!!) })
        MenuItem(
            stringResource(id = R.string.delete),
            R.drawable.delete,
            click = { callback.onDelete(dataState.value!!) })
        MenuItem(
            stringResource(id = R.string.singer),
            R.drawable.microphone,
            click = { callback.onSinger(dataState.value!!) })
        MenuItem(
            stringResource(id = R.string.album),
            R.drawable.album,
            click = { callback.onAlbum(dataState.value!!) })
    }
}

@Composable
inline fun MenuItem(
    name: String = "",
    id: Int,
    enable: Boolean = true,
    crossinline click: () -> Any = {}
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp, 8.dp)
            .clickable { click.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = id), contentDescription = "")
        Text(text = name, Modifier.padding(5.dp, 0.dp, 0.dp, 0.dp))
    }
}

class DefaultCallback : MenuCallback {
    override fun onSkipToNext(data: MusicData) {
    }

    override fun onCollect(data: MusicData) {
    }

    override fun onDownload(data: MusicData) {
    }

    override fun onComments(data: MusicData) {
    }

    override fun onShare(data: MusicData) {
    }

    override fun onDelete(data: MusicData) {
    }

    override fun onSinger(data: MusicData) {
    }

    override fun onAlbum(data: MusicData) {
    }

}

interface MenuCallback {
    fun onSkipToNext(data: MusicData)
    fun onCollect(data: MusicData)
    fun onDownload(data: MusicData)
    fun onComments(data: MusicData)
    fun onShare(data: MusicData)
    fun onDelete(data: MusicData)
    fun onSinger(data: MusicData)
    fun onAlbum(data: MusicData)
}