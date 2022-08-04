package com.luffy.smartplay.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.luffy.smartplay.R




    @Preview
    @Composable
    fun PlaybackControllerUI(callback: PlaybackControllerCallback?=null){
        Row (modifier = Modifier
            .background(colorResource(id = R.color.colorPrimary))
            .size(
                Dp(1080F),
                Dp(80F)
            ).absolutePadding(top = Dp(10F), bottom = Dp(10F)), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            ActionRepeat(mode = 0, onValueChanged = {},callback = callback!!::onRepeat)
            Spacer(modifier = Modifier.size(Dp(30F)))
            ActionPrevious(callback = callback!!::onPrevious)
            Spacer(modifier = Modifier.size(Dp(20F)))
            ActionPlay(mode = 0, onValueChanged = {},callback = callback!!::onPlayOrPause)
            Spacer(modifier = Modifier.size(Dp(20F)))
            ActionNext(callback = callback!!::onNext)
            Spacer(modifier = Modifier.size(Dp(30F)))
            ActionShuffle(mode = 0, onValueChanged = {},callback = callback!!::onShuffle)
        }

    }

    @Composable
    fun ActionRepeat(mode:Int,onValueChanged:(Int) -> Unit,callback: (Int) -> Unit? = { }){
        Image(painter = painterResource(id = R.drawable.repeat), contentDescription = "repeat",
            alignment = Alignment.Center, modifier = Modifier
                .size(Dp(24F))
                .clickable {
                    callback.invoke(mode)
                })
    }

    @Composable
    fun ActionPrevious(callback: () -> Unit? = { }){
        Image(painter = painterResource(id = R.drawable.prev), contentDescription = "previous",
            alignment = Alignment.Center,modifier = Modifier
                .size(Dp(32F))
                .clickable {
                    callback.invoke()
                })
    }

    @Composable
    fun ActionPlay(mode:Int,onValueChanged:(Int) -> Unit,callback: () -> Unit? = { }){
        Image(painter = painterResource(id = R.drawable.play), contentDescription = "play",
            alignment = Alignment.Center,modifier = Modifier
                .size(Dp(48F))
                .clickable {
                    callback.invoke()
                })
    }

    @Composable
    fun ActionNext(callback: () -> Unit? = { }){
        Image(painter = painterResource(id = R.drawable.next), contentDescription = "next",
            alignment = Alignment.Center,modifier = Modifier
                .size(Dp(32F))
                .clickable {
                    callback.invoke()
                })
    }

    @Composable
    fun ActionShuffle(mode:Int,onValueChanged:(Int) -> Unit,callback: (Int) -> Unit? = { }){
        val state by remember {
            mutableStateOf(mode)
        }
        Image(painter = painterResource(id = R.drawable.shuffle), contentDescription = "shuffle",
            alignment = Alignment.Center,modifier = Modifier
                .size(Dp(24F))
                .clickable {
                    callback.invoke(mode)
                })
    }

interface PlaybackControllerCallback{
    fun onRepeat(value : Int)
    fun onPrevious()
    fun onPlayOrPause()
    fun onNext()
    fun onShuffle(value : Int)
}