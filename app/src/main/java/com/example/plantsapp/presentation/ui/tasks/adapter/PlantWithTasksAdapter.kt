package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.model.PlantWithTasksItemCallback

class PlantWithTasksAdapter :
    ListAdapter<Pair<Plant, List<Task>>, PlantWithTasksViewHolder>(PlantWithTasksItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantWithTasksViewHolder {
        return PlantWithTasksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantWithTasksViewHolder, position: Int) {
        val plantAndTasks = getItem(position)
        holder.bind(plantAndTasks.first, plantAndTasks.second)
    }
}
