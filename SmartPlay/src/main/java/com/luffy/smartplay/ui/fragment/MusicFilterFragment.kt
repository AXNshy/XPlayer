package com.luffy.smartplay.ui.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.luffy.smartplay.R
import com.luffy.smartplay.db.bean.MusicData
import com.luffy.smartplay.db.repo.MusicRepository
import com.luffy.smartplay.ui.base.BaseComposeFragment
import com.luffy.smartplay.ui.viewmodel.BaseViewModel
import com.luffy.smartplay.ui.viewmodel.UIState
import com.luffy.smartplay.utils.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MusicFilterFragment: BaseComposeFragment<MusicFilterFragment.MusicFilterFragmentViewModel>() {



    class MusicFilterFragmentViewModel :BaseViewModel<MusicFilterUIState>() {

        init {
            viewModelScope.launch(Dispatchers.IO) {
                MusicRepository.queryLocalMusicsFlow().collect{

                    Logger.d(TAG,"queryLocalMusicsFlow $it")
                    _state.value = _state.value.copy(list = it)
                }
            }
        }

        override fun init(): MusicFilterUIState {
            return MusicFilterUIState()
        }

        fun scanMusic() {
            Logger.d(TAG,"scanMusic")
            viewModelScope.launch {
                MusicRepository.scanMusic()
            }
        }
    }

    data class MusicFilterUIState(val list:List<MusicData> = mutableListOf()) :UIState()

    override val viewModel: MusicFilterFragmentViewModel by lazy { ViewModelProvider(this)[MusicFilterFragmentViewModel::class.java] }

    override fun onComposeViewCreate(view: ComposeView) {
        view.setContent {
            MusicFilterContent()
        }
    }

    @Preview
    @Composable
    fun MusicFilterContent(){
        val state = viewModel.state.collectAsState()
        Column(modifier = Modifier.padding(10.dp)) {
            if(state.value.list.isEmpty()){
                Row(modifier = Modifier.padding(8.dp)) {
                    Button(onClick = { viewModel.scanMusic() }) {
                        Text(text = "扫描本地文件")
                    }
                }
            }else{

            }
            LazyColumn{
                items(state.value.list){ item->
                    MusicItemContent(data = item)
                }
            }

        }
    }

    @Composable
    fun MusicItemContent(data: MusicData){
        Row (modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(1f)){
            Column(modifier = Modifier.fillMaxWidth(1F)) {
                Text(text = data.displayName.ifBlank { data.musicName })
                Row {
                    Text(text = "SQ", modifier = Modifier
                        .background(Color(0xf9dddc))
                        .padding(2.dp), color = Color(0xd6251f))
                    Text(text = "${data.artist} ${data.album}")
                }
            }
            Icon(painter = painterResource(id = R.drawable.round_more_horiz_black_48), contentDescription = "")
        }
    }

}