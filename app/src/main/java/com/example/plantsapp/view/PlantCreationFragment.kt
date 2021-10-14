package com.example.plantsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.plantsapp.databinding.FragmentPlantCreationBinding
import timber.log.Timber

class PlantCreationFragment : Fragment() {

    private var _binding: FragmentPlantCreationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantCreationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnCreationSave.setOnClickListener { collectData() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectData() {
        with(binding) {
            Timber.d("Plant name: ${etCreationPlantName.text}")
            Timber.d("Plant species name: ${etCreationSpeciesName.text}")
            Timber.d("Watering frequency in days: ${etCreationWateringFrequency.text}")
        }
    }
}