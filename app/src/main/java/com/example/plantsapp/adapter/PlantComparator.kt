package com.example.plantsapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.plantsapp.model.Plant

class PlantComparator : DiffUtil.ItemCallback<Plant>() {

    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }
}
