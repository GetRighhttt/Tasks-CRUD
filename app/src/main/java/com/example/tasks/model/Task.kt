package com.example.tasks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

// @Entity used to define a table name
// This will hold the data in the table

@Entity(tableName = "task_table")
data class Task(

    @PrimaryKey(autoGenerate = true)
    var taskId: Long = 0L,

    @ColumnInfo(name = "task_name")
    var taskName: String = "",

    @ColumnInfo(name = "task_done")
    var taskDone: Boolean = false
) { @Ignore
   constructor(): this(0, "", false)
}
