package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.statisticsapp.presentation.model.TaskStatisticsInfo
import com.example.statisticsapp.presentation.model.TaskStatisticsInfoItemCallback

class TasksStatisticsAdapter :
    ListAdapter<TaskStatisticsInfo, TasksStatisticsViewHolder>(
        TaskStatisticsInfoItemCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksStatisticsViewHolder {
        return TasksStatisticsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TasksStatisticsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
