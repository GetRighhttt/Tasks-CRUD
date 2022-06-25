package com.example.tasks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.adapter.TaskItemAdapter
import com.example.tasks.databinding.FragmentTasksBinding
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
        val adapter = TaskItemAdapter()
        binding.tasksList.adapter = adapter

        // observe the ViewModel's tasks property and make changes to the list accordingly
        // when the list of tasks change, assign it to the adapter's data property
        // this whole process basically passes livedata to the adapter
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}