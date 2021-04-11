package com.richarddewan.todo_paging3.data.paging.rx


import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.richarddewan.todo_paging3.data.paging.mapper.PagingResponseMapper
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.remote.NetworkService
import com.richarddewan.todo_paging3.data.remote.response.TaskResponse
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject
import javax.inject.Singleton


/*
created by Richard Dewan 17/03/2021
*/

/*
Implement a PagingSource<Key, Value> to define a data source.
It takes a Key and Value as parameters. The Key is the index numbers for pages and,
Value is the type of data loaded.
 */
@Singleton
class TaskRxPagingSource @Inject constructor(
    private val networkService: NetworkService,
    private val schedulerProvider: SchedulerProvider
) : RxPagingSource<Int,TaskPaging.Task>(), PagingResponseMapper<TaskResponse,TaskPaging>{

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, TaskPaging.Task>> {
        val currentPage = params.key ?: 1
        return networkService.getTaskListRxPaging(pageNumber = currentPage)
            .subscribeOn(schedulerProvider.io())
            .map { mapFromResponse(it) }
            .map { loadResult(data = it,currentPage = currentPage ) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    override fun getRefreshKey(state: PagingState<Int, TaskPaging.Task>): Int? {
        return  state.anchorPosition
    }

    private fun loadResult(data: TaskPaging, currentPage: Int): LoadResult<Int, TaskPaging.Task> =
        LoadResult.Page(
            data = data.tasks,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = if (data.endOfPage) null else  currentPage + 1
        )

    override fun mapFromResponse(response: TaskResponse): TaskPaging {
        return with(response) {
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