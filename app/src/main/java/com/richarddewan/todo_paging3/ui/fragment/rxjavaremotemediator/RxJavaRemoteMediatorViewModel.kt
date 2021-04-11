package com.richarddewan.todo_paging3.ui.fragment.rxjavaremotemediator

import androidx.lifecycle.viewModelScope
import androidx.paging.*
import androidx.paging.rxjava3.cachedIn
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRemoteMediatorRepositoryImpl
import com.richarddewan.todo_paging3.ui.base.BaseViewModel
import com.richarddewan.todo_paging3.ui.model.UiModel
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/*
created by Richard Dewan 09/04/2021
*/

@ExperimentalPagingApi
class RxJavaRemoteMediatorViewModel  constructor(
    networkConnectionHelper: NetworkConnectionHelper,
    schedulerProvider: SchedulerProvider,
    private val repository: TaskRxRemoteMediatorRepositoryImpl
) : BaseViewModel(networkConnectionHelper, schedulerProvider) {


    @ExperimentalCoroutinesApi
    fun getTaskListPaging(): Flowable<PagingData<TaskEntity>> {
        return  repository.getTaskListPaging()
            .cachedIn(viewModelScope)
    }

    @ExperimentalCoroutinesApi
    fun getTaskListPagingUiModel(): Flowable<PagingData<UiModel>> =
        repository.getTaskListPaging()
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

                    if (before.task.status == "STARTED"
                        || before.task.status == "PENDING"
                        || before.task.status == "COMPLETED"
                        || before.task.status == "bbb"
                    ) {
                        UiModel.SeparatorItem("$alphabet : ${after.task.status}")
                    } else {
                        null
                    }
                }
            }
            .cachedIn(viewModelScope)
}