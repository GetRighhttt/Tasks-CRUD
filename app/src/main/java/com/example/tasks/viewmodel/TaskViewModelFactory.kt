package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.model.TaskDao
import java.lang.IllegalArgumentException
/**
    ViewModelProvider's job is to provide fragments and activities with ViewModels.
    The factory is only needed when our ViewModel constructor has an argument.
    However in this case, the Factory requires TaskDao as a argument. So we have to get
    an instance of TaskDao from the Database that we created earlier.
*/

class TaskViewModelFactory(private val dao: TaskDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TasksViewModel::class.java)) {
            // We use the TaskDao object to create a TaskViewModel object
            return TasksViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}