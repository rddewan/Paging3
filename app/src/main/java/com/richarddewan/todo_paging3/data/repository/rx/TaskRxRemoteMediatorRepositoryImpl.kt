package com.richarddewan.todo_paging3.data.repository.rx

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.richarddewan.todo_paging3.data.local.db.DatabaseService
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.paging.rx.TaskRxRemoteMediator
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject
import javax.inject.Singleton


/*
created by Richard Dewan 10/04/2021
*/
@ExperimentalPagingApi
@Singleton
class TaskRxRemoteMediatorRepositoryImpl
@Inject constructor(
    private val taskRxRemoteMediator: TaskRxRemoteMediator,
    private val databaseService: DatabaseService,
) : TaskRxRemoteMediatorRepository {

    @ExperimentalCoroutinesApi
    override fun getTaskListPaging(): Flowable<PagingData<TaskEntity>> {
        return Pager(
            config = defaultPagingConfig(),
            remoteMediator = taskRxRemoteMediator,
            pagingSourceFactory = {databaseService.taskRxDao().getTasks()}
        ).flowable
    }

    private fun defaultPagingConfig(): PagingConfig {
        return  PagingConfig(
            //Mandatory, if your API has query string to show how many data will be shown use this to pass it to API
            pageSize = 10,
            //Prefetch distance which defines how far from the edge of loaded content an access must be to trigger further loading. Typically should be set several times the number of visible items onscreen
            prefetchDistance = 10,
            //Defines whether PagingData may display null placeholders if PagingSource provides them
            enablePlaceholders = true,
            //Defines the maximum number of items that may be loaded into PagingData before pages should be dropped. Value must be at least pageSize + (2 * prefetchDistance)
            maxSize = 30,
            //Default is pageSize * 3. Defines requested load size for initial load from PagingSource, typically larger than pageSize, so on first load data there’s a large enough range of content loaded to cover small scrolls.
            initialLoadSize = 10
        )
    }

}