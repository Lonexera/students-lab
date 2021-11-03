package com.example.plantsapp.presentation.ui.tasksfordays

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksForDaysBinding
import com.example.plantsapp.presentation.ui.tasksfordays.adapter.TasksForDaysPagerAdapter
import com.example.plantsapp.presentation.ui.utils.formatDateToStandard
import com.example.plantsapp.presentation.ui.utils.formatDateWithoutYear
import com.example.plantsapp.presentation.ui.utils.plusDays
import java.util.Date

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

                binding.vpTasks.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        binding.btnPrevDate.text = requireContext().onPageChangeText(it, position - 1)
                        binding.btnNextDate.text = requireContext().onPageChangeText(it, position + 1)
                        binding.tvCurrentDate.text = requireContext().onPageChangeText(it, position, true)

                        binding.btnPrevDate.visibility = onPageChangeVisibility(position)
                    }
                })
            }
        }

        with(binding) {
            btnPrevDate.setOnClickListener { vpTasks.currentItem -= 1 }
            btnNextDate.setOnClickListener { vpTasks.currentItem += 1 }
        }
    }

    private fun Context.onPageChangeText(date: Date, offset: Int, showWithYear: Boolean = false): String {
        return when (offset) {
            0 -> {
                resources.getString(R.string.title_today_date)
            }
            1 -> {
                resources.getString(R.string.title_tomorrow_date)
            }
            else -> {
                if (showWithYear) {
                    date.plusDays(offset).formatDateToStandard()
                } else {
                    date.plusDays(offset).formatDateWithoutYear()
                }
            }
        }
    }

    private fun onPageChangeVisibility(offset: Int): Int {
        return when (offset) {
            0 -> View.GONE
            else -> View.VISIBLE
        }
    }
}
