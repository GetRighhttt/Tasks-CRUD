package com.example.tasks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.model.TaskDao
import java.lang.IllegalArgumentException


// Because we're passing arguments in our viewmodel, we need a viewmodelFactory to create
// an instance of viewmodel in our fragment
class EditTaskViewModelFactory(private val taskId: Long, private val dao: TaskDao)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditTaskViewModel::class.java)) {
            return EditTaskViewModel(taskId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel.")
    }

}