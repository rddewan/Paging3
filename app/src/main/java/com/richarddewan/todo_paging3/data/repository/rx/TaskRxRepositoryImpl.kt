package com.richarddewan.todo_paging3.data.repository.rx

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.richarddewan.todo_paging3.data.paging.rx.TaskRxPagingSource
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.remote.NetworkService
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject


/*
created by Richard Dewan 17/03/2021
*/

class TaskRxRepositoryImpl @Inject constructor(
    private val taskPagingSource: TaskRxPagingSource
) {

    fun getTaskListPaging(): Flowable<PagingData<TaskPaging.Task>> {
        return Pager(
            config = defaultPagingConfig(),
            pagingSourceFactory = {taskPagingSource}
        ).flowable
    }

    private fun defaultPagingConfig(): PagingConfig {
        return  PagingConfig(
            //Mandatory, if your API has query string to show how many data will be shown use this to pass it to API
            pageSize = 10,
            //Prefetch distance which defines how far from the edge of loaded content an access must be to trigger further loading. Typically should be set several times the number of visible items onscreen
            prefetchDistance = 3,
            //Defines whether PagingData may display null placeholders if PagingSource provides them
            enablePlaceholders = true,
            //Defines the maximum number of items that may be loaded into PagingData before pages should be dropped. Value must be at least pageSize + (2 * prefetchDistance)
            maxSize = 26,
            //Default is pageSize * 3. Defines requested load size for initial load from PagingSource, typically larger than pageSize, so on first load data there’s a large enough range of content loaded to cover small scrolls.
            initialLoadSize = 30
        )
    }
}