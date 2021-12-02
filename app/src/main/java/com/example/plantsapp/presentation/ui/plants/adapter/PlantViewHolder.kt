package com.example.plantsapp.presentation.ui.plants.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.databinding.ItemPlantBinding
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.presentation.ui.utils.getSharedImageTransitionName
import com.example.plantsapp.presentation.ui.utils.getSharedPlantNameTransitionName
import com.example.plantsapp.presentation.ui.utils.loadPicture

class PlantViewHolder(
    private val binding: ItemPlantBinding,
    private val onPlantClick: (plant: Plant, transitionElements: Pair<View, View>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plant: Plant) {
        with(binding) {
            tvPlantName.text = plant.name.value
            tvSpeciesName.text = plant.speciesName
            ivPlant.loadPicture(plant.plantPicture)

            ViewCompat.setTransitionName(
                ivPlant,
                root.context.getSharedImageTransitionName(plant.name.value)
            )
            ViewCompat.setTransitionName(
                tvPlantName,
                root.context.getSharedPlantNameTransitionName(plant.name.value)
            )
        }

        binding.root.setOnClickListener {
            onPlantClick(plant, binding.ivPlant to binding.tvPlantName)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onPlantClick: (Plant, Pair<View, View>) -> Unit
        ): PlantViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

            return PlantViewHolder(binding, onPlantClick)
        }
    }
}
