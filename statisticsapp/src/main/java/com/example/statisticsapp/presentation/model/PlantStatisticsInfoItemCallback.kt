package com.example.statisticsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.statisticsapp.presentation.ui.statistics.StatisticsViewModel

class PlantStatisticsInfoItemCallback : DiffUtil.ItemCallback<StatisticsViewModel.PlantStatisticsInfo>() {

    override fun areItemsTheSame(
        oldItem: StatisticsViewModel.PlantStatisticsInfo,
        newItem: StatisticsViewModel.PlantStatisticsInfo
    ): Boolean {
        return oldItem.plant == newItem.plant
    }

    override fun areContentsTheSame(
        oldItem: StatisticsViewModel.PlantStatisticsInfo,
        newItem: StatisticsViewModel.PlantStatisticsInfo
    ): Boolean {
        return oldItem == newItem
    }
}
