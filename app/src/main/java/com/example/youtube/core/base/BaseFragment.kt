package com.example.youtube.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected abstract val viewModel:VM
    /*protected val viewModel get() = _viewModel!!*/
    protected abstract fun inflateViewBinding(): VB

    /*protected abstract fun setViewModel():VM*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateViewBinding()
        /*_viewModel = setViewModel()*/
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkConnection()
        initData()
        initView()
        initRecyclerView()
        initListener()

    }
    open fun initView() {}
    open fun initRecyclerView() {}
    open fun initListener() {}
    open fun checkConnection() {}
    open fun initData() {}
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        /*_viewModel = null*/
    }
}