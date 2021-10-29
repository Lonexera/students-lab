package com.example.plantsapp.presentation.ui.tasksfordays.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TasksForDaysPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val numberOfPages: Int,
    private val fragmentConstructor: () -> Fragment
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = numberOfPages

    override fun createFragment(position: Int): Fragment {
        return fragmentConstructor()
    }
}
