package com.example.plantsapp.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.databinding.ItemPlantBinding
import com.example.plantsapp.model.Plant

class PlantViewHolder(
    private val binding: ItemPlantBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plant: Plant) {
        with(binding) {
            tvPlantName.text = plant.name
            tvSpeciesName.text = plant.speciesName
            Glide.with(binding.root)
                .load(plant.plantPicture)
                .centerCrop()
                .into(ivPlant)
        }
    }
}
