package com.example.statisticsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.statisticsapp.domain.model.Plant

class PlantItemCallback : DiffUtil.ItemCallback<Plant>() {

    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }
}
