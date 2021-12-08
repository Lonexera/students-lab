package com.example.plantsapp.presentation.ui.tasksfordays.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plantsapp.presentation.ui.tasks.TasksFragment
import com.example.plantsapp.presentation.ui.utils.plusDays
import java.util.Date

class TasksForDaysPagerAdapter(
    fragmentActivity: Fragment,
    private val todayDate: Date
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = Int.MAX_VALUE

    override fun createFragment(position: Int): Fragment {
        return TasksFragment.newInstance(
            todayDate.plusDays(position)
        )
    }
}
