package com.richarddewan.todo_paging3.data.local.db.fowdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richarddewan.todo_paging3.data.local.entity.TaskKeyEntity


/*
created by Richard Dewan 09/04/2021
*/

@Dao
interface TaskKeyFlowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(keyEntities: List<TaskKeyEntity>)

    @Query("SELECT * FROM task_keys WHERE taskId =:taskId")
    suspend fun getKeysByTaskId(taskId: Int): TaskKeyEntity?

    @Query("DELETE FROM task_keys")
    suspend fun clearKeys()
}