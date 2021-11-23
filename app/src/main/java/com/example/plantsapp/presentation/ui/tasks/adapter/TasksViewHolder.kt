package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.R
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

            btnCompleteTask.text = getCompleteButtonTitle(taskWithState.isCompleted)
            btnCompleteTask.isEnabled = !taskWithState.isCompleted
        }
    }

    private fun getCompleteButtonTitle(isCompleted: Boolean): String {
        return binding.root.context.getString(
            when (isCompleted) {
                true -> R.string.title_btn_completed_state
                else -> R.string.title_btn_uncompleted_state
            }
        )
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
