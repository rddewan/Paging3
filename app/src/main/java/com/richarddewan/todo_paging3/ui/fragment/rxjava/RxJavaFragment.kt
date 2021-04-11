package com.richarddewan.todo_paging3.ui.fragment.rxjava

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.richarddewan.todo_paging3.databinding.FragmentRxjavaBinding
import com.richarddewan.todo_paging3.di.component.FragmentComponent
import com.richarddewan.todo_paging3.ui.base.BaseFragment
import com.richarddewan.todo_paging3.ui.fragment.rxjava.adaptor.RxTaskLoadStateAdaptor
import com.richarddewan.todo_paging3.ui.fragment.rxjava.adaptor.RxTaskPagingAdaptor


/*
created by Richard Dewan 12/03/2021
*/

class RxJavaFragment : BaseFragment<RxJavaViewModel,FragmentRxjavaBinding>() {

    private lateinit var taskPagingAdaptor: RxTaskPagingAdaptor
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    override fun provideLayout(): ViewBinding = FragmentRxjavaBinding.inflate(layoutInflater)

    override fun setupView(view: View) {
        //paging adaptor
        taskPagingAdaptor = RxTaskPagingAdaptor()
        //setup recyclerView
        binding.rvRxPagingSourceTask.apply {
            layoutManager = linearLayoutManager
            adapter = taskPagingAdaptor
        }

        binding.rvRxPagingSourceTask.adapter = taskPagingAdaptor.withLoadStateHeaderAndFooter(
            header = RxTaskLoadStateAdaptor {taskPagingAdaptor.retry()},
            footer = RxTaskLoadStateAdaptor {taskPagingAdaptor.retry()}
        )

    }

    override fun setupObservers() {

        viewModel.getTaskListPaging()
            .subscribe { pagingData->
                taskPagingAdaptor.submitData(viewLifecycleOwner.lifecycle,pagingData)
            }
    }

    override fun injectDependencies(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }
}