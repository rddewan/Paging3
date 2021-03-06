package com.richarddewan.todo_paging3.data.repository.flow

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.richarddewan.todo_paging3.data.paging.flow.TaskFlowPagingSource
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


/*
created by Richard Dewan 19/03/2021
*/

@Singleton
class TaskFlowRepositoryImpl @Inject constructor(
    private val taskFlowPagingSource: TaskFlowPagingSource
) : TaskFlowRepository {

    override  fun getTaskListPaging(): Flow<PagingData<TaskPaging.Task>> {
        return Pager(
            config = pagingConfig(),
            pagingSourceFactory = {taskFlowPagingSource}
        ).flow
    }

    private fun pagingConfig() : PagingConfig {
        return PagingConfig(
            //Mandatory, if your API has query string to show how many data will be shown use this to pass it to API
            pageSize = 10,
            //Prefetch distance which defines how far from the edge of loaded content an access must be to trigger further loading. Typically should be set several times the number of visible items onscreen
            prefetchDistance = 20,
            //Default is pageSize * 3. Defines requested load size for initial load from PagingSource, typically larger than pageSize, so on first load data there’s a large enough range of content loaded to cover small scrolls.
            initialLoadSize = 30,
            //Defines the maximum number of items that may be loaded into PagingData before pages should be dropped. Value must be at least pageSize + (2 * prefetchDistance)
            maxSize = 50,
            //Defines whether PagingData may display null placeholders if PagingSource provides them
            enablePlaceholders = false
        )
    }
}