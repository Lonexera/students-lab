package com.example.plantsapp.presentation.ui.tasksfordays

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksForDaysBinding
import com.example.plantsapp.presentation.ui.tasksfordays.adapter.TasksForDaysPagerAdapter
import com.example.plantsapp.presentation.ui.utils.formatDate
import com.example.plantsapp.presentation.ui.utils.plusDays
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class TasksForDaysFragment : Fragment(R.layout.fragment_tasks_for_days) {

    private val binding: FragmentTasksForDaysBinding by viewBinding(FragmentTasksForDaysBinding::bind)
    private val viewModel: TasksForDaysViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle(R.string.title_tasks_screen)

        with(viewModel) {
            todayDate.observe(viewLifecycleOwner) {
                binding.vpTasks.adapter = TasksForDaysPagerAdapter(
                    this@TasksForDaysFragment,
                    it
                )

                binding.vpTasks.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)

                        binding.btnPrevDate.text = requireContext().getNewPageText(it, position - 1)
                        binding.btnNextDate.text = requireContext().getNewPageText(it, position + 1)
                        binding.tvCurrentDate.text =
                            requireContext().getNewPageText(it, position, true)

                        binding.btnPrevDate.isVisible = position != 0
                    }
                })
            }

            launchStatisticsApp.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { intent ->
                    startActivity(intent)
                }
            }
        }

        with(binding) {
            btnPrevDate.setOnClickListener { vpTasks.currentItem -= 1 }
            btnNextDate.setOnClickListener { vpTasks.currentItem += 1 }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_tasks_appbar, menu)

        viewModel.isStatisticsAppInstalled.observe(viewLifecycleOwner) { isInstalled ->
            menu.findItem(R.id.action_statistics).isVisible = isInstalled
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_statistics -> {
                viewModel.onStatisticsClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun Context.getNewPageText(
        date: Date,
        offset: Int,
        showWithYear: Boolean = false
    ): String {
        return when (offset) {
            0 -> getString(R.string.title_today_date)
            1 -> getString(R.string.title_tomorrow_date)
            else -> {
                val format = if (showWithYear) {
                    DATE_FORMAT_WITH_YEAR
                } else {
                    DATE_FORMAT_WITHOUT_YEAR
                }
                date.plusDays(offset).formatDate(format)
            }
        }
    }

    companion object {
        private const val DATE_FORMAT_WITH_YEAR = "d MMM yyyy"
        private const val DATE_FORMAT_WITHOUT_YEAR = "d MMM"
    }
}
