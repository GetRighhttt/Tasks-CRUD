package com.example.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.model.Task

/**
 * Here is our adapter necessary to inflate our item layout,
 * pass in the right data to each item, and bind views to their
 * position.
 */

// Extending RecyclerView.Adapter allows the class to act as an adapter.
// We use generics to say our adapter works with the viewholder inner class
class TaskItemAdapter : RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {

    // setter for the recyclerview adapter
    var data = listOf<Task>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    // ViewHolder class we need to set up our ViewHolder
    // Rootview is whatever the layout starts with for the list item
    class TaskItemViewHolder(private val rootView: CardView)
        :RecyclerView.ViewHolder(rootView) {

        val taskName: TextView = rootView.findViewById(R.id.task_name)
        val taskDone: CheckBox = rootView.findViewById(R.id.task_done)
        val cardView: CardView = rootView.findViewById(R.id.list_item)


        // set the text to the taskName from the entity table in our bind method
        // set the checkbox view also
        fun bind(item: Task) {
            taskName.text = item.taskName
            taskDone.isChecked = item.taskDone
        }

        // static object to inflate the layout and create the ViewHolder
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.task_item, parent, false) as CardView
                return TaskItemViewHolder(view)
            }
        }

        /**
         * It's better to hold all the code for the viewholder underneath the overriden methods.
         * It paints a more clear picture for separation of concerns.
         */
    }

    // Create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)


    // bind the ViewHolder to the position of the views in the RecyclerView
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item) // call the bind method to bind the item to the ViewHolder

        // animation for the RecyclerView
        holder.cardView.startAnimation(AnimationUtils
            .loadAnimation(holder.itemView.context,R.anim.visibility_animation))
    }

    // return the size of the data
    override fun getItemCount() = data.size
}