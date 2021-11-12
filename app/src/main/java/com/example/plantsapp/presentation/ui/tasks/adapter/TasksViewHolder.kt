package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.databinding.ItemTaskBinding
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.presentation.ui.utils.getIconRes
import com.example.plantsapp.presentation.ui.utils.getTitleRes

class TasksViewHolder(
    private val binding: ItemTaskBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(task: Task) {
        with(binding) {

            tvTaskTitle.text = root.context.getString(
                task.getTitleRes()
            )

            Glide.with(binding.root)
                .load(task.getIconRes())
                .centerCrop()
                .into(ivTaskIcon)
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
