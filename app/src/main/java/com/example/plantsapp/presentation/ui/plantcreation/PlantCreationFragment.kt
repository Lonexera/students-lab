package com.example.plantsapp.presentation.ui.plantcreation

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(creationViewModel) {
            toNavigateBack.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            openGallery.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { requestCode ->
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    requireActivity().startActivityFromFragment(
                        this@PlantCreationFragment,
                        intent,
                        requestCode
                    )
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        creationViewModel.onGalleryActivityResult(requestCode, resultCode, data)
    }
}
