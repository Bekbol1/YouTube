package com.example.youtube.presentation.playlists


import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube.R
import com.example.youtube.core.base.BaseFragment
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.FragmentPlaylistsBinding
import com.example.youtube.presentation.playlists.playlists_load_state.PlaylistsLoadStateAdapter
import com.example.youtube.utils.InternetConnectionService
import com.example.youtube.utils.PlaylistModelComparator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlaylistsFragment : BaseFragment<FragmentPlaylistsBinding, PlaylistsViewModel>() {
    override val viewModel: PlaylistsViewModel by viewModel()

    override fun inflateViewBinding(): FragmentPlaylistsBinding =
        FragmentPlaylistsBinding.inflate(layoutInflater)

    private val adapter = PlaylistsAdapter(PlaylistModelComparator,this::onClickItem)


    /*private val internetConnectionService = InternetConnectionService(requireContext())*/
    // нельзя задавать requireContext переменной пустой переменной

    override fun checkConnection() {
        super.checkConnection()
        InternetConnectionService(requireContext()).observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.rvPlaylists.visibility = View.GONE
                binding.containerInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnCheckInternetTryAgain.setOnClickListener {
                if (isConnect) {
                    binding.rvPlaylists.visibility = View.VISIBLE
                    binding.containerInclude.visibility = View.GONE
                } else{
                    Toast.makeText(requireContext(), getString(R.string.no_connection_try_again), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initView() {
        super.initView()
        viewModel.getPagingPlaylists().observe(viewLifecycleOwner) { pagingData ->
            binding.rvPlaylists.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PlaylistsLoadStateAdapter(),
                footer = PlaylistsLoadStateAdapter()
            )
            adapter.submitData(lifecycle = lifecycle, pagingData = pagingData)
            binding.pbLoading.visibility = View.GONE
        }
        /*viewModel.getPagingPlaylists().observe(viewLifecycleOwner) {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                binding.rvPlaylists.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = PlaylistsLoadStateAdapter(),
                    footer = PlaylistsLoadStateAdapter()
                )
                adapter.submitData(
                    lifecycle = lifecycle,
                    pagingData = it)

                binding.pbLoading.visibility = View.GONE
            }
        }*/
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.pbLoading.visibility = View.VISIBLE
            }
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickItem(playlistItem: PlaylistsModel.Item) {
        /*setFragmentResult(
            Constants.KEY_PLAYLIST_TO_PLAYLIST_ITEMS,
            bundleOf(Constants.KEY_SET_VIDEO_TO_FRAGMENT_ITEMS to playlistItem)
        )
        findNavController().navigate(R.id.playlistItemsFragment)*/
        findNavController().navigate(PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistItemsFragment(playlistItem))
    }

}