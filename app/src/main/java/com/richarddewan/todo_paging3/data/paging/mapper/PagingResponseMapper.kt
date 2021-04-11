package com.richarddewan.todo_paging3.data.paging.mapper


/*
created by Richard Dewan 17/03/2021
*/

interface PagingResponseMapper<Response,PagingModel> {

    fun mapFromResponse(response: Response) : PagingModel

}