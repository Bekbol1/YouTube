package com.example.youtube.presentation.playlistitems


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube.R
import com.example.youtube.core.base.BaseFragment
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.FragmentPlaylistItemsBinding
import com.example.youtube.presentation.playlists.playlists_load_state.PlaylistsLoadStateAdapter
import com.example.youtube.utils.InternetConnectionService
import com.example.youtube.utils.PlaylistModelComparator
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlaylistItemsFragment : BaseFragment<FragmentPlaylistItemsBinding, PlaylistItemsViewModel>() {
    override val viewModel: PlaylistItemsViewModel by viewModel()

    override fun inflateViewBinding(): FragmentPlaylistItemsBinding =
        FragmentPlaylistItemsBinding.inflate(layoutInflater)

    private val adapter = PlaylistItemsAdapter(PlaylistModelComparator,this::onItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*initResultListener()*/
        initLiveData()
    }

    override fun initData() {
        super.initData()
        arguments?.let {
            val playlistItem = PlaylistItemsFragmentArgs.fromBundle(it).playlistItem
            viewModel.getPagingPlaylistsItems(playlistItem.id).observe(viewLifecycleOwner) { pagingData ->
                binding.rvPlaylistItems.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PlaylistsLoadStateAdapter(),
                    footer = PlaylistsLoadStateAdapter()
                )
                adapter.submitData(lifecycle = lifecycle,pagingData = pagingData)
            }
            initHeaderContainer(playlistItem)
        }

    }

    private fun initLiveData() {
        /*viewModel.playlistItems.observe(viewLifecycleOwner) { list ->
            initRecycler(list.items)
        }*/

        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            if (loading)
                binding.progressBar.visibility = View.VISIBLE
            else
                binding.progressBar.visibility = View.GONE
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    /*private fun initRecycler(items: List<PlaylistsModel.Item>) {
        adapter.addData(items)
        binding.rvPlaylistItems.adapter = adapter
    }*/

    override fun checkConnection() {
        InternetConnectionService(requireContext()).observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.llContainerPlaylistItems.visibility = View.GONE
                binding.flInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnCheckInternetTryAgain.setOnClickListener {
                if (isConnect) {
                    binding.llContainerPlaylistItems.visibility = View.VISIBLE
                    binding.flInclude.visibility = View.GONE
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.no_connection_try_again),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    /*private fun initResultListener() {
        setFragmentResultListener(Constants.KEY_PLAYLIST_TO_PLAYLIST_ITEMS) { _, bundle ->
            bundle.getSerializable(Constants.KEY_SET_VIDEO_TO_FRAGMENT_ITEMS)
                ?.let { item ->
                    val _item = item as PlaylistsModel.Item
                    initHeaderContainer(_item)
                    initView(_item.id)
                }
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun initHeaderContainer(item: PlaylistsModel.Item) {
        binding.tvTitle.text = item.snippet.title
        binding.tvDescription.text = item.snippet.description
        binding.tvVideosCount.text = item.contentDetails.itemCount.toString() + " video series"
        binding.layoutToolbarItems.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onItemClick(playlistItem: PlaylistsModel.Item) {
        /*setFragmentResult(
            KEY_ITEMS_TO_VIDEOS_FRAGMENT,
            bundleOf(KEY_SET_VIDEO_TO_VIDEOS_FRAGMENT to playlistItem)
        )*/
        findNavController().navigate(PlaylistItemsFragmentDirections.actionPlaylistItemsFragmentToVideoDetailFragment(playlistItem))
    }

}