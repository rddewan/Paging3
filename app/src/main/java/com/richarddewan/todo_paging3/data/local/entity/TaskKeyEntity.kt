package com.richarddewan.todo_paging3.data.local.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "task_keys")
data class TaskKeyEntity(
    @PrimaryKey
    val taskId: Long,
    val previousKey: Int?,
    val nextKey: Int?
)
