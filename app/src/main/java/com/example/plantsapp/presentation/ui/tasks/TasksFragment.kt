package com.example.plantsapp.presentation.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksBinding
import com.example.plantsapp.presentation.ui.tasks.adapter.TasksAdapter

class TasksFragment : Fragment(R.layout.fragment_tasks) {

    private val binding: FragmentTasksBinding by viewBinding(FragmentTasksBinding::bind)
    private val tasksViewModel: TasksViewModel by viewModels {
        TasksViewModelFactory()
    }
    private val tasksAdapter = TasksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(tasksViewModel) {
            tasks.observe(viewLifecycleOwner) {
                tasksAdapter.submitList(it)
            }
        }

        with(binding) {
            rvTasks.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = tasksAdapter
            }
        }
    }
}
