package com.richarddewan.todo_paging3.di.module

import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRepositoryImpl
import com.richarddewan.todo_paging3.ui.base.BaseFragment
import com.richarddewan.todo_paging3.ui.fragment.flow.FlowViewModel
import com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator.FlowRemoteMediatorViewModel
import com.richarddewan.todo_paging3.ui.fragment.home.HomeViewModel
import com.richarddewan.todo_paging3.ui.fragment.rxjava.RxJavaViewModel
import com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator.RxJavaRemoteMediatorViewModel
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import com.richarddewan.todo_paging3.util.viewmodel.ViewModelProviderFactory
import dagger.Module
import dagger.Provides


/*
created by Richard Dewan 13/03/2021
*/

@Module
class FragmentModule(private val fragment: BaseFragment<*, *>) {

    @Provides
    fun provideContext() = fragment.context

    @Provides
    fun provideHomeViewModel(
        networkConnectionHelper: NetworkConnectionHelper,
        schedulerProvider: SchedulerProvider
    ): HomeViewModel = ViewModelProvider(
        fragment.requireActivity(),
        ViewModelProviderFactory(HomeViewModel::class) {
            HomeViewModel(networkConnectionHelper, schedulerProvider)
        }).get(HomeViewModel::class.java)

    @Provides
    fun provideRxJavViewModel(
        networkConnectionHelper: NetworkConnectionHelper,
        schedulerProvider: SchedulerProvider,
        repositoryImpl: TaskRxRepositoryImpl
    ): RxJavaViewModel = ViewModelProvider(
        fragment,
        ViewModelProviderFactory(RxJavaViewModel::class) {
            RxJavaViewModel(networkConnectionHelper, schedulerProvider, repositoryImpl)
        }
    ).get(RxJavaViewModel::class.java)

    @Provides
    fun provideFlowViewModel(
        networkConnectionHelper: NetworkConnectionHelper,
        schedulerProvider: SchedulerProvider,
        taskFlowRepositoryImpl: TaskFlowRepositoryImpl
    ): FlowViewModel =
        ViewModelProvider(fragment, ViewModelProviderFactory(FlowViewModel::class) {
            FlowViewModel(networkConnectionHelper, schedulerProvider, taskFlowRepositoryImpl)
        }).get(FlowViewModel::class.java)

    @Provides
    fun provideFlowRemoteMediatorViewModel(
        networkConnectionHelper: NetworkConnectionHelper,
        schedulerProvider: SchedulerProvider,
        repository: TaskFlowRemoteMediatorRepositoryImpl
    ): FlowRemoteMediatorViewModel =
        ViewModelProvider(
            fragment,
            ViewModelProviderFactory(FlowRemoteMediatorViewModel::class) {
                FlowRemoteMediatorViewModel(networkConnectionHelper, schedulerProvider, repository)
            }).get(FlowRemoteMediatorViewModel::class.java)

    @ExperimentalPagingApi
    @Provides
    fun provide(
        networkConnectionHelper: NetworkConnectionHelper,
        schedulerProvider: SchedulerProvider,
        repository: TaskRxRemoteMediatorRepositoryImpl
    ): RxJavaRemoteMediatorViewModel =
        ViewModelProvider(fragment, ViewModelProviderFactory(RxJavaRemoteMediatorViewModel::class) {
            RxJavaRemoteMediatorViewModel(networkConnectionHelper, schedulerProvider,repository)
        }).get(RxJavaRemoteMediatorViewModel::class.java)

}