package com.luffy.smartplay.ui.fragment

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.FragmentMainBinding
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.db.repo.MusicRepository
import com.luffy.smartplay.ui.activity.MusicFilterActivity
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.MainFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by axnshy on 16/4/18.
 */
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {
    //private ExpandableListView mExpandableListView;
    private val topList: List<String>? = null
    private val defaultChildList: List<String>? = null
    private val map: Map<String, String>? = null

    override val viewModel: MainFragmentViewModel by lazy{ViewModelProvider(this)[MainFragmentViewModel::class.java]}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.composeContainer.setContent {
            HomeFragmentContent()
            CreateAlbumDialog()
        }
    }

    @Preview
    @Composable
    fun HomeFragmentContent(viewModel: MainFragmentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
        val _state = viewModel.state.collectAsState()
        Column(
            modifier = Modifier
                .fillMaxHeight(1F)
                .wrapContentWidth()
                .background(Color(0xfff8f8ff)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(modifier = Modifier.padding(Dp(5F))) {
                ImageWithTitle(R.drawable.round_schedule_black_36, "最近播放")
                ImageWithTitle(R.drawable.round_grid_view_black_36, "本地", click = {
                    startActivity(Intent(requireContext(),MusicFilterActivity::class.java))
                })
                ImageWithTitle(R.drawable.round_cloud_black_36, "上传")
                ImageWithTitle(R.drawable.round_more_horiz_black_36, "更多")
            }
            Row(
                modifier = Modifier
                    .padding(Dp(5F))
                    .fillMaxWidth(1F),
            ) {
                ImageWithTitle(R.drawable.round_add_black_36, "添加歌曲")
                ImageWithTitle(R.drawable.round_add_black_36, "添加歌单", click = {
                    viewModel.showCreateAlbumDialog()
                })
                ImageWithTitle(R.drawable.round_add_black_36, "本地扫描", click = {
                    lifecycleScope.launch(Dispatchers.IO) {
                        MusicRepository.scanMusic()
                    }
                })
            }

            FavoriteRow(_state.value.favoriteMusicSize, _state.value.favoriteBoxBg)
            LazyColumn {
                items(_state.value.albumList) { album ->
                    AlbumRow(album)
                }
            }
        }
    }

    @Composable
    fun ImageWithTitle(id: Int, title: String, click: () -> Any = { }) {
        Column(
            modifier = Modifier
                .size(Dp(80F), Dp(100F))
                .clickable {
                    click.invoke()
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = id),
                contentDescription = "",
                modifier = Modifier.size(Dp(60F))
            )
            Text(text = title)
        }
    }

    @Composable
    fun FavoriteRow(count: Int, background: Bitmap?) {
        Row(
            modifier = Modifier
                .height(Dp(80F))
                .fillMaxWidth()
                .background(Color(0xfff8f8ff))
                .padding(Dp(5F)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(Dp(80F)), contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if(background!= null)BitmapPainter(background.asImageBitmap())else painterResource(id = R.drawable.appicon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(Dp(48F))
                        .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                )

                Spacer(
                    modifier = Modifier
                        .size(Dp(48F))
                        .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                        .background(Color(0x99000000))
                )


                Image(
                    painter = painterResource(id = R.drawable.round_favorite_36),
                    contentDescription = "",
                    modifier = Modifier.size(Dp(20F))
                )
            }

            Column {
                Text(text = "我喜欢的音乐")
                Row {
//                    Icon(painter = painterResource(id = R.drawable.round_fa), contentDescription = "", modifier = Modifier.size(Dp(5F)))
                    Text(text = "共 $count 首")
                }
            }
        }
    }

    @Composable
    fun AlbumRow(album: AlbumData) {
        Row(
            modifier = Modifier
                .width(Dp(100F))
                .fillMaxHeight(1F),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = R.drawable.icon_play), contentDescription = "")
            Text(text = album.albumName ?: "")
        }
    }

    @Composable
    fun CreateAlbumDialog(viewModel: MainFragmentViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
        val _state = viewModel.state.collectAsState()
        if (_state.value.createAlbumDailog) {
            var albumName = remember {
                mutableStateOf("")
            }
            Dialog(onDismissRequest = { viewModel.dismissCreateAlbumDialog() }) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "创建歌单", fontSize = TextUnit.Unspecified)
                    TextField(value = "", onValueChange = {

                    })

                    Button(onClick = {
                        viewModel.createAlbum(albumName.value)
                    }) {
                        Text(text = stringResource(id = R.string.album_create))
                    }
                }
            }
        }
    }


    override fun bindViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(layoutInflater, container, false)
    }

}
