package com.richarddewan.todo_paging3.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider
import com.richarddewan.todo_paging3.util.livedata.SingleLiveEvent


/*
created by Richard Dewan 12/03/2021
*/

abstract class BaseViewModel(
    protected val networkConnectionHelper: NetworkConnectionHelper,
    protected val schedulerProvider: SchedulerProvider
) : ViewModel() {

    val isNetworkConnection : MutableLiveData<Boolean> = SingleLiveEvent()

    fun checkNetworkConnection() {
        isNetworkConnection.value = networkConnectionHelper.isNetworkConnected()
    }
}