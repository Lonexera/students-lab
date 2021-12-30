package com.example.statisticsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil

class PlantStatisticsInfoItemCallback : DiffUtil.ItemCallback<PlantStatisticsInfo>() {

    override fun areItemsTheSame(
        oldItem: PlantStatisticsInfo,
        newItem: PlantStatisticsInfo
    ): Boolean {
        return oldItem.plant == newItem.plant
    }

    override fun areContentsTheSame(
        oldItem: PlantStatisticsInfo,
        newItem: PlantStatisticsInfo
    ): Boolean {
        return oldItem == newItem
    }
}
