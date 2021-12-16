package com.example.plantsapp.presentation.ui.plantdetail

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentPlantDetailBinding
import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.presentation.ui.plantdetail.adapter.DetailTasksAdapter
import com.example.plantsapp.presentation.ui.plantdetail.adapter.PlantPhotosAdapter
import com.example.plantsapp.presentation.ui.utils.loadPicture
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PlantDetailFragment : Fragment(R.layout.fragment_plant_detail) {

    private val binding: FragmentPlantDetailBinding by viewBinding(FragmentPlantDetailBinding::bind)
    @Inject
    lateinit var assistedFactory: DetailViewModelAssistedFactory
    private val detailViewModel: PlantDetailViewModel by viewModels {
        DetailViewModelFactory(
            assistedFactory = assistedFactory,
            plantName = requireArguments().plantName
        )
    }
    private val tasksAdapter = DetailTasksAdapter()
    private val photosAdapter = PlantPhotosAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(detailViewModel) {
            plant.observe(viewLifecycleOwner) { plant ->
                showPlantDetail(plant)
            }

            toNavigateBack.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }

            tasks.observe(viewLifecycleOwner) {
                tasksAdapter.submitList(it)
            }

            plantPhotos.observe(viewLifecycleOwner) { photos ->
                photosAdapter.submitList(photos)
                binding.clPlantGallery.isVisible = photos.isNotEmpty()
            }
        }

        with(binding) {
            btnDelete.setOnClickListener {
                detailViewModel.onDelete()
            }
        }
    }

    private fun showPlantDetail(plant: Plant) {
        with(binding) {
            ivDetailPlant.loadPicture(plant.plantPicture)

            tvDetailPlantName.text = getString(
                R.string.msg_detail_plant_name,
                plant.name.value
            )
            tvDetailSpeciesName.text = getString(
                R.string.msg_detail_species_name,
                plant.speciesName
            )

            rvDetailTasks.adapter = tasksAdapter
            rvPlantPhotos.adapter = photosAdapter

            btnDelete.isEnabled = true
        }
    }

    companion object {
        private const val ARGUMENT_PLANT_NAME = "PLANT NAME"
        private var Bundle.plantName: Plant.Name
            get() = Plant.Name(
                getString(ARGUMENT_PLANT_NAME, null)
                    ?: error("You forgot to pass ARGUMENT_PLANT_NAME")
            )
            set(plantName) = putString(ARGUMENT_PLANT_NAME, plantName.value)

        fun newInstance(plantName: Plant.Name): PlantDetailFragment {
            val detailFragment = PlantDetailFragment()
            val args = bundleOf()
            args.plantName = plantName
            detailFragment.arguments = args
            return detailFragment
        }
    }
}
