package com.example.tasks.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.tasks.model.Task

/**
 * Diffutil class to update the recyclerview without redrawing the list every time.
 * We extend the DiffUtil class, and use generics to specify the type of objects it works
 * with.
 * areItemsTheSame() used to check if two items in the list are the same assuming the list isn't
 * null.
 * areContentsTheSame() used to check if the contents of two items are the same, only
 * works if areItemsTheSame() is true though.
 */
class TaskDiffItemCallBack: DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task) =
        (oldItem.taskId == newItem.taskId)

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}