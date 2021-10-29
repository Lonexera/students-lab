package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.model.TaskItemCallback

class TasksAdapter : ListAdapter<Task, TasksViewHolder>(TaskItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
