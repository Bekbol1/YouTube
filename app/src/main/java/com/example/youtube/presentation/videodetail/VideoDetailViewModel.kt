package com.example.youtube.presentation.videodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.core.base.BaseViewModel
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.domain.repository.Repository

class VideoDetailViewModel(private val repository: Repository) : BaseViewModel() {
    private val _videosDetails = MutableLiveData<PlaylistsModel>()
    val videosDetails: LiveData<PlaylistsModel> get() = _videosDetails

    fun getVideosDetails(videoId: String) {
        doOperation(
            operation = { repository.getVideosDetails(videoId) },
            success = { _videosDetails.postValue(it) }
        )
    }
}