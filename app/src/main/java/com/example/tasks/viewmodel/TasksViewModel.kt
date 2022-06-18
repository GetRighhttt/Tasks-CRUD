package com.example.tasks.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.tasks.model.Task
import com.example.tasks.model.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// we pass our viewModel as instance of TaskDao so we can use it
// to update Room
class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""

    /**
     assigning a variable to get all the tasks from Room
     using Transformations.map() so our textview can display a list
     Transformations.map() is used to change liveData into another liveData object
     It also keeps the record up to date
     */
    val tasks = dao.getAll()
    val tasksString = Transformations.map(tasks) {
        tasks -> formatTasks(tasks)
    }


    // method to insert task into the table
    fun addTask() {
        CoroutineScope(Dispatchers.IO).launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }

    // method used to format a list of Tasks into a string
    fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") {
            str, item -> str + '\n' + formatTasks(tasks)
        }
    }

    // method that formats each tasks into a string individually
    fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}"
        return str
    }
}