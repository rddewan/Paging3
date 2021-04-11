package com.richarddewan.todo_paging3.data.paging.flow

import androidx.paging.*
import androidx.room.withTransaction
import com.richarddewan.todo_paging3.data.local.db.DatabaseService
import com.richarddewan.todo_paging3.data.local.entity.TaskKeyEntity
import com.richarddewan.todo_paging3.data.local.entity.EntityMapper
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.remote.response.TaskResponseMapper
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.remote.NetworkService
import com.richarddewan.todo_paging3.data.remote.response.TaskResponse
import java.io.InvalidObjectException
import javax.inject.Inject
import javax.inject.Singleton


/*
created by Richard Dewan 09/04/2021
*/

@ExperimentalPagingApi
@Singleton
class TaskFlowRemoteMediator @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) : RemoteMediator<Int, TaskEntity>(),
    TaskResponseMapper<TaskResponse, TaskPaging>,
    EntityMapper<TaskPaging.Task,TaskEntity> {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TaskEntity>
    ): MediatorResult {
        val page = when (loadType) {
            /**
             * [PagingData] content being refreshed, which can be a result of [PagingSource]
             * invalidation, refresh that may contain content updates, or the initial load.
             */
            LoadType.REFRESH -> {
                val keys = getKeyForTheClosestToCurrentPosition(state)

                keys?.nextKey?.minus(1) ?: 1
            }
            /**
             * Load at the start of a [PagingData].
             */
            LoadType.PREPEND -> {
                val keys = getKeyForTheFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val previousKey = keys.previousKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

                previousKey
            }
            /**
             * Load at the end of a [PagingData].
             */
            LoadType.APPEND -> {
                val keys = getKeyForTheLastItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Result is empty"))

                val nextKey =
                    keys.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)

                nextKey
            }
        }

        //get data from api
        try {
            val response = networkService.getTaskListFlowPaging(pageNumber = page)
            val data = mapFromResponse(response)

            databaseService.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    databaseService.taskFlowDao().clearTasks()
                    databaseService.taskKeyFlowDao().clearKeys()
                }

                val previousKey = if (page == 1)  null else page - 1
                val nextKey =  if (data.endOfPage) null else  page + 1
                val keys = data.tasks.map {
                    TaskKeyEntity(taskId = it.id.toLong(),previousKey = previousKey, nextKey = nextKey)
                }

                //insert to database
                databaseService.taskKeyFlowDao().insertMany(keys)
                databaseService.taskFlowDao().insertMany(mapToEntity(data.tasks))
            }

            return MediatorResult.Success(endOfPaginationReached = data.endOfPage)

        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }

    private suspend fun getKeyForTheFirstItem(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.pages.firstOrNull() {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { task ->
            databaseService.taskKeyFlowDao().getKeysByTaskId(task.taskId.toInt())
        }
    }

    private suspend fun getKeyForTheLastItem(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { task ->
            databaseService.taskKeyFlowDao().getKeysByTaskId(task.taskId.toInt())
        }
    }

    private suspend fun getKeyForTheClosestToCurrentPosition(state: PagingState<Int, TaskEntity>): TaskKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.taskId?.let { id ->
                databaseService.taskKeyFlowDao().getKeysByTaskId(id.toInt())
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

}