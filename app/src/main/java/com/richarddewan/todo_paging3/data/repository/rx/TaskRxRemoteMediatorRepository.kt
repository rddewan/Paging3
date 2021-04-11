package com.richarddewan.todo_paging3.data.repository.rx

import androidx.paging.PagingData
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import io.reactivex.rxjava3.core.Flowable


/*
created by Richard Dewan 17/03/2021
*/

interface TaskRxRemoteMediatorRepository {

    fun getTaskListPaging(): Flowable<PagingData<TaskEntity>>
}