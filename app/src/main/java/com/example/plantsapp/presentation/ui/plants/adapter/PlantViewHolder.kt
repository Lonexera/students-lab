package com.example.plantsapp.presentation.ui.plants.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.databinding.ItemPlantBinding
import com.example.plantsapp.domain.model.Plant

class PlantViewHolder(
    private val binding: ItemPlantBinding,
    private val onPlantClick: (Plant) -> Unit
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

        binding.root.setOnClickListener {
            onPlantClick(plant)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onPlantClick: (Plant) -> Unit
        ): PlantViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

            return PlantViewHolder(binding, onPlantClick)
        }
    }
}
