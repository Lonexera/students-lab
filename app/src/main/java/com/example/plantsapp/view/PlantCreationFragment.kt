package com.example.plantsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.plantsapp.databinding.FragmentPlantCreationBinding
import com.example.plantsapp.viewModels.PlantCreationViewModel

class PlantCreationFragment : Fragment() {

    private var _binding: FragmentPlantCreationBinding? = null
    private val binding get() = _binding!!
    private val creationViewModel: PlantCreationViewModel by viewModels()

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

        with(creationViewModel) {
            toSaveData.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { toSaveData: Boolean ->
                    if (toSaveData) {
                        creationViewModel.saveData(
                            binding.etCreationPlantName.text.toString(),
                            binding.etCreationSpeciesName.text.toString(),
                            binding.etCreationWateringFrequency.text.toString()
                        )
                    }
                }
            }

            toNavigateBack.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { toBack: Boolean ->
                    if (toBack) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        }

        with(binding) {
            btnCreationSave.setOnClickListener {
                creationViewModel.onSaveClicked()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
