package com.example.plantsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.databinding.ItemPlantBinding
import com.example.plantsapp.model.Plant

class PlantsAdapter : ListAdapter<Plant, PlantViewHolder>(itemComparator) {

    companion object {
        val itemComparator = object : DiffUtil.ItemCallback<Plant>() {

            override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

        return PlantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
