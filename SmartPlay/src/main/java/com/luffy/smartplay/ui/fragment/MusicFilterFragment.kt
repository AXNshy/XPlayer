package com.luffy.smartplay.ui.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.luffy.smartplay.R
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.repo.MusicRepository
import com.luffy.smartplay.ui.base.BaseComposeFragment
import com.luffy.smartplay.ui.base.BasePlayerActivity
import com.luffy.smartplay.ui.viewmodel.BaseViewModel
import com.luffy.smartplay.ui.viewmodel.UIState
import com.luffy.smartplay.ui.widget.MenuCallback
import com.luffy.smartplay.ui.widget.MusicMenuDialog
import com.luffy.smartplay.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MusicFilterFragment :
    BaseComposeFragment<MusicFilterFragment.MusicFilterFragmentViewModel>() {


    class MusicFilterFragmentViewModel : BaseViewModel<MusicFilterUIState>() {

        init {
            viewModelScope.launch {
                MusicRepository.queryLocalMusicsFlow().collect {

                    Logger.d(TAG, "queryLocalMusicsFlow $it")
                    _state.emit(_state.value.copy(list = it))
                }
            }
        }

        override fun init(): MusicFilterUIState {
            return MusicFilterUIState()
        }

        fun scanMusic() {
            Logger.d(TAG, "scanMusic")
            viewModelScope.launch(Dispatchers.IO) {
                MusicRepository.scanMusic()
            }
        }
    }

    data class MusicFilterUIState(val list: List<MusicData> = mutableListOf()) : UIState()

    override val viewModel: MusicFilterFragmentViewModel by lazy { ViewModelProvider(this)[MusicFilterFragmentViewModel::class.java] }

    override fun onComposeViewCreate(view: ComposeView) {
        view.setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colors.background) {
                    val menuData: MutableState<MusicData?> = remember { mutableStateOf(null) }
                    MusicFilterContent(menuData)
                    MusicMenuDialog(menuData, callback = callback)
                }
            }
        }
    }

    @Composable
    fun MusicFilterContent(
        openDialog: MutableState<MusicData?>,
        viewModel: MusicFilterFragmentViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    ) {
        val state by viewModel.state.collectAsState()
        Column(modifier = Modifier.padding(10.dp)) {
            Logger.d(TAG, "MusicFilterContent")
            if (state.list.isEmpty()) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Button(onClick = { viewModel.scanMusic() }) {
                        Text(text = "扫描本地文件")
                    }
                }
            }
            LazyColumn {
                items(state.list) { item ->
                    MusicItemContent(data = item, openDialog)
                }
            }

        }
    }

    @Composable
    fun MusicItemContent(data: MusicData, openDialog: MutableState<MusicData?>) {

        Logger.d(TAG, "MusicItemContent $data")
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .clickable {
                    startPlayMusicData(data)
                },

            ) {
            Image(
                painter = painterResource(id = R.drawable.appicon),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.title.ifBlank { data.displayName })
                Row(modifier = Modifier.padding(3.dp, 0.dp, 0.dp, 0.dp)) {
                    Text(
                        text = "SQ", modifier = Modifier
                            .background(colorResource(id = R.color.red))
                            .clip(shape = RoundedCornerShape(CornerSize(2.dp)))
                            .padding(2.dp), color = colorResource(id = R.color.white)
                    )
                    Text(
                        text = "${data.artist} ${data.album}",
                        color = colorResource(id = R.color.black_60),
                        modifier = Modifier
                            .padding(2.dp),
                    )
                    Text(
                        text = data.getMusicTimeTotal(),
                        color = colorResource(id = R.color.white),
                        modifier = Modifier
                            .background(
                                colorResource(id = R.color.red)
                            )
                            .clip(shape = RoundedCornerShape(CornerSize(2.dp)))
                            .padding(2.dp)
                    )
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.round_more_horiz_black_48),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        openDialog.value = data
                    }
            )
        }
    }

    private val callback: MenuCallback = object : MenuCallback {
        override fun onSkipToNext(data: MusicData) {
            val cur = viewModel.state.value.list.indexOf(data)
            startPlayMusicData(viewModel.state.value.run {
                if (cur + 1 < list.size) {
                    list[cur + 1]
                } else {
                    list[0]
                }
            })
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


    private fun startPlayMusicData(data: MusicData) {
        val activity = requireActivity()
        if (activity is BasePlayerActivity) {
            activity.setMusicData(data)
        }
    }
}