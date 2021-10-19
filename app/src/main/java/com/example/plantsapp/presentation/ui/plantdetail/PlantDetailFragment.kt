package com.example.plantsapp.presentation.ui.plantdetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantDetailBinding
import com.example.plantsapp.domain.model.Plant

class PlantDetailFragment : Fragment(R.layout.fragment_plant_detail) {

    private val binding: FragmentPlantDetailBinding by viewBinding(FragmentPlantDetailBinding::bind)
    private val detailViewModel: PlantDetailViewModel by activityViewModels {
        DetailViewModelFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(detailViewModel) {
            plant.observe(viewLifecycleOwner) {
                showPlantDetail(it)
            }
        }
    }

    private fun showPlantDetail(plant: Plant) {
        with(binding) {
            Glide.with(requireContext())
                .load(plant.plantPicture)
                .into(ivDetailPlant)

            tvDetailPlantName.text = plant.name
            tvDetailSpeciesName.text = plant.speciesName
            tvDetailWateringText.text = getString(
                R.string.msg_detail_watering_text,
                plant.wateringFrequencyDays
            )
        }
    }
}
