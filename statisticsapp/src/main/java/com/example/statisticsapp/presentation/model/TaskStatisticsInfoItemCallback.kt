package com.example.statisticsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.statisticsapp.presentation.ui.statistics.StatisticsViewModel

class TaskStatisticsInfoItemCallback :
    DiffUtil.ItemCallback<StatisticsViewModel.TaskStatisticsInfo>() {

    override fun areItemsTheSame(
        oldItem: StatisticsViewModel.TaskStatisticsInfo,
        newItem: StatisticsViewModel.TaskStatisticsInfo
    ): Boolean {
        return oldItem.task == newItem.task
    }

    override fun areContentsTheSame(
        oldItem: StatisticsViewModel.TaskStatisticsInfo,
        newItem: StatisticsViewModel.TaskStatisticsInfo
    ): Boolean {
        return oldItem == newItem
    }
}
