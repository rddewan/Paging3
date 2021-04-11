package com.richarddewan.todo_paging3.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.richarddewan.todo_paging3.ui.base.BaseViewModel
import com.richarddewan.todo_paging3.util.livedata.SingleLiveEvent
import com.richarddewan.todo_paging3.util.network.NetworkConnectionHelper
import com.richarddewan.todo_paging3.util.rx.SchedulerProvider


/*
created by Richard Dewan 13/03/2021
*/

class HomeViewModel(
    networkConnectionHelper: NetworkConnectionHelper,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(networkConnectionHelper,schedulerProvider) {

    var count: MutableLiveData<Int> = MutableLiveData(0)
    private val _isNetworkAvailable: MutableLiveData<Boolean> = SingleLiveEvent()
    var isNetworkAvailable: LiveData<Boolean> = _isNetworkAvailable


    fun getUser() {
        _isNetworkAvailable.value = networkConnectionHelper.isNetworkConnected()
    }
}