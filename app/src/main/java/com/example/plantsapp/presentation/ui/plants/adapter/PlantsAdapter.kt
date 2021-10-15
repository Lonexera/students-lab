package com.example.plantsapp.presentation.ui.plants.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.presentation.model.PlantItemCallback

class PlantsAdapter(
    private val onPlantClick: (Plant) -> Unit
) : ListAdapter<Plant, PlantViewHolder>(PlantItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        return PlantViewHolder.create(parent, onPlantClick)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
