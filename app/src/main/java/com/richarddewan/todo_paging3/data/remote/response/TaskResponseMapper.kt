package com.richarddewan.todo_paging3.data.remote.response


/*
created by Richard Dewan 09/04/2021
*/

interface TaskResponseMapper<Response,Model> {

    abstract fun mapFromResponse(response: Response) : Model

}