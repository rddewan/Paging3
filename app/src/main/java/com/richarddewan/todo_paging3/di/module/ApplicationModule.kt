package com.richarddewan.todo_paging3.di.module

import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.richarddewan.todo_paging3.BaseApplication
import com.richarddewan.todo_paging3.data.local.db.DatabaseService
import com.richarddewan.todo_paging3.data.paging.flow.TaskFlowPagingSource
import com.richarddewan.todo_paging3.data.paging.flow.TaskFlowRemoteMediator
import com.richarddewan.todo_paging3.data.paging.rx.TaskRxPagingSource
import com.richarddewan.todo_paging3.data.paging.rx.TaskRxRemoteMediator
import com.richarddewan.todo_paging3.data.remote.Network
import com.richarddewan.todo_paging3.data.remote.NetworkService
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRepositoryImpl
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.RxSchedulerProvider
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/*
created by Richard Dewan 15/03/2021
*/

@Module
class ApplicationModule(private val application: BaseApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext() = application

    @Provides
    @Singleton
    fun provideNetworkConnectionHelper() = NetworkConnectionHelper(application)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideTaskPagingSource(
        networkService: NetworkService,
        schedulerProvider: SchedulerProvider
    ): TaskRxPagingSource = TaskRxPagingSource(
        networkService,
        schedulerProvider
    )

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskPagingSource: TaskRxPagingSource
    ): TaskRxRepositoryImpl = TaskRxRepositoryImpl(taskPagingSource)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideTaskFlowRemoteMediatorRepository(
        databaseService: DatabaseService,
        taskFlowRemoteMediator: TaskFlowRemoteMediator
    ): TaskFlowRemoteMediatorRepositoryImpl =
        TaskFlowRemoteMediatorRepositoryImpl(databaseService, taskFlowRemoteMediator)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideTaskRxRemoteMediator(
        networkService: NetworkService,
        databaseService: DatabaseService,
        schedulerProvider: SchedulerProvider
    ) : TaskRxRemoteMediator =
        TaskRxRemoteMediator(networkService, databaseService, schedulerProvider)

    @Provides
    @Singleton
    fun provideTaskFlowRepository(taskFlowPagingSource: TaskFlowPagingSource):
            TaskFlowRepositoryImpl = TaskFlowRepositoryImpl(taskFlowPagingSource)

    @ExperimentalPagingApi
    @Provides
    @Singleton
    fun provideTaskRxRemoteMediatorRepositoryImpl(
        taskRxRemoteMediator: TaskRxRemoteMediator,
        databaseService: DatabaseService
    ) : TaskRxRemoteMediatorRepositoryImpl =
        TaskRxRemoteMediatorRepositoryImpl(taskRxRemoteMediator, databaseService)


    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Network.create(
            "https://freeapi.rdewan.dev/",
            application.cacheDir,
            10 * 1024 * 1024
        )

    @Provides
    @Singleton
    fun provideDatabaseService(): DatabaseService =
        Room.databaseBuilder(application, DatabaseService::class.java, "task.db").build()

}