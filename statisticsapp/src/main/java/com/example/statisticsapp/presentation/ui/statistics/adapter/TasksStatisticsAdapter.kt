package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.statisticsapp.presentation.model.TaskStatisticsInfoItemCallback
import com.example.statisticsapp.presentation.ui.statistics.StatisticsViewModel

class TasksStatisticsAdapter :
    ListAdapter<StatisticsViewModel.TaskStatisticsInfo, TasksStatisticsViewHolder>(
        TaskStatisticsInfoItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksStatisticsViewHolder {
        return TasksStatisticsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TasksStatisticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
