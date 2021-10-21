package com.example.plantsapp.presentation.ui.plantcreation

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            creationViewModel.onGalleryResult(it)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(creationViewModel) {
            toNavigateBack.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            openGallery.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    getContent.launch("image/*")
                }
            }

            selectedPicture.observe(viewLifecycleOwner) { picture ->
                Glide.with(this@PlantCreationFragment)
                    .load(picture)
                    .into(binding.ivCreationPlant)
            }
        }

        with(binding) {
            btnCreationSave.setOnClickListener {
                creationViewModel.saveData(
                    etCreationPlantName.text.toString(),
                    etCreationSpeciesName.text.toString(),
                    etCreationWateringFrequency.text.toString()
                )
            }
            ivCreationPlant.setOnClickListener {
                creationViewModel.onImageClicked()
            }
        }
    }
}
