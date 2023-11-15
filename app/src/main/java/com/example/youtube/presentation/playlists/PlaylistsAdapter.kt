package com.example.youtube.presentation.playlists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.ItemPlaylistBinding

class PlaylistsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlaylistsModel.Item>,
    private val onClickItem: (playlistsModelItem: PlaylistsModel.Item) -> Unit
) : PagingDataAdapter<PlaylistsModel.Item,PlaylistsAdapter.PlaylistsViewHolder>(diffUtilCallback) {

/*    private var list = mutableListOf<PlaylistsModel.Item>()*/
    //тут адаптер получает 2 источника чтобы обновить данные,из-за этого был тот баг,с непрогрузкой плейлиста
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistsViewHolder {
        return PlaylistsViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
/*
    override fun getItemCount(): Int = list.size*/
    //ему для корректной работы нужен только 1 источник

    override fun onBindViewHolder(holder: PlaylistsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let{
            holder.toBind(it)
        }
    }

    inner class PlaylistsViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun toBind(item: PlaylistsModel.Item) {
            binding.tvPlaylistName.text = item.snippet.title
            binding.tvPlaylistVideoCount.text =
                item.contentDetails.itemCount.toString() + " video series"
            binding.imgVideo.load(item.snippet.thumbnails.default.url)
            itemView.setOnClickListener { onClickItem(item) }
        }
    }
}