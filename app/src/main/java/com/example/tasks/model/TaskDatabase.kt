package com.example.tasks.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import okhttp3.internal.Internal.instance

/**
 * We created and called the getInstance method for when we need to provide our viewModelFactory
 * with an instance of taskDao. Because the Factory has a constructor that requires us to add
 * an instance of a variable before our ViewModel provider can provide our fragment with an
 * instance of the ViewModel.
 */

// Adds the table we first created to the database using the annotation
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {
    abstract val taskDao: TaskDao // Tells Room you want to use the methods specified in TaskDao

    // Companion object defined so we can call the getInstance() without having an
    // instance of TaskDatabase
    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // returns an Instance of TaskDatabase
        // builds the database if it doesn't exist
        fun getInstance(context: Context): TaskDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "task_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}