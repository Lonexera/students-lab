package com.example.plantsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.plantsapp.domain.model.Plant

class PlantWithTaskAndStateItemCallback : DiffUtil.ItemCallback<Pair<Plant, List<TaskWithState>>>() {

    override fun areItemsTheSame(
        oldItem: Pair<Plant, List<TaskWithState>>,
        newItem: Pair<Plant, List<TaskWithState>>
    ): Boolean {
        return oldItem.first == newItem.first
    }

    override fun areContentsTheSame(
        oldItem: Pair<Plant, List<TaskWithState>>,
        newItem: Pair<Plant, List<TaskWithState>>
    ): Boolean {
        return oldItem == newItem
    }
}
