package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Plant
import com.example.statisticsapp.presentation.model.PlantItemCallback

class PlantAdapter : ListAdapter<Plant, PlantViewHolder>(PlantItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
