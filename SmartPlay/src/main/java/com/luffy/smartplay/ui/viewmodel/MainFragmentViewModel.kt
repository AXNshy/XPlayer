package com.luffy.smartplay.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.luffy.smartplay.db.bean.AlbumData
import com.luffy.smartplay.db.repo.AlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel : BaseViewModel<MainFragmentViewModel.MainFragmentState>() {

    data class MainFragmentState(
        val albumList: List<AlbumData> = mutableListOf(),
        val favoriteBoxBg: Bitmap? = null,
        val favoriteMusicSize: Int = 0,
        val createAlbumDailog: Boolean = false
    ) : UIState()

    override fun init(): MainFragmentState {
        return MainFragmentState()
    }

    fun createAlbum(name: String, avatarUri: String = "", des: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            AlbumRepository.createCustomAlbum(AlbumData().apply {
                albumName = name
                createData = System.currentTimeMillis()
                albumAvatar = avatarUri
                description = des
            })
        }
    }

    fun showCreateAlbumDialog() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(createAlbumDailog = true))
        }
    }

    fun dismissCreateAlbumDialog() {
        viewModelScope.launch {
            _state.emit(_state.value.copy(createAlbumDailog = false))
        }
    }

}