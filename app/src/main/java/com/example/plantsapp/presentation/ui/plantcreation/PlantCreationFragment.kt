package com.example.plantsapp.presentation.ui.plantcreation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantCreationBinding
import com.example.plantsapp.presentation.ui.ViewModelFactory

class PlantCreationFragment : Fragment(R.layout.fragment_plant_creation) {

    private val binding: FragmentPlantCreationBinding by viewBinding(FragmentPlantCreationBinding::bind)
    private val creationViewModel: PlantCreationViewModel by viewModels { ViewModelFactory() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(creationViewModel) {
            toNavigateBack.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }

        with(binding) {
            btnCreationSave.setOnClickListener {
                creationViewModel.saveData(
                    binding.etCreationPlantName.text.toString(),
                    binding.etCreationSpeciesName.text.toString(),
                    binding.etCreationWateringFrequency.text.toString()
                )
            }
        }
    }
}
