package com.example.tasks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tasks.adapter.TaskItemAdapter
import com.example.tasks.databinding.FragmentTasksBinding
import com.example.tasks.model.Task
import com.example.tasks.model.TaskDatabase
import com.example.tasks.viewmodel.TaskViewModelFactory
import com.example.tasks.viewmodel.TasksViewModel


class TasksFragment : Fragment() {

    private var _binding: FragmentTasksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTasksBinding.inflate(inflater, container, false)
        val view = binding.root

        /**
         * Below, we are going to build the database in the first two lines if it doesn't already
         * exist. Then we are getting a reference to the taskDao property.
         * After that, we call the ViewModelProvider to provide the ViewModel to the Fragment.
         */
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao
        val viewModelFactory = TaskViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(TasksViewModel::class.java)
        // data binding so the layout can get access to the viewModel's properties
        binding.viewModel = viewModel
        // data binding so the fragment can respond to updates with LiveData
        binding.lifecycleOwner = viewLifecycleOwner

        // Now we tell the recyclerview to use the adapter
        // And use the clickListener set in the adapter's constructor to send infro from
        // the task fragment to edit task fragment
        val adapter = TaskItemAdapter { taskId ->
            viewModel.onTaskClicked(taskId)
        }
        binding.tasksList.adapter = adapter

        // observe the ViewModel's tasks property and make changes to the list accordingly
        // when the list of tasks change, assign it to the adapter's data property
        // this whole process basically passes livedata to the adapter
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        // Now we are going to use the viewmodel's navigatetotask method to go from one fragment
        // to the next fragment, and we're passing the taskId with this method.
        viewModel.navigateToTask.observe(viewLifecycleOwner, Observer { taskId ->
            taskId?.let {
                val action = TasksFragmentDirections
                    .actionTasksFragmentToEditTasksFragment(taskId)
                view.findNavController().navigate(action)
                viewModel.onTaskNavigated()
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}