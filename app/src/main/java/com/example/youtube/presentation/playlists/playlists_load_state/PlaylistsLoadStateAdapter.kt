package com.example.youtube.presentation.playlists.playlists_load_state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.youtube.R
import com.example.youtube.databinding.ItemProgressBarBinding

class PlaylistsLoadStateAdapter():LoadStateAdapter<PlaylistsLoadStateAdapter.PlaylistsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PlaylistsLoadStateViewHolder, loadState: LoadState) {
        holder.toBind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PlaylistsLoadStateViewHolder {
        return PlaylistsLoadStateViewHolder.create(
            parent = parent
        )
    }
 class PlaylistsLoadStateViewHolder(
        binding:ItemProgressBarBinding
    ):RecyclerView.ViewHolder(binding.root){

        fun toBind(loadState: LoadState) {
            loadState.endOfPaginationReached
        }

        companion object {
            fun create(parent: ViewGroup): PlaylistsLoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress_bar, parent, false)
                val binding = ItemProgressBarBinding.bind(view)
                return PlaylistsLoadStateViewHolder(binding)
            }
        }
    }
}