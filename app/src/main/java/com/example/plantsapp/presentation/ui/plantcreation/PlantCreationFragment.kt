package com.example.plantsapp.presentation.ui.plantcreation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantCreationBinding
import com.example.plantsapp.presentation.PlantApplication
import com.example.plantsapp.presentation.ui.utils.getCameraImageOutputUri

class PlantCreationFragment : Fragment(R.layout.fragment_plant_creation) {

    private val binding: FragmentPlantCreationBinding by viewBinding(FragmentPlantCreationBinding::bind)
    private val creationViewModel: PlantCreationViewModel by viewModels {
        CreationViewModelFactory(
            plantsRepository = (requireActivity().application as PlantApplication)
                .roomPlantsRepository,
            tasksRepository = (requireActivity().application as PlantApplication)
                .roomTasksRepository,
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
            uri?.let {
                creationViewModel.onImageSelected(uri)
            }
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

            frequencyValues.observe(viewLifecycleOwner) {
                setUpFrequencyViews(it)
            }

            frequencies.observe(viewLifecycleOwner) { frequenciesModel ->
                frequenciesModel.wateringFrequency?.let {
                    binding.etCreationWateringFrequency.setTextWithUnits(it)
                }
                frequenciesModel.sprayingFrequency?.let {
                    binding.etCreationSprayingFrequency.setTextWithUnits(it)
                }
                frequenciesModel.looseningFrequency?.let {
                    binding.etCreationLooseningFrequency.setTextWithUnits(it)
                }
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

    private fun setUpFrequencyViews(valuesList: List<Int>) {
        val wateringValueList = valuesList.map {
            resources.getQuantityString(
                R.plurals.msg_creation_frequency_units, it, it
            )
        }

        val frequencyAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            wateringValueList
        )

        binding.etCreationWateringFrequency.setUpWithAdapter(
            frequencyAdapter,
            valuesList,
            creationViewModel::onWateringFrequencySelected
        )

        binding.etCreationSprayingFrequency.setUpWithAdapter(
            frequencyAdapter,
            valuesList,
            creationViewModel::onSprayingFrequencySelected
        )

        binding.etCreationLooseningFrequency.setUpWithAdapter(
            frequencyAdapter,
            valuesList,
            creationViewModel::onLooseningFrequencySelected
        )
    }

    private fun <T> AutoCompleteTextView.setUpWithAdapter(
        adapter: ArrayAdapter<T>,
        valuesList: List<Int>,
        onItemClick: (Int) -> Unit
    ) {
        setAdapter(adapter)

        setOnItemClickListener { _, _, position, _ ->
            onItemClick(valuesList[position])
        }

        isEnabled = true
    }

    private fun AutoCompleteTextView.setTextWithUnits(value: Int) {
        setText(
            resources.getQuantityString(
                R.plurals.msg_creation_frequency_units, value, value
            ),
            false
        )
    }

    private fun showDialogIntentPicker() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.title_intent_picker_dialog)
            .setPositiveButton(R.string.title_intent_picker_dialog_btn_gallery) { _, _ ->
                imagePickerLauncher.launch(null)
            }
            .setNegativeButton(R.string.title_intent_picker_dialog_btn_camera) { _, _ ->
                cameraLauncher.launch(
                    requireContext().getCameraImageOutputUri()
                )
            }
            .setNeutralButton(R.string.title_intent_picker_dialog_btn_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
