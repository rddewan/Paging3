package com.richarddewan.todo_paging3.ui.fragment.rxjava

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.richarddewan.todo_paging3.data.paging.model.TaskPaging
import com.richarddewan.todo_paging3.data.repository.rx.TaskRxRepositoryImpl
import com.richarddewan.todo_paging3.ui.base.BaseViewModel
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import io.reactivex.rxjava3.core.Flowable


/*
created by Richard Dewan 14/03/2021
*/

class RxJavaViewModel(
    networkConnectionHelper: NetworkConnectionHelper,
    schedulerProvider: SchedulerProvider,
    private val repository: TaskRxRepositoryImpl
): BaseViewModel(networkConnectionHelper,schedulerProvider) {


    fun getTaskListPaging(): Flowable<PagingData<TaskPaging.Task>> {
        return repository.getTaskListPaging()
            .cachedIn(viewModelScope)
    }

}