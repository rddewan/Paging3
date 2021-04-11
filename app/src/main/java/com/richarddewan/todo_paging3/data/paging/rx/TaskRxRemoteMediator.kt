package com.richarddewan.todo_paging3.data.paging.rx

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.richarddewan.todo_paging3.data.local.db.DatabaseService
import com.richarddewan.todo_paging3.data.local.entity.EntityMapper
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.local.entity.TaskKeyEntity
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.remote.NetworkService
import com.richarddewan.todo_paging3.data.remote.response.TaskResponse
import com.richarddewan.todo_paging3.data.remote.response.TaskResponseMapper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton


/*
created by Richard Dewan 10/04/2021
*/

@ExperimentalPagingApi
@Singleton
class TaskRxRemoteMediator @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val schedulerProvider: SchedulerProvider
) : RxRemoteMediator<Int, TaskEntity>(),
    TaskResponseMapper<TaskResponse, TaskPaging>,
    EntityMapper<TaskPaging.Task, TaskEntity> {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, TaskEntity>
    ): Single<MediatorResult> {

        return Single.just(loadType)
            .subscribeOn(schedulerProvider.io())
            .map {type ->
                when (type) {
                    /*
                    LoadType.REFRESH gets called when it's the first time we're loading data,
                    or when PagingDataAdapter.refresh() is called; so now the point of reference
                    for loading our data is the state.anchorPosition. If this is the first load,
                    then the anchorPosition is null. When PagingDataAdapter.refresh() is called,
                    the anchorPosition is the first visible position in the displayed list,
                    so we will need to load the page that contains that specific item.
                     */
                    LoadType.REFRESH -> {
                        val keys = getKeyForTheClosestToCurrentPosition(state)

                        keys?.nextKey?.minus(1) ?: 1
                    }
                    /*
                    When we need to load data at the beginning of the currently loaded data set,
                    the load parameter is LoadType.PREPEND. Based on the first item in the
                    database we need to compute the network page key.
                     */
                    LoadType.PREPEND -> {
                        val keys = getKeyForTheFirstItem(state)
                            ?: throw  InvalidObjectException("Result is empty")

                        keys.previousKey ?: INVALID_PAGE
                    }
                    /*
                    When we need to load data at the end of the currently loaded data set,
                    the load parameter is LoadType.APPEND. So now, based on the last item in the
                     database we need to compute the network page key.
                     */
                    LoadType.APPEND -> {
                        val keys = getKeyForTheLastItem(state)
                            ?: throw  InvalidObjectException("Result is empty")

                        keys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    networkService.getTaskListRxPaging(pageNumber = page)
                        .map { mapFromResponse(it) }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> {
                            MediatorResult.Success(endOfPaginationReached = it.endOfPage)
                        }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }.onErrorReturn { MediatorResult.Error(it) }
    }

    private fun insertToDb(page: Int, loadType: LoadType, data: TaskPaging): TaskPaging {

        try {
            //check if loadType enum is REFRESH or not. If yes, we clear the both table
            if (loadType == LoadType.REFRESH) {
                databaseService.taskKeyRxDao().clearTaskKeys()
                databaseService.taskRxDao().clearTasks()
            }

            val previousKey = if (page == 1 ) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.tasks.map {
                TaskKeyEntity(taskId = it.id.toLong(), previousKey = previousKey, nextKey = nextKey)
            }
            //insert to db
            databaseService.taskRxDao().insertMany(mapToEntity(data.tasks))
            databaseService.taskKeyRxDao().insertMany(keys)
        }
        catch (e: Exception) {
            Timber.e(e)
        }

        return data
    }

    private fun getKeyForTheFirstItem(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.pages.firstOrNull { //Get the first page that was retrieved, that contained items
            it.data.isNotEmpty()
        }?.data?.firstOrNull() // From that first page, get the first item
            ?.let { task ->
                // Get the remote keys of the first items retrieved
                databaseService.taskKeyRxDao().getTaskKeysByTaskId(task.taskId.toInt())
            }
    }

    private fun getKeyForTheLastItem(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()// From that last page, get the first item
            ?.let { task ->
                // Get the remote keys of the last items retrieved
                databaseService.taskKeyRxDao().getTaskKeysByTaskId(task.taskId.toInt())
            }
    }

    private fun getKeyForTheClosestToCurrentPosition(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.anchorPosition?.let { position ->
            // Get the item closest to the anchor position
            state.closestItemToPosition(position)?.taskId?.let {
                databaseService.taskKeyRxDao().getTaskKeysByTaskId(it.toInt())
            }
        }

    }

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

    override fun mapToEntity(model: List<TaskPaging.Task>): List<TaskEntity> {
        return model.map {
            TaskEntity(
                taskId = it.id.toLong(),
                userId = it.userId,
                title = it.title,
                body = it.body,
                note = it.note,
                status = it.status
            )
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}