package com.richarddewan.todo_paging3.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.richarddewan.todo_paging3.data.local.db.fowdao.TaskFlowDao
import com.richarddewan.todo_paging3.data.local.db.fowdao.TaskKeyFlowDao
import com.richarddewan.todo_paging3.data.local.db.rxdao.TaskKeyRxDao
import com.richarddewan.todo_paging3.data.local.db.rxdao.TaskRxDao
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import com.richarddewan.todo_paging3.data.local.entity.TaskKeyEntity


/*
created by Richard Dewan 09/04/2021
*/

@Database(entities = [TaskEntity::class,TaskKeyEntity::class],version = 1,exportSchema = true)
abstract class DatabaseService: RoomDatabase() {

    abstract fun taskFlowDao(): TaskFlowDao
    abstract fun taskKeyFlowDao(): TaskKeyFlowDao

    abstract fun taskRxDao():TaskRxDao
    abstract fun taskKeyRxDao(): TaskKeyRxDao
}