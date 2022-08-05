package com.luffy.smartplay.ui.fragment

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.ViewModelProvider
import com.luffy.smartplay.R
import com.luffy.smartplay.databinding.FragmentMainBinding
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.ui.base.BaseFragment
import com.luffy.smartplay.ui.viewmodel.MainFragmentViewModel

/**
 * Created by axnshy on 16/4/18.
 */
class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {
    //private ExpandableListView mExpandableListView;
    private val topList: List<String>? = null
    private val defaultChildList: List<String>? = null
    private val map: Map<String, String>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MainFragmentViewModel::class.java]
        viewBinding = FragmentMainBinding.inflate(layoutInflater, container, false)
        viewBinding.composeContainer.setContent {
            HomeFragmentContent()
        }
        return viewBinding.root
    }

    @Preview
    @Composable
    fun HomeFragmentContent(){
        val state by remember{ mutableStateOf(viewModel.state)}
        Column(modifier = Modifier
            .fillMaxHeight(1F)
            .wrapContentWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
            Row(modifier = Modifier.padding(Dp(5F))) {
                ImageWithTitle(R.drawable.round_schedule_black_36,"最近播放")
                ImageWithTitle(R.drawable.round_grid_view_black_36,"本地")
                ImageWithTitle(R.drawable.round_cloud_black_36,"上传")
                ImageWithTitle(R.drawable.round_more_horiz_black_36,"更多")
            }

            FavoriteRow(10,null)
            LazyColumn {
                items(state.value.albumList){ album->
                    AlbumRow(album)
                }
            }
        }    
    }

    @Composable
    fun ImageWithTitle(id:Int,title:String){
        Column(modifier = Modifier.size(Dp(80F),Dp(100F)), horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
            Icon(painter = painterResource(id = id), contentDescription = "")
            Text(text = title)
        }
    }

    @Composable
    fun FavoriteRow(count:Int,background:Bitmap?){
        Row(modifier = Modifier
            .height(Dp(80F))
            .fillMaxWidth()
            .padding(Dp(5F)), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(Dp(80F)), contentAlignment = Alignment.Center){
                Image(painter = painterResource(id = R.drawable.appicon), contentDescription = "")
                Icon(painter = painterResource(id = R.drawable.round_favorite_black_36), contentDescription = "")
            }

            Column {
                Text(text = "我喜欢的音乐")
                Row {
                    Icon(Icons.Default.Add, contentDescription = "", modifier = Modifier.size(Dp(5F)))
                    Text(text = "共 $count 首")
                }
            }
            Text(text ="title")
        }
    }

    @Composable
    fun AlbumRow(album:AlbumData){
        Row(modifier = Modifier.size(Dp(80F),Dp(100F)), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.play), contentDescription = "")
            Text(text = album.albumName?:"")
        }
    }

    companion object{
        val MyAppIcons = Icons.Rounded
    }
    
}
