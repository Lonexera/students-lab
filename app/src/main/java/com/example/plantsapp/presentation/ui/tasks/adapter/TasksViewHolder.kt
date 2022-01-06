package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.databinding.ItemTaskBinding
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.model.TaskWithState
import com.example.plantsapp.presentation.ui.utils.getColorRes
import com.example.plantsapp.presentation.ui.utils.getIconRes
import com.example.plantsapp.presentation.ui.utils.getTitleRes

class TasksViewHolder(
    private val binding: ItemTaskBinding,
    private val onTaskClick: (Pair<Plant, Task>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plant: Plant, taskWithState: TaskWithState) {
        with(binding) {
            val task = taskWithState.task

            tvTaskTitle.text = root.context.getString(task.getTitleRes())
            setUpTaskIconFor(task)

            btnCompleteTask.setOnClickListener {
                onTaskClick(plant to task)
            }
            btnCompleteTask.isVisible = taskWithState.isCompletable
            btnCompleteTask.isEnabled = !taskWithState.isCompleted
        }
    }

    private fun setUpTaskIconFor(task: Task) {
        with(binding.ivTaskIcon) {
            setImageResource(task.getIconRes())
            setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    task.getColorRes()
                )
            )
        }
    }

    companion object {

        fun create(
            parent: ViewGroup,
            onTaskClick: (Pair<Plant, Task>) -> Unit
        ): TasksViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemTaskBinding.inflate(inflater, parent, false)

            return TasksViewHolder(binding, onTaskClick)
        }
    }
}
