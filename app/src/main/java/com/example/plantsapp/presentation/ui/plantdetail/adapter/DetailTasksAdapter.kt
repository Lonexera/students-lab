package com.example.plantsapp.presentation.ui.plantdetail.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.model.TaskItemCallback

class DetailTasksAdapter : ListAdapter<Task, DetailTasksViewHolder>(TaskItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTasksViewHolder {
        return DetailTasksViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DetailTasksViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
