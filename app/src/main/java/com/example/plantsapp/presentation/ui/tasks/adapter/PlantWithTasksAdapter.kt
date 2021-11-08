package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.PlantWithTasks
import com.example.plantsapp.presentation.model.PlantWithTasksItemCallback

class PlantWithTasksAdapter : ListAdapter<PlantWithTasks, PlantWithTasksViewHolder>(PlantWithTasksItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantWithTasksViewHolder {
        return PlantWithTasksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantWithTasksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
