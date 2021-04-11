package com.richarddewan.todo_paging3.di.component

import androidx.paging.ExperimentalPagingApi
import com.richarddewan.todo_paging3.BaseApplication
import com.richarddewan.todo_paging3.data.local.db.DatabaseService
import com.richarddewan.todo_paging3.data.paging.flow.TaskFlowPagingSource
import com.richarddewan.todo_paging3.data.paging.flow.TaskFlowRemoteMediator
import com.richarddewan.todo_paging3.data.paging.rx.TaskRxPagingSource
import com.richarddewan.todo_paging3.data.paging.rx.TaskRxRemoteMediator
import com.richarddewan.todo_paging3.data.remote.NetworkService
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRepositoryImpl
import com.richarddewan.todo_paging3.di.module.ApplicationModule
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import dagger.Component
import javax.inject.Singleton


/*
created by Richard Dewan 15/03/2021
*/

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    /*
    we need to write a function which takes the class that need dependencies
     */
    fun inject(application: BaseApplication)

    /*
    share the instance between other component
     */
    fun getNetworkConnectionHelper(): NetworkConnectionHelper

    fun getScheduleProvider(): SchedulerProvider

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getTaskPagingSource(): TaskRxPagingSource

    fun getTaskFlowPagingSource(): TaskFlowPagingSource

    @ExperimentalPagingApi
    fun getTaskFlowRemoteMediator(): TaskFlowRemoteMediator

    fun getTaskRepositoryImpl(): TaskRxRepositoryImpl

    fun getTaskFlowRepositoryImpl(): TaskFlowRepositoryImpl

    fun getTaskFlowRemoteMediatorRepositoryImpl(): TaskFlowRemoteMediatorRepositoryImpl

    @ExperimentalPagingApi
    fun getTaskRxRemoteMediator(): TaskRxRemoteMediator

    @ExperimentalPagingApi
    fun getTaskRxRemoteMediatorRepositoryImpl(): TaskRxRemoteMediatorRepositoryImpl

}