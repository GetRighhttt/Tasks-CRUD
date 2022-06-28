package com.example.tasks.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.findFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tasks.R
import com.example.tasks.databinding.FragmentEditTasksBinding
import com.example.tasks.model.TaskDatabase
import com.example.tasks.viewmodel.EditTaskViewModel
import com.example.tasks.viewmodel.EditTaskViewModelFactory

class EditTasksFragment : Fragment() {

    private var _binding: FragmentEditTasksBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       _binding = FragmentEditTasksBinding.inflate(inflater,container, false)
        val view = binding?.root

        // Get the info from the safeargs and assign it to the taskId
        val taskId = EditTasksFragmentArgs.fromBundle(requireArguments()).taskId

        // needed to create an EditTaskViewlModelFactory
        // and grab an instance of the database
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        // create an instance of our viewmodelFactory which will get an instance of our
        // viewmodel from calling the viewModelProvider. The provider provides the fragment with
        // the instance of the viewmodel
        val viewModelFactory = EditTaskViewModelFactory(taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(EditTaskViewModel::class.java)

        // set our data binding variable for the layout so we can interact with
        // our viewmodel and livedata properties
        binding?.viewModel = viewModel
        binding?.lifecycleOwner = viewLifecycleOwner

        // calling our viewmodel to observe the livedata.
        // if its updated or deleted, navigate back to the tasks fragment
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if(navigate) {
                view?.findNavController()?.navigate(R.id.action_editTasksFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }
        })

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}