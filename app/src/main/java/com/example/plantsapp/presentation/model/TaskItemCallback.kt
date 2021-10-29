package com.example.plantsapp.presentation.model

import androidx.recyclerview.widget.DiffUtil
import com.example.plantsapp.domain.model.Task

class TaskItemCallback : DiffUtil.ItemCallback<Task>() {

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.plantName == newItem.plantName
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}
