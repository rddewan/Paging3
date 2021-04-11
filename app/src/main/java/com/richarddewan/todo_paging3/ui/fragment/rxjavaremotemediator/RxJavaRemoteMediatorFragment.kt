package com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator

import android.view.View
import androidx.core.view.isVisible
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.richarddewan.todo_paging3.databinding.FragmentFlowRmBinding
import com.richarddewan.todo_paging3.databinding.FragmentRxjavaRmBinding
import com.richarddewan.todo_paging3.di.component.FragmentComponent
import com.richarddewan.todo_paging3.ui.base.BaseFragment
import com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.adaptor.FlowTaskRemoteMediatorUiModelAdaptor
import com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.adaptor.RxRemoteMediatorLoadStateAdaptor
import com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.adaptor.RxTaskRemoteMediatorPagingAdaptor
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi


/*
created by Richard Dewan 09/04/2021
*/

@ExperimentalPagingApi
class RxJavaRemoteMediatorFragment :
    BaseFragment<RxJavaRemoteMediatorViewModel, FragmentRxjavaRmBinding>() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var remoteMediatorPagingAdaptor: RxTaskRemoteMediatorPagingAdaptor
    private lateinit var remoteMediatorUiModelAdaptor: FlowTaskRemoteMediatorUiModelAdaptor
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun provideLayout(): ViewBinding = FragmentRxjavaRmBinding.inflate(layoutInflater)

    override fun setupView(view: View) {
        remoteMediatorPagingAdaptor = RxTaskRemoteMediatorPagingAdaptor()
        remoteMediatorUiModelAdaptor = FlowTaskRemoteMediatorUiModelAdaptor()

        binding.rvRxRemoteMediator.apply {
            layoutManager = linearLayoutManager
            adapter = remoteMediatorUiModelAdaptor
        }

        binding.rvRxRemoteMediator.adapter =
            remoteMediatorUiModelAdaptor.withLoadStateHeaderAndFooter(
                header = RxRemoteMediatorLoadStateAdaptor {remoteMediatorUiModelAdaptor.retry()},
                footer = RxRemoteMediatorLoadStateAdaptor {remoteMediatorUiModelAdaptor.retry()},
            )

        //adapter load state
        remoteMediatorUiModelAdaptor.addLoadStateListener { loadStates ->
            // Only show the list if refresh succeeds.
            /* binding.rvFlowPagingSourceRemoteMediator.isVisible =
                 loadStates.source.refresh is LoadState.NotLoading *///(is) type checking
            // Show loading spinner during initial load or refresh.
            binding.rxRemoteMediatorProgressBar.isVisible =
                loadStates.source.refresh is LoadState.Loading

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

    @ExperimentalCoroutinesApi
    override fun setupObservers() {
        compositeDisposable.add(
            viewModel.getTaskListPagingUiModel()
                .subscribe {
                    remoteMediatorUiModelAdaptor.submitData(viewLifecycleOwner.lifecycle, it)
                }
        )

    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}