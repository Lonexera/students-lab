package com.example.plantsapp.presentation.ui.plantdetail.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ItemPlantDetailTaskBinding
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.ui.utils.getIconRes
import com.example.plantsapp.presentation.ui.utils.getTitleRes
import com.example.plantsapp.presentation.ui.utils.loadPicture

class DetailTasksViewHolder(
    private val binding: ItemPlantDetailTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        with(binding) {
            ivTaskIcon.loadPicture(
                context = root.context,
                picture = task.getIconRes()
            )

            tvTaskDetail.text = root.context.getTaskDetailText(task)
        }
    }

    private fun Context.getTaskDetailText(task: Task): String {
        return getString(
            R.string.title_task_detail,
            getString(task.getTitleRes()),
            resources.getQuantityString(
                R.plurals.msg_creation_frequency_units,
                task.frequencyInDays,
                task.frequencyInDays
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
