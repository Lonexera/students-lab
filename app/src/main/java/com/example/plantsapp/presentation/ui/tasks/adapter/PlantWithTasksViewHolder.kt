package com.example.plantsapp.presentation.ui.tasks.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ItemPlantWithTasksBinding
import com.example.plantsapp.domain.model.PlantWithTasks

class PlantWithTasksViewHolder(
    private val binding: ItemPlantWithTasksBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val tasksAdapter = TasksAdapter()

    fun bind(plantWithTasks: PlantWithTasks) {
        with(binding) {
            tvTasksPlantName.text = plantWithTasks.plant.name.value

            Glide.with(binding.root)
                .load(plantWithTasks.plant.plantPicture)
                .placeholder(R.drawable.ic_baseline_image_24)
                .centerCrop()
                .into(ivTasksPlantPicture)

            rvPlantsWithTasks.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = tasksAdapter
            }

            tasksAdapter.submitList(plantWithTasks.tasks)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): PlantWithTasksViewHolder {

            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemPlantWithTasksBinding.inflate(inflater, parent, false)

            return PlantWithTasksViewHolder(binding)
        }
    }
}
