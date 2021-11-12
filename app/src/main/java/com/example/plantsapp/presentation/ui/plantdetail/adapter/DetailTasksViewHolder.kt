package com.example.plantsapp.presentation.ui.plantdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ItemPlantDetailTaskBinding
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.ui.utils.getIconRes
import com.example.plantsapp.presentation.ui.utils.getTitleRes

class DetailTasksViewHolder(
    private val binding: ItemPlantDetailTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        with(binding) {
            Glide.with(binding.root)
                .load(task.getIconRes())
                .centerCrop()
                .into(ivTaskIcon)

            tvTaskTitle.text = root.context.getTaskTitleText(task)
            tvTaskFrequency.text = root.context.getFrequencyText(task.frequencyInDays)
        }
    }

    private fun Context.getTaskTitleText(task: Task): String {
        return getString(
            R.string.title_task_name,
            getString(task.getTitleRes())
        )
    }

    private fun Context.getFrequencyText(frequency: Int): String {
        return getString(
            R.string.msg_task_frequency,
            resources.getQuantityString(
                R.plurals.msg_creation_frequency_units,
                frequency,
                frequency
            )
        )
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
