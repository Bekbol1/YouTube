package com.example.youtube.presentation.videodetail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.youtube.R
import com.example.youtube.core.base.BaseFragment
import com.example.youtube.data.model.PlaylistsModel
import com.example.youtube.databinding.FragmentVideoDetailBinding
import com.example.youtube.utils.InternetConnectionService
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import org.koin.androidx.viewmodel.ext.android.viewModel


class VideoDetailFragment : BaseFragment<FragmentVideoDetailBinding,VideoDetailViewModel>() {
    override val viewModel: VideoDetailViewModel by viewModel()
    override fun inflateViewBinding(): FragmentVideoDetailBinding = FragmentVideoDetailBinding.inflate(layoutInflater)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLiveData()
        lifecycle.addObserver(binding.youtubePlayerView)
    }
    override fun checkConnection() {
        super.checkConnection()
        InternetConnectionService(requireContext()).observe(viewLifecycleOwner) { isConnect ->
            if (!isConnect) {
                binding.mainContainer.visibility = View.GONE
                binding.containerInclude.visibility = View.VISIBLE
            }
            binding.layoutNoConnection.btnCheckInternetTryAgain.setOnClickListener {
                if (isConnect) {
                    binding.mainContainer.visibility = View.VISIBLE
                    binding.containerInclude.visibility = View.GONE
                } else{
                    Toast.makeText(requireContext(), getString(R.string.no_connection_try_again), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        arguments?.let {
            val video:PlaylistsModel.Item = VideoDetailFragmentArgs.fromBundle(it).videoDetail
            viewModel.getVideosDetails(video.contentDetails.videoId!!)
        }
    }

    /*private fun initResultListener(){
        setFragmentResultListener(KEY_ITEMS_TO_VIDEOS_FRAGMENT){ _, bundle ->
            bundle.getSerializable(KEY_SET_VIDEO_TO_VIDEOS_FRAGMENT)?.let { model ->
                val _model = model as PlaylistsModel.Item
                _model.contentDetails.videoId?.let { initView(it) }//
                //Log.e("denn", "initResultListener: ${_model.contentDetails.videoId}", )
            }
        }
    }*/

    private fun initLiveData() {
        viewModel.videosDetails.observe(viewLifecycleOwner){item ->
            binding.tvNameVideo.text = item.items.first().snippet.title
            binding.tvDescVideo.text = item.items.first().snippet.description
            //binding.ivVideoImg.load(item.items.first().snippet.thumbnails.standard.url)
            binding.youtubePlayerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback{
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    val videoId = item.items.first().id
                    //Log.e("denn", "onYouTubePlayer: ${ item.items.first().id}", )
                    youTubePlayer.loadVideo(videoId,0f)
                }

            })

        }
        viewModel.loading.observe(viewLifecycleOwner){isLoading->
            if (isLoading) binding.pbLoading.visibility = View.VISIBLE
            else binding.pbLoading.visibility = View.GONE
        }
        viewModel.error.observe(viewLifecycleOwner){error ->
            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun initListener() {
        super.initListener()
        binding.toolbar.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnDownloadVideo.setOnClickListener {
            binding.containerDownload.visibility = View.VISIBLE
        }
        binding.layoutDownload.btnDownload.setOnClickListener {
            binding.containerDownload.visibility = View.GONE
        }
    }

}