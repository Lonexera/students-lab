package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.statisticsapp.presentation.model.PlantStatisticsInfoItemCallback
import com.example.statisticsapp.presentation.ui.statistics.StatisticsViewModel

class PlantAdapter : ListAdapter<StatisticsViewModel.PlantStatisticsInfo, PlantViewHolder>(
    PlantStatisticsInfoItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
