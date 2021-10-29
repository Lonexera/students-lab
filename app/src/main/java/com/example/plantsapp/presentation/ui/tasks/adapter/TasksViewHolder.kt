package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.databinding.ItemTaskBinding
import com.example.plantsapp.domain.model.Task

class TasksViewHolder(
    private val binding: ItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        with(binding) {
            tvTaskTitle.text = "${task.taskAction} ${task.plantName}"
            Glide.with(binding.root)
                .load(task.plantPicture)
                .centerCrop()
                .into(ivPlant)
        }
    }

    companion object {

        fun create(
            parent: ViewGroup
        ): TasksViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTaskBinding.inflate(inflater, parent, false)

            return TasksViewHolder(binding)
        }
    }
}
