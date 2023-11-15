package com.example.youtube.core.di

import com.example.youtube.presentation.playlistitems.PlaylistItemsViewModel
import com.example.youtube.presentation.playlists.PlaylistsViewModel
import com.example.youtube.presentation.videodetail.VideoDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { PlaylistsViewModel(get()) }
    viewModel { PlaylistItemsViewModel(get()) }
    viewModel { VideoDetailViewModel(get()) }
}