package com.richarddewan.todo_paging3.data.local.db.rxdao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity
import io.reactivex.rxjava3.core.Single


/*
created by Richard Dewan 09/04/2021
*/

@Dao
interface TaskRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(tasks: List<TaskEntity>)

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getTasks(): PagingSource<Int,TaskEntity>

    @Query("DELETE FROM tasks")
    fun clearTasks()
}