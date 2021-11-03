package com.example.plantsapp.presentation.ui.tasksfordays

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksForDaysBinding
import com.example.plantsapp.presentation.ui.tasksfordays.adapter.TasksForDaysPagerAdapter

class TasksForDaysFragment : Fragment(R.layout.fragment_tasks_for_days) {

    private val binding: FragmentTasksForDaysBinding by viewBinding(FragmentTasksForDaysBinding::bind)
    private val viewModel: TasksForDaysViewModel by viewModels {
        TasksForDaysViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            todayDate.observe(viewLifecycleOwner) {
                binding.vpTasks.adapter = TasksForDaysPagerAdapter(
                    requireActivity(),
                    it
                )
            }

            currentPageDate.observe(viewLifecycleOwner) {
                binding.tvCurrentDate.text = it
            }

            prevButtonVisibility.observe(viewLifecycleOwner) {
                binding.btnPrevDate.visibility = it
            }

            prevPageDate.observe(viewLifecycleOwner) {
                binding.btnPrevDate.text = it
            }

            nextPageDate.observe(viewLifecycleOwner) {
                binding.btnNextDate.text = it
            }
        }

        with(binding) {
            vpTasks.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    viewModel.onCurrentPageChange(position)
                }
            })

            btnPrevDate.setOnClickListener { vpTasks.currentItem -= 1 }
            btnNextDate.setOnClickListener { vpTasks.currentItem += 1 }
        }
    }
}
