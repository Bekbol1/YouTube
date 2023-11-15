package com.example.youtube.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.youtube.core.network.RemoteDataSource
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.domain.paging.PlaylistsItemsPagingSource
import com.example.youtube.domain.paging.PlaylistsPagingSource

class Repository(private val remoteDataSource: RemoteDataSource) {
    /*    suspend fun getPlaylists(): Result<PlaylistsModel> {
            return remoteDataSource.getPlaylists()
        }*/
    fun getPlaylists(): LiveData<PagingData<PlaylistsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = true,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { PlaylistsPagingSource(remoteDataSource) }
        )
        return pagingData.liveData
    }

    /*suspend fun getPlaylistItems(playlistId: String): Result<PlaylistsModel> {
        return remoteDataSource.getPlaylistItems(playlistId)
    }*/
    fun getPlaylistsItems(playlistsId: String): LiveData<PagingData<PlaylistsModel.Item>> {
        val pagingData = Pager(
            config = PagingConfig(
                initialLoadSize = 20,
                pageSize = Int.MAX_VALUE,
                enablePlaceholders = true,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { PlaylistsItemsPagingSource(remoteDataSource, playlistsId) }
        )
        return pagingData.liveData
    }

    suspend fun getVideosDetails(videoId: String): Result<PlaylistsModel> {
        return remoteDataSource.getVideosDetails(videoId)
    }
}