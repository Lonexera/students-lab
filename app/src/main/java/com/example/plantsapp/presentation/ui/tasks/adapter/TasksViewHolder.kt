package com.example.plantsapp.presentation.ui.tasks.adapter

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.databinding.ItemTaskBinding
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskWithState
import com.example.plantsapp.presentation.ui.utils.getIconRes
import com.example.plantsapp.presentation.ui.utils.getTitleRes
import com.example.plantsapp.presentation.ui.utils.loadPicture

class TasksViewHolder(
    private val binding: ItemTaskBinding,
    private val onTaskClick: (Task) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(taskWithState: TaskWithState) {
        with(binding) {
            val task = taskWithState.task

            tvTaskTitle.text = root.context.getString(
                task.getTitleRes()
            )

            ivTaskIcon.loadPicture(task.getIconRes())

            btnCompleteTask.setOnClickListener {
                onTaskClick(task)
            }

            if (taskWithState.isCompleted) {
                showCompletedTask()
            }
        }
    }

    private fun showCompletedTask() {
        with(binding) {
            tvTaskTitle.setTextColor(Color.LTGRAY)
            tvTaskTitle.paintFlags = tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            ivTaskIcon.setColorFilter(Color.LTGRAY)

            btnCompleteTask.isEnabled = false
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            onTaskClick: (Task) -> Unit
        ): TasksViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTaskBinding.inflate(inflater, parent, false)

            return TasksViewHolder(binding, onTaskClick)
        }
    }
}
