package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.model.TaskWithState
import com.example.plantsapp.presentation.model.PlantWithTaskAndStateItemCallback

class PlantWithTasksAdapter(
    private val onTaskClick: (Task) -> Unit
) :
    ListAdapter<Pair<Plant, List<TaskWithState>>, PlantWithTasksViewHolder>(PlantWithTaskAndStateItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantWithTasksViewHolder {
        return PlantWithTasksViewHolder.create(parent, onTaskClick)
    }

    override fun onBindViewHolder(holder: PlantWithTasksViewHolder, position: Int) {
        val plantAndTasks = getItem(position)
        holder.bind(plantAndTasks.first, plantAndTasks.second)
    }
}
