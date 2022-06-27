package com.example.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.databinding.TaskItemBinding
import com.example.tasks.model.Task

/**
 * Here is our adapter necessary to inflate our item layout,
 * pass in the right data to each item, and bind views to their
 * position.
 */

// Extending RecyclerView.Adapter allows the class to act as an adapter.
// We use generics to say our adapter works with the viewholder inner class
// We pass in the id of the task from the entity table, and use that to make the item's
// respond to clicks
class TaskItemAdapter(private val clickListener: (taskId: Long) -> Unit)
    : RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {

    // setter for the recyclerview adapter
    var data = listOf<Task>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    // ViewHolder class we need to set up our ViewHolder
    // we're passing in databinding to inflate the root and the cardview needed for animations
    class TaskItemViewHolder(private val binding: TaskItemBinding)
        :RecyclerView.ViewHolder(binding.root) {

        // set the cardview item to the id of the cardview to use animation later on
        val cardView: CardView = binding.listItem

        // bind the task from the database to the recyclerview item
        // we set the onclicklistener here with the id of the task from the entity table
        fun bind(item: Task, clickListener: (taskId: Long) -> Unit) {
            binding.task = item
            binding.root.setOnClickListener{
                clickListener(item.taskId)
            }
        }

        // static object to inflate the layout and create the ViewHolder
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }

        /**
         * It's better to hold all the code for the viewholder outside the overriden methods.
         * It paints a more clear picture for separation of concerns.
         */
    }

    // Create the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)


    // bind the ViewHolder to the position of the views in the RecyclerView
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = data[position]
        // call the bind method to bind the item to the ViewHolder
        // and also set the clicklistener with that bind
        holder.bind(item, clickListener)

        // animation for the RecyclerView
        holder.cardView.startAnimation(AnimationUtils
            .loadAnimation(holder.itemView.context,R.anim.visibility_animation))
    }

    // return the size of the data
    override fun getItemCount() = data.size
}