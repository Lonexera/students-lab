package com.example.statisticsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil

class TaskStatisticsInfoItemCallback :
    DiffUtil.ItemCallback<TaskStatisticsInfo>() {

    override fun areItemsTheSame(
        oldItem: TaskStatisticsInfo,
        newItem: TaskStatisticsInfo
    ): Boolean {
        return oldItem.task == newItem.task
    }

    override fun areContentsTheSame(
        oldItem: TaskStatisticsInfo,
        newItem: TaskStatisticsInfo
    ): Boolean {
        return oldItem == newItem
    }
}
