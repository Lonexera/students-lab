package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.statisticsapp.R
import com.example.statisticsapp.databinding.ItemPlantBinding
import com.example.statisticsapp.presentation.model.PlantStatisticsInfo

class PlantViewHolder(
    private val binding: ItemPlantBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val tasksStatisticsAdapter = TasksStatisticsAdapter()

    init {
        binding.rvTasksWithCompletionsNumber.adapter = tasksStatisticsAdapter
    }

    fun bind(plantStatisticsInfo: PlantStatisticsInfo) {
        with(binding) {
            tvPlantName.text = plantStatisticsInfo.plant.name.value

            Glide.with(root.context)
                .load(plantStatisticsInfo.plant.plantPicture)
                .centerCrop()
                .placeholder(R.drawable.placeholder_image)
                .into(ivPlant)
        }

        tasksStatisticsAdapter.submitList(plantStatisticsInfo.tasks)
    }

    companion object {
        fun create(parent: ViewGroup): PlantViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

            return PlantViewHolder(binding)
        }
    }
}
