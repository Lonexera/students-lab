package com.example.plantsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.plantsapp.domain.model.PlantWithTasks

class PlantWithTasksItemCallback : DiffUtil.ItemCallback<PlantWithTasks>() {

    override fun areItemsTheSame(oldItem: PlantWithTasks, newItem: PlantWithTasks): Boolean {
        return oldItem.plant == newItem.plant
    }

    override fun areContentsTheSame(oldItem: PlantWithTasks, newItem: PlantWithTasks): Boolean {
        return oldItem == newItem
    }
}
