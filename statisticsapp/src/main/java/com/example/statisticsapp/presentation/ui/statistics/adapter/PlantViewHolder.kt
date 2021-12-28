package com.example.statisticsapp.presentation.ui.statistics.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.domain.model.Plant
import com.example.statisticsapp.databinding.ItemPlantBinding
import com.example.statisticsapp.presentation.ui.statistics.StatisticsViewModel

class PlantViewHolder(
    private val binding: ItemPlantBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plantStatisticsInfo: StatisticsViewModel.PlantStatisticsInfo) {
        with(binding) {
            tvPlantName.text = plantStatisticsInfo.plant.name.value

            rvTasksWithCompletionsNumber.adapter = TasksStatisticsAdapter()
                .apply {
                    submitList(plantStatisticsInfo.tasks)
                }
        }
    }

    companion object {
        fun create(parent: ViewGroup): PlantViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemPlantBinding.inflate(layoutInflater, parent, false)

            return PlantViewHolder(binding)
        }
    }
}
