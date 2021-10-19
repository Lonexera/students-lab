package com.example.plantsapp.presentation.ui.plantdetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.data.PlantsRepositoryImpl
import com.example.plantsapp.databinding.FragmentPlantDetailBinding
import com.example.plantsapp.domain.model.Plant

class PlantDetailFragment : Fragment(R.layout.fragment_plant_detail) {

    private val binding: FragmentPlantDetailBinding by viewBinding(FragmentPlantDetailBinding::bind)
    private val detailViewModel: PlantDetailViewModel by viewModels {
        DetailViewModelFactory(
            repository = PlantsRepositoryImpl,
            plantName = Plant.PlantName(requireArguments().plantName)
        )
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

            tvDetailPlantName.text = plant.name.value
            tvDetailSpeciesName.text = plant.speciesName
            tvDetailWateringText.text = getString(
                R.string.msg_detail_watering_text,
                plant.wateringFrequencyDays
            )
        }
    }

    companion object {
        private const val ARGUMENT_PLANT_NAME = "PLANT NAME"
        private var Bundle.plantName: String
            get() = getString(ARGUMENT_PLANT_NAME, null)
            ?: error("You forgot to pass ARGUMENT_PLANT_NAME")
            set(value) = putString(ARGUMENT_PLANT_NAME, value)

        fun newInstance(plantName: Plant.PlantName): PlantDetailFragment {
            val detailFragment = PlantDetailFragment()
            val args = bundleOf()
            args.plantName = plantName.value
            detailFragment.arguments = args
            return detailFragment
        }
    }
}
