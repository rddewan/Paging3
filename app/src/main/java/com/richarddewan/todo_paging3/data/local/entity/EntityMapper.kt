package com.richarddewan.todo_paging3.data.local.entity


/*
created by Richard Dewan 09/04/2021
*/

interface EntityMapper<Model,Entity> {

    abstract fun mapToEntity(model: List<Model>) : List<Entity>
}