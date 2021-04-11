package com.richarddewan.todo_paging3.data.remote

import com.richarddewan.todo_paging3.data.remote.response.TaskResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton


/*
created by Richard Dewan 16/03/2021
*/

@Singleton
interface NetworkService {

    @Headers(Network.HEADER_ACCEPT)
    @GET(Endpoint.GET_TASK_LIST)
    fun getTaskListRxPaging(@Query("page") pageNumber: Int): Single<TaskResponse>

    @Headers(Network.HEADER_ACCEPT)
    @GET(Endpoint.GET_TASK_LIST)
     suspend fun getTaskListFlowPaging(@Query("page") pageNumber: Int): TaskResponse

}