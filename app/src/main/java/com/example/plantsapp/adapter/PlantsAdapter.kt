package com.example.plantsapp.adapter


import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.model.Plant

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
