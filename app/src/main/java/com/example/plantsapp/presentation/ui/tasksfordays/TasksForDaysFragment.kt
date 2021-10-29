package com.example.plantsapp.presentation.ui.tasksfordays

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksForDaysBinding
import com.example.plantsapp.presentation.ui.tasks.TasksFragment
import com.example.plantsapp.presentation.ui.tasksfordays.adapter.TasksForDaysPagerAdapter

class TasksForDaysFragment : Fragment(R.layout.fragment_tasks_for_days) {

    private val binding: FragmentTasksForDaysBinding by viewBinding(FragmentTasksForDaysBinding::bind)
    private val viewModel: TasksForDaysViewModel by viewModels {
        TasksForDaysViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            numberOfDays.observe(viewLifecycleOwner) {
                binding.vpTasks.adapter = TasksForDaysPagerAdapter(
                    requireActivity(),
                    it
                ) { TasksFragment() }
            }
        }
    }
}
