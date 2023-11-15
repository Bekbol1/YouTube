package com.example.youtube.core.network

import com.example.youtube.BuildConfig
import com.example.youtube.core.base.BaseDataSource
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.utils.Constants

class RemoteDataSource(private val apiService: ApiService) : BaseDataSource() {

    suspend fun getPlaylists(nextPageToken:String): Result<PlaylistsModel> {
        return getResult {
            apiService.getPlaylists(
                part = Constants.PART,
                channelId = Constants.CHANNEL_ID,
                apiKey = BuildConfig.API_KEY,
                maxResults = 16,
                nextPageToken = nextPageToken
            )
        }
    }

    suspend fun getPlaylistItems(nextPageToken: String,playlistId: String): Result<PlaylistsModel> {
        return getResult {
            apiService.getPlaylistItems(
                part = Constants.PART,
                apiKey = BuildConfig.API_KEY,
                playlistId = playlistId,
                maxResults = 16,
                nextPageToken = nextPageToken
            )
        }
    }

    suspend fun getVideosDetails(videoId: String): Result<PlaylistsModel> {
        return getResult {
            apiService.getVideosDetails(
                part = Constants.PART,
                apiKey = BuildConfig.API_KEY,
                videoId = videoId
            )
        }
    }
}