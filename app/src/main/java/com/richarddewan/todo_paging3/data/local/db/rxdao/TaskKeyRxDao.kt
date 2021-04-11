package com.richarddewan.todo_paging3.data.local.db.rxdao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.richarddewan.todo_paging3.data.local.entity.TaskKeyEntity


/*
created by Richard Dewan 09/04/2021
*/

@Dao
interface TaskKeyRxDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(keys: List<TaskKeyEntity>)

    @Query("SELECT * FROM task_keys WHERE taskId =:taskId")
    fun getTaskKeysByTaskId(taskId: Int ): TaskKeyEntity

    @Query("DELETE FROM task_keys")
    fun clearTaskKeys()
}