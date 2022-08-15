package com.luffy.smartplay.ui.widget

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.luffy.smartplay.R
import com.luffy.smartplay.ui.viewmodel.UIState


@Preview
@Composable
fun PlaybackControllerUI(callback: PlaybackControllerCallback = DefaultControllerCallback()) {
    Row(
        modifier = Modifier
            .background(colorResource(id = R.color.colorPrimary))
            .size(
                Dp(1080F),
                Dp(80F)
            )
            .absolutePadding(top = Dp(10F), bottom = Dp(10F)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ActionRepeat(mode = 0, onValueChanged = {}, callback = callback::onRepeat)
        Spacer(modifier = Modifier.size(Dp(30F)))
        ActionPrevious(callback = callback::onPrevious)
        Spacer(modifier = Modifier.size(Dp(20F)))
        ActionPlay(mode = 0, onValueChanged = {}, callback = callback::onPlayOrPause)
        Spacer(modifier = Modifier.size(Dp(20F)))
        ActionNext(callback = callback::onNext)
        Spacer(modifier = Modifier.size(Dp(30F)))
        ActionShuffle(mode = 0, onValueChanged = {}, callback = callback::onShuffle)
    }

}

@Composable
fun ActionRepeat(mode: Int, onValueChanged: (Int) -> Unit, callback: (Int) -> Unit? = { }) {
    Image(painter = painterResource(id = R.drawable.repeat), contentDescription = "repeat",
        alignment = Alignment.Center, modifier = Modifier
            .size(Dp(24F))
            .clickable {
                callback.invoke(mode)
            })
}

@Composable
fun ActionPrevious(callback: () -> Unit? = { }) {
    Image(painter = painterResource(id = R.drawable.prev), contentDescription = "previous",
        alignment = Alignment.Center, modifier = Modifier
            .size(Dp(32F))
            .clickable {
                callback.invoke()
            })
}

@Composable
fun ActionPlay(mode: Int, onValueChanged: (Int) -> Unit, callback: () -> Unit? = { }) {
    Image(painter = painterResource(id = R.drawable.icon_play), contentDescription = "play",
        alignment = Alignment.Center, modifier = Modifier
            .size(Dp(48F))
            .clickable {
                callback.invoke()
            })
}

@Composable
fun ActionNext(callback: () -> Unit? = { }) {
    Image(painter = painterResource(id = R.drawable.next), contentDescription = "next",
        alignment = Alignment.Center, modifier = Modifier
            .size(Dp(32F))
            .clickable {
                callback.invoke()
            })
}

@Composable
fun ActionShuffle(mode: Int, onValueChanged: (Int) -> Unit, callback: (Int) -> Unit? = { }) {
    val state by remember {
        mutableStateOf(mode)
    }
    Image(painter = painterResource(id = R.drawable.shuffle), contentDescription = "shuffle",
        alignment = Alignment.Center, modifier = Modifier
            .size(Dp(24F))
            .clickable {
                callback.invoke(mode)
            })
}

interface PlaybackControllerCallback {
    fun onRepeat(value: Int)
    fun onPrevious()
    fun onPlayOrPause()
    fun onNext()
    fun onShuffle(value: Int)
}

class DefaultControllerCallback : PlaybackControllerCallback {
    override fun onRepeat(value: Int) {
    }

    override fun onPrevious() {
    }

    override fun onPlayOrPause() {
    }

    override fun onNext() {
    }

    override fun onShuffle(value: Int) {
    }

}


@Preview
@Composable
fun SimpleController(
    state: MutableState<PlaybackControllerState> = mutableStateOf(
        PlaybackControllerState(true, name = "Title", singer = "SingerXXXXX")
    )
) {
    Row(
        modifier = Modifier
            .height(91.dp)
            .width(480.dp)
            .clip(shape = RoundedCornerShape(25.dp))
            .background(Color(0xCC2D0C42)), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.appicon),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp)
                .size(64.dp)
                .clip(shape = RoundedCornerShape(20.dp))
                .border(width = 0.dp, Color.Transparent)
        )
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth()
                .weight(weight = 1F),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = state.value.name, color = colorResource(id = R.color.white))
            Text(text = state.value.singer, color = colorResource(id = R.color.white))
        }

        Image(
            painter = painterResource(
                id = if (state.value.isPlaying) {
                    R.drawable.icon_pause
                } else {
                    R.drawable.icon_play
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .padding(start = 18.dp, end = 18.dp)
                .size(48.dp)

                .background(Color.Transparent),
        )
    }
}

data class PlaybackControllerState(
    val isPlaying: Boolean = false,
    val avatar: Bitmap? = null,
    val name: String = "",
    val singer: String = ""
) : UIState()
