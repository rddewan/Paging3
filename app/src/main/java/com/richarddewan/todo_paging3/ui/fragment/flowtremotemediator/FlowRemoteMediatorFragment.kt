package com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.richarddewan.todo_paging3.databinding.FragmentFlowRmBinding
import com.richarddewan.todo_paging3.di.component.FragmentComponent
import com.richarddewan.todo_paging3.ui.base.BaseFragment
import com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor.FlowTaskRemoteMediatorAdaptor
import com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor.FlowTaskRemoteMediatorLoadStateAdaptor
import com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor.FlowTaskRemoteMediatorUiModelAdaptor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/*
created by Richard Dewan 09/04/2021
*/

class FlowRemoteMediatorFragment :
    BaseFragment<FlowRemoteMediatorViewModel, FragmentFlowRmBinding>() {

    private lateinit var remoteMediatorAdaptor: FlowTaskRemoteMediatorAdaptor
    private lateinit var remoteMediatorUiModelAdaptor: FlowTaskRemoteMediatorUiModelAdaptor
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun provideLayout(): ViewBinding = FragmentFlowRmBinding.inflate(layoutInflater)

    override fun setupView(view: View) {

        remoteMediatorAdaptor = FlowTaskRemoteMediatorAdaptor()
        remoteMediatorUiModelAdaptor = FlowTaskRemoteMediatorUiModelAdaptor()

        binding.rvFlowPagingSourceRemoteMediator.apply {
            layoutManager = linearLayoutManager
            adapter = remoteMediatorUiModelAdaptor
        }

        //display header and footer load state on recycler view
        binding.rvFlowPagingSourceRemoteMediator.adapter =
            remoteMediatorUiModelAdaptor.withLoadStateHeaderAndFooter(
                footer = FlowTaskRemoteMediatorLoadStateAdaptor{remoteMediatorUiModelAdaptor.retry()},
                header = FlowTaskRemoteMediatorLoadStateAdaptor{remoteMediatorUiModelAdaptor.retry()}
            )

        //adapter load state
        remoteMediatorUiModelAdaptor.addLoadStateListener { loadStates ->
            // Only show the list if refresh succeeds.
           /* binding.rvFlowPagingSourceRemoteMediator.isVisible =
                loadStates.source.refresh is LoadState.NotLoading *///(is) type checking
            // Show loading spinner during initial load or refresh.
            binding.flowRemoteMediatorProgressBar.isVisible =
                loadStates.source.refresh is LoadState.Loading //(is) type checking

            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = //as is type casting if cant cast return null
                loadStates.source.refresh as? LoadState.Error // PagingSource
                    ?: loadStates.source.append as? LoadState.Error // PagingSource
                    ?: loadStates.source.prepend as? LoadState.Error // PagingSource
                    ?: loadStates.refresh as? LoadState.Error // RemoteMediator
                    ?: loadStates.append as? LoadState.Error // RemoteMediator
                    ?: loadStates.prepend as? LoadState.Error // RemoteMediator

            errorState?.let {
                showErrorSnackBar(it.error.message.toString())
            }
        }

    }

    @ExperimentalPagingApi
    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.getTaskListPagingUiModel()
                .collectLatest {
                    remoteMediatorUiModelAdaptor.submitData(it)
                }
        }

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}