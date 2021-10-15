package com.example.plantsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantDetailBinding
import com.example.plantsapp.model.Plant
import com.example.plantsapp.viewModels.PlantDetailViewModel

class PlantDetailFragment : Fragment() {

    private var _binding: FragmentPlantDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: PlantDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(detailViewModel) {
            plant.observe(viewLifecycleOwner) {
                showPlantDetail(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
