package com.example.tasks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}