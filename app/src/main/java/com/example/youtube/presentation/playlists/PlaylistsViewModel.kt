package com.example.youtube.presentation.playlists

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.youtube.core.base.BaseViewModel
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.domain.repository.Repository

class PlaylistsViewModel(private val repository: Repository) : BaseViewModel() {
/*    private val _playlists = MutableLiveData<PlaylistsModel>()
    val playlists: LiveData<PlaylistsModel> get() = _playlists*/

    fun getPagingPlaylists(): LiveData<PagingData<PlaylistsModel.Item>> {
        return repository.getPlaylists()
    }

/*    fun getPlaylists() {
        doOperation(
            operation = { repository.getPlaylists() },
            success = { _playlists.postValue(it) }
        )
    }*/
}