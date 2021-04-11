package com.richarddewan.todo_paging3.ui.fragment.flow


import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.richarddewan.todo_paging3.databinding.FragmentFlowBinding
import com.richarddewan.todo_paging3.di.component.FragmentComponent
import com.richarddewan.todo_paging3.ui.base.BaseFragment
import com.richarddewan.todo_paging3.ui.fragment.flow.adaptor.FlowTaskLoadStateAdaptor
import com.richarddewan.todo_paging3.ui.fragment.flow.adaptor.FlowTaskPagingAdaptor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


/*
created by Richard Dewan 12/03/2021
*/

class FlowFragment : BaseFragment<FlowViewModel, FragmentFlowBinding>() {

    private lateinit var flowTaskPagingAdaptor: FlowTaskPagingAdaptor
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun provideLayout(): ViewBinding = FragmentFlowBinding.inflate(layoutInflater)

    override fun setupView(view: View) {
        //paging adaptor
        flowTaskPagingAdaptor = FlowTaskPagingAdaptor()

        //setup recyclerView
        binding.rvFlowPagingSourceTask.apply {
            layoutManager = linearLayoutManager
            adapter = flowTaskPagingAdaptor
        }

        //display header and footer load state
        binding.rvFlowPagingSourceTask.adapter = flowTaskPagingAdaptor
            .withLoadStateHeaderAndFooter(
                header = FlowTaskLoadStateAdaptor { flowTaskPagingAdaptor.retry() },
                footer = FlowTaskLoadStateAdaptor { flowTaskPagingAdaptor.retry() }
            )

        flowTaskPagingAdaptor.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.rvFlowPagingSourceTask.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.flowProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.source.refresh as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error

            errorState?.let {
                showErrorSnackBar(it.error.message.toString())
            }
        }
    }

    override fun setupObservers() {
        lifecycleScope.launch {
            viewModel.getTaskListPaging()
                .collectLatest {
                    flowTaskPagingAdaptor.submitData(it)
                }
        }
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }


}