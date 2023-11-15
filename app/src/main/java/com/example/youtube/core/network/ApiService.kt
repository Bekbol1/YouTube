package com.example.youtube.core.network

import com.example.youtube.data.model.PlaylistsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    suspend fun getPlaylists(
        @Query("part")
        part: String,
        @Query("channelId")
        channelId: String,
        @Query("key")
        apiKey: String,
        @Query("maxResults")
        maxResults: Int,
        @Query("pageToken")
        nextPageToken :String
    ): Response<PlaylistsModel>

    @GET("playlistItems")
    suspend fun getPlaylistItems(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("playlistId") playlistId: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken")
        nextPageToken: String
    ): Response<PlaylistsModel>

    @GET("videos")
    suspend fun getVideosDetails(
        @Query("part") part: String,
        @Query("key") apiKey: String,
        @Query("id") videoId: String
    ): Response<PlaylistsModel>
}