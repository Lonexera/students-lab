package com.example.plantsapp.presentation.ui.plantcreation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantCreationBinding
import com.example.plantsapp.presentation.PlantApplication
import timber.log.Timber

class PlantCreationFragment : Fragment(R.layout.fragment_plant_creation) {

    private val binding: FragmentPlantCreationBinding by viewBinding(FragmentPlantCreationBinding::bind)
    private val creationViewModel: PlantCreationViewModel by viewModels {
        CreationViewModelFactory(
            repository = (requireActivity().application as PlantApplication)
                .roomPlantsRepository,
            validator = PlantCreationValidator()
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
    private val cameraLauncher =
        registerForActivityResult(
            CameraContract()
        ) { uri ->
            Timber.d("cameraPictureUri - ${uri.toString()}")
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpViews()
    }

    private fun setUpObservers() {
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

            invalidInput.observe(viewLifecycleOwner) { errorStringId ->
                Toast.makeText(
                    requireContext(),
                    getString(errorStringId),
                    Toast.LENGTH_SHORT
                ).show()
            }

            wateringFrequencyValues.observe(viewLifecycleOwner) { valuesList ->
                val wateringValueList = valuesList.map {
                    resources.getQuantityString(
                        R.plurals.msg_creation_frequency_units, it, it
                    )
                }

                binding.etCreationWateringFrequency.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        wateringValueList
                    )
                )

                binding.etCreationWateringFrequency.setOnItemClickListener { _, _, position, _ ->
                    creationViewModel.onWateringFrequencySelected(valuesList[position])
                }
                binding.etCreationWateringFrequency.isEnabled = true
            }

            wateringSelectedFrequency.observe(viewLifecycleOwner) {
                binding.etCreationWateringFrequency.setText(
                    resources.getQuantityString(
                        R.plurals.msg_creation_frequency_units, it, it
                    )
                )
            }
        }
    }

    private fun setUpViews() {
        with(binding) {
            btnCreationSave.setOnClickListener {
                creationViewModel.saveData(
                    etCreationPlantName.text.toString(),
                    etCreationSpeciesName.text.toString()
                )
            }
            ivCreationPlant.setOnClickListener {
                showDialogIntentPicker()
            }
        }
    }

    private fun showDialogIntentPicker() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_intent_picker_dialog)
            .setPositiveButton(R.string.title_intent_picker_dialog_btn_gallery) { _, _ ->
                imagePickerLauncher.launch(null)
            }
            .setNegativeButton(R.string.title_intent_picker_dialog_btn_camera) { _, _ ->
                cameraLauncher.launch(null)
            }
            .setNeutralButton(R.string.title_intent_picker_dialog_btn_cancel) { dialog, _, ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
