package com.example.plantsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task

class PlantWithTasksItemCallback : DiffUtil.ItemCallback<Pair<Plant, List<Task>>>() {

    override fun areItemsTheSame(
        oldItem: Pair<Plant, List<Task>>,
        newItem: Pair<Plant, List<Task>>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<Plant, List<Task>>,
        newItem: Pair<Plant, List<Task>>
    ): Boolean {
        return oldItem.first == newItem.first
    }
}
