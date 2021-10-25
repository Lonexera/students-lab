package com.example.plantsapp.presentation.ui.plantcreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantCreationBinding
import com.example.plantsapp.presentation.PlantApplication

class PlantCreationFragment : Fragment(R.layout.fragment_plant_creation) {

    private val binding: FragmentPlantCreationBinding by viewBinding(FragmentPlantCreationBinding::bind)
    private val creationViewModel: PlantCreationViewModel by viewModels {
        CreationViewModelFactory(
            repository = (requireActivity().application as PlantApplication)
                .roomPlantsRepository
        )
    }
    private val imagePickerLauncher =
        registerForActivityResult(
            ImagePickerContract()
        ) { uri ->
            uri?.let {
                creationViewModel.onImageSelected(uri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(creationViewModel) {
            toNavigateBack.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            selectedPicture.observe(viewLifecycleOwner) { picture ->
                Glide.with(this@PlantCreationFragment)
                    .load(picture)
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .into(binding.ivCreationPlant)
            }

            wateringFrequencyValues.observe(viewLifecycleOwner) { valuesList ->

                binding.etCreationWateringFrequency.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        valuesList.map {
                            resources.getQuantityString(
                                R.plurals.msg_creation_frequency_units,
                                it,
                                it
                            )
                        }
                    )
                )

                binding.etCreationWateringFrequency.setOnItemClickListener { _, _, position, _ ->
                    creationViewModel.onWateringFrequencySelected(valuesList[position])
                }
                binding.etCreationWateringFrequency.isEnabled = true
            }

            wateringSelectedFrequency.observe(viewLifecycleOwner) {
                binding.etCreationWateringFrequency.setText(it.toString())
            }
        }

        with(binding) {
            btnCreationSave.setOnClickListener {
                creationViewModel.saveData(
                    etCreationPlantName.text.toString(),
                    etCreationSpeciesName.text.toString()
                )
            }
            ivCreationPlant.setOnClickListener {
                imagePickerLauncher.launch(null)
            }
        }
    }
}
