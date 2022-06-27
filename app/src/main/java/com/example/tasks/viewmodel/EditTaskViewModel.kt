package com.example.tasks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.model.TaskDao
import kotlinx.coroutines.launch

// ViewModel necessary to access TaskDao's methods and pass the results to
// EditTaskFragment. Will hold the business logic and the data
class EditTaskViewModel(taskId: Long, val dao: TaskDao): ViewModel() {
    // sets the task property to a LiveData<Task>
    val task = dao.get(taskId)

    // property assigned so we can navigate back to tasksfragment when the
    // tasks has been updated or deleted
    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean>
        get() = _navigateToList

    // method to update the task
    // sets navigatetolist to true so it goes back to tasks fragment after
    fun updateTask() {
        viewModelScope.launch {
            dao.update(task.value!!)
            _navigateToList.value = true
        }
    }

    // method to delete the task
    // sets navigatetolist to true so it goes back to tasks fragment after
    fun deleteTask() {
        viewModelScope.launch {
            dao.delete(task.value!!)
            _navigateToList.value = true
        }
    }

    // after we have navigated back to tasks, set the _navigateToList back to false
    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}