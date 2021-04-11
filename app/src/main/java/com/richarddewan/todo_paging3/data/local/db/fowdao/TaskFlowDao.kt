package com.richarddewan.todo_paging3.data.local.db.fowdao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richarddewan.todo_paging3.data.local.entity.TaskEntity


/*
created by Richard Dewan 09/04/2021
*/

@Dao
interface TaskFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(tasks: List<TaskEntity>): List<Long>

    @Query("SELECT * FROM tasks ORDER BY id ASC")
    fun getTasks(): PagingSource<Int,TaskEntity>

    @Query("DELETE FROM tasks")
    suspend fun clearTasks()

    @Query("DELETE FROM tasks where id =:id")
    suspend fun deleteTask(id: Int)


}