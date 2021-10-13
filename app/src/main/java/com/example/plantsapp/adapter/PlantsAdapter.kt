package com.example.plantsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.databinding.ItemPlantBinding
import com.example.plantsapp.model.Plant

class PlantsAdapter : ListAdapter<Plant, PlantViewHolder>(PlantComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
