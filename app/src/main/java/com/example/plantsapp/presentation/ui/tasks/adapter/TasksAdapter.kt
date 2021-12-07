package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.model.TaskWithState
import com.example.plantsapp.presentation.model.TaskWithStateItemCallback

class TasksAdapter(
    private val plant: Plant,
    private val onTaskClick: (Pair<Plant, Task>) -> Unit
) : ListAdapter<TaskWithState, TasksViewHolder>(TaskWithStateItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder.create(parent, onTaskClick)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(
            plant = plant,
            taskWithState = getItem(position)
        )
    }
}
