package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.statisticsapp.presentation.model.PlantStatisticsInfo
import com.example.statisticsapp.presentation.model.PlantStatisticsInfoItemCallback

class PlantAdapter : ListAdapter<PlantStatisticsInfo, PlantViewHolder>(
    PlantStatisticsInfoItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
