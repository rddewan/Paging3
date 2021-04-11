package com.richarddewan.todo_paging3.data.repository.flow

import androidx.paging.PagingData
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import kotlinx.coroutines.flow.Flow


/*
created by Richard Dewan 19/03/2021
*/

interface TaskFlowRepository {

    fun getTaskListPaging(): Flow<PagingData<TaskPaging.Task>>
}