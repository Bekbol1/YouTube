package com.example.youtube.presentation.playlistitems

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.ItemPlaylistBinding

class PlaylistItemsAdapter(
    diffUtilCallback: DiffUtil.ItemCallback<PlaylistsModel.Item>,
    private val onItemClick:(playlistItem: PlaylistsModel.Item) -> Unit
) : PagingDataAdapter<PlaylistsModel.Item,PlaylistItemsAdapter.PlaylistItemsViewHolder>(diffUtilCallback) {

    /*private var _list = mutableListOf<PlaylistsModel.Item>()
    private val list: List<PlaylistsModel.Item> get() = _list
    fun addData(playlistModelItem: List<PlaylistsModel.Item>) {
        _list.clear()
        _list.addAll(playlistModelItem)
        notifyItemRangeInserted(_list.size, playlistModelItem.size - _list.size)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PlaylistItemsViewHolder(
        ItemPlaylistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    /*override fun getItemCount() = list.size*/

    override fun onBindViewHolder(holder: PlaylistItemsViewHolder, position: Int) {
        val newPosition = getItem(position)
        newPosition?.let{
            holder.toBind(it)
        }
    }

    inner class PlaylistItemsViewHolder(private val binding: ItemPlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun toBind(item: PlaylistsModel.Item) {
            if (!item.snippet.thumbnails.default.url.isEmpty() && !item.snippet.title.isEmpty()) {
                binding.imgTransparent.visibility = View.INVISIBLE
                binding.tvInImgPlaylist.visibility = View.INVISIBLE
                binding.tvPlaylistName.text = item.snippet.title
                binding.tvPlaylistVideoCount.text = item.snippet.channelTitle
                binding.imgVideo.load(item.snippet.thumbnails.default.url)
            }
            itemView.setOnClickListener { onItemClick(item) }
        }

    }
}