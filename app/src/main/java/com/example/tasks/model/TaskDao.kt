package com.example.tasks.model

import androidx.lifecycle.LiveData
import androidx.room.*

// Dao = data access object
// Interface used to interact with the table using SQL methodology
// suspend used to mark coroutines
// @Query returns live data so we don't need suspend for those

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM task_table WHERE taskId = :taskId")
    fun get(taskId: Long) : LiveData<Task>

    @Query("SELECT * FROM task_table ORDER by taskId DESC")
    fun getAll() : LiveData<List<Task>>
}