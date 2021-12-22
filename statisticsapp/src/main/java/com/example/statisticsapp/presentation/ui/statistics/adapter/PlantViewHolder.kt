package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.domain.model.Plant
import com.example.statisticsapp.databinding.ItemPlantBinding

class PlantViewHolder(
    private val binding: ItemPlantBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plant: Plant) {
        with(binding) {
            tvPlantPicture.text = plant.plantPicture.toString()
            tvPlantName.text = plant.name.value
            tvPlantSpeciesName.text = plant.speciesName
        }
    }

    companion object {
        fun create(parent: ViewGroup): PlantViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

            return PlantViewHolder(binding)
        }
    }
}
