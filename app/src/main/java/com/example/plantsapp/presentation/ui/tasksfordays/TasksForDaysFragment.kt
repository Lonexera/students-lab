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
import com.google.android.material.tabs.TabLayoutMediator
import java.util.Date

class TasksForDaysFragment : Fragment(R.layout.fragment_tasks_for_days) {

    private val binding: FragmentTasksForDaysBinding by viewBinding(FragmentTasksForDaysBinding::bind)
    private val viewModel: TasksForDaysViewModel by viewModels {
        TasksForDaysViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            // TODO move date setting to viewModel
            val todayDate = Date()
            numberOfDays.observe(viewLifecycleOwner) {
                binding.vpTasks.adapter = TasksForDaysPagerAdapter(
                    requireActivity(),
                    it
                ) { position ->
                    TasksFragment.newInstance(
                        getDateWithOffset(
                            startDate = todayDate,
                            offsetInDays = position
                        )
                    )
                }

                TabLayoutMediator(binding.tlDays, binding.vpTasks) { tab, position ->
                    tab.text = position.toString()
                }.attach()
            }
        }
    }

    private fun getDateWithOffset(
        startDate: Date,
        offsetInDays: Int
    ): Date {
        return Date(startDate.time + (offsetInDays * DAY_IN_MILLISECONDS))
    }
}

private const val DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24L
