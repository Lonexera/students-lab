package com.example.plantsapp.presentation.ui.plantdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ItemPlantDetailTaskBinding
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.ui.utils.getColorRes
import com.example.plantsapp.presentation.ui.utils.getIconRes

class DetailTasksViewHolder(
    private val binding: ItemPlantDetailTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        with(binding) {
            ivTaskIcon.setImageResource(task.getIconRes())
            ivTaskIcon.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    task.getColorRes()
                )
            )

            tvTaskTitle.text = root.context.getString(task.getShortTitleRes())
            tvTaskDetail.text = root.context.getTaskDetailText(task)
        }
    }

    private fun Context.getTaskDetailText(task: Task): String {
        return getString(
            R.string.title_task_detail,
            resources.getQuantityString(
                R.plurals.msg_creation_frequency_units,
                task.frequency,
                task.frequency
            )
        )
    }

    private fun Task.getShortTitleRes(): Int {
        return when (this) {
            is Task.WateringTask -> R.string.title_plant_detail_watering_task
            is Task.SprayingTask -> R.string.title_plant_detail_spraying_task
            is Task.LooseningTask -> R.string.title_plant_detail_loosening_task
            is Task.TakingPhotoTask -> R.string.title_plant_detail_taking_photo_task
        }
    }

    companion object {

        fun create(
            parent: ViewGroup
        ): DetailTasksViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                ItemPlantDetailTaskBinding.inflate(inflater, parent, false)

            return DetailTasksViewHolder(binding)
        }
    }
}
