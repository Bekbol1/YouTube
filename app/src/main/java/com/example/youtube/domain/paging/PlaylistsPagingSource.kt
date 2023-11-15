package com.example.youtube.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.youtube.core.network.RemoteDataSource
import com.example.youtube.data.model.PlaylistsModel

class PlaylistsPagingSource(private val remoteDataSource: RemoteDataSource) :
    PagingSource<String, PlaylistsModel.Item>() {
    override fun getRefreshKey(state: PagingState<String, PlaylistsModel.Item>): String? {
        return null
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PlaylistsModel.Item> {
        try {
            val nextPageToken = params.key ?: ""
            val response = remoteDataSource.getPlaylists(nextPageToken)
            val items = mutableListOf<PlaylistsModel.Item>()

            when {
                response.isSuccess -> response.onSuccess {
                    items.addAll(it.items)
                }

                response.isFailure -> response.onFailure {

                }
            }

            var nextKey = ""

            if (response.isSuccess) response.onSuccess { nextKey = it.nextPageToken }

            return LoadResult.Page(
                data = items,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}