package com.example.statisticsapp.presentation.ui.statistics.adapter

import com.example.statisticsapp.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.domain.model.Task
import com.example.statisticsapp.databinding.ItemTaskAndCompletionsNumberBinding
import com.example.statisticsapp.presentation.model.TaskStatisticsInfo

class TasksStatisticsViewHolder(
    private val binding: ItemTaskAndCompletionsNumberBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(taskStatisticsInfo: TaskStatisticsInfo) {
        with (binding) {
            tvTaskAndCompletionNumber.text = root.context.getString(
                taskStatisticsInfo.task.getTitleRes(),
                taskStatisticsInfo.amount
            )
        }
    }

    private fun Task.getTitleRes(): Int {
        return when (this) {
            is Task.WateringTask -> R.string.title_watering_task
            is Task.SprayingTask -> R.string.title_spraying_task
            is Task.LooseningTask -> R.string.title_loosening_task
            is Task.TakingPhotoTask -> R.string.title_taking_photo_task
        }
    }

    companion object {
        fun create(parent: ViewGroup): TasksStatisticsViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemTaskAndCompletionsNumberBinding.inflate(layoutInflater, parent, false)

            return TasksStatisticsViewHolder(binding)
        }
    }
}
