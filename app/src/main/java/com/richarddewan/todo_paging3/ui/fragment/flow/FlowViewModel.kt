package com.richarddewan.todo_paging3.ui.fragment.flow

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRepository
import com.richarddewan.todo_paging3.ui.base.BaseViewModel
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import kotlinx.coroutines.flow.Flow


/*
created by Richard Dewan 14/03/2021
*/

class FlowViewModel(
    networkConnectionHelper: NetworkConnectionHelper,
    schedulerProvider: SchedulerProvider,
    private val repository: TaskFlowRepository
) : BaseViewModel(networkConnectionHelper, schedulerProvider) {


    fun getTaskListPaging(): Flow<PagingData<TaskPaging.Task>> =
        repository.getTaskListPaging()
            .cachedIn(viewModelScope)


}