package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ItemTaskBinding
import com.example.plantsapp.domain.model.Task

class TasksViewHolder(
    private val binding: ItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        with(binding) {

            tvTaskTitle.text = root.context.getString(
                task.getTitleRes(),
                task.plantName
            )

            tvTaskTitle.setCompoundDrawablesWithIntrinsicBounds(
                ResourcesCompat.getDrawable(
                    root.resources,
                    task.getIconRes(),
                    null
                ),
                null,
                null,
                null
            )
            Glide.with(binding.root)
                .load(task.plantPicture)
                .centerCrop()
                .into(ivPlant)
        }
    }

    private fun Task.getTitleRes(): Int {
        return when (this) {
            is Task.WateringTask -> R.string.title_watering_task
            is Task.SprayingTask -> R.string.title_spraying_task
            is Task.LooseningTask -> R.string.title_loosening_task
        }
    }

    private fun Task.getIconRes(): Int {
        return when (this) {
            is Task.WateringTask -> R.drawable.ic_watering
            is Task.SprayingTask -> R.drawable.ic_spraying
            is Task.LooseningTask -> R.drawable.ic_watering
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
