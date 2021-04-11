package com.richarddewan.todo_paging3.data.paging.flow

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.richarddewan.todo_paging3.data.paging.mapper.PagingResponseMapper
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.remote.NetworkService
import com.richarddewan.todo_paging3.data.remote.response.TaskResponse
import javax.inject.Inject
import javax.inject.Singleton


/*
created by Richard Dewan 19/03/2021
*/

/*
Implement a PagingSource<Key, Value> to define a data source.
It takes a Key and Value as parameters. The Key is the index numbers for pages and,
Value is the type of data loaded.

load() is a suspend function.we can make our network or local database requests without blocking the main thread.
getRefreshKey() provides a Key for the initial load for the next Paging Source due to invalidation
of this PagingSource. The last accessed position can be retrieved via "state.anchorPosition"
so, we used the "state.anchorPosition".
 */

@Singleton
class TaskFlowPagingSource @Inject constructor(
    private val networkService: NetworkService
) : PagingSource<Int, TaskPaging.Task>(), PagingResponseMapper<TaskResponse, TaskPaging> {

    override fun getRefreshKey(state: PagingState<Int, TaskPaging.Task>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TaskPaging.Task> {
        val currentPage = params.key ?: 1

        return try {
            networkService.getTaskListFlowPaging(currentPage)
                .run {
                    val data = mapFromResponse(this)

                    return LoadResult.Page(
                        data = data.tasks,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (data.endOfPage) null else currentPage + 1
                    )
                }
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun mapFromResponse(response: TaskResponse): TaskPaging {
        return  with(response){
            TaskPaging(
                totalPage = lastPage,
                currentPage = currentPage,
                tasks = tasks.map {
                    TaskPaging.Task(
                        id = it.id,
                        userId = it.userId,
                        title = it.title,
                        body = it.body,
                        note = it.note,
                        status = it.status,
                        createdAt = it.createdAt,
                        updatedAt = it.updatedAt
                    )
                }
            )
        }
    }
}