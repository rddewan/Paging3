package com.richarddewan.todo_paging3.ui.fragment.flowtremotemediator

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.repository.flow.TaskFlowRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.ui.base.BaseViewModel
import com.richarddewan.todo_paging3.ui.model.UiModel
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/*
created by Richard Dewan 09/04/2021
*/

class FlowRemoteMediatorViewModel(
    networkConnectionHelper: NetworkConnectionHelper,
    schedulerProvider: SchedulerProvider,
    private val repository: TaskFlowRemoteMediatorRepositoryImpl
) : BaseViewModel(networkConnectionHelper, schedulerProvider) {


    @ExperimentalPagingApi
    fun getTaskListPaging(): Flow<PagingData<TaskEntity>> =
        repository.getTaskListPagingRemoteMediator()
            .cachedIn(viewModelScope)


    @ExperimentalPagingApi
    fun getTaskListPagingUiModel(): Flow<PagingData<UiModel>> =
        repository.getTaskListPagingRemoteMediator()
            .map { pagingDat -> pagingDat.map { UiModel.TaskItem(it) } }
            .map {
                it.insertSeparators<UiModel.TaskItem, UiModel> { before, after ->
                    if (after == null) {
                        // we're at the end of the list
                        return@insertSeparators null
                    }

                    val alphabet = after.task.status.trim().take(1)

                    if (before == null) {
                        // we're at the beginning of the list
                        return@insertSeparators UiModel
                            .SeparatorItem("$alphabet : ${after.task.status}")
                    }

                    if (after.task.status == "STARTED"
                        || after.task.status == "PENDING"
                        || after.task.status == "COMPLETED"
                        || after.task.status == "bbb"
                    ) {
                        UiModel.SeparatorItem("$alphabet : ${after.task.status}")
                    } else {
                        null
                    }
                }
            }
            .cachedIn(viewModelScope)
}