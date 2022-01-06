package com.example.plantsapp.presentation.ui.plants

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.presentation.ui.plants.adapter.PlantsAdapter
import com.example.plantsapp.databinding.FragmentPlantsBinding
import com.example.plantsapp.presentation.ui.plantcreation.PlantCreationFragment
import com.example.plantsapp.presentation.ui.plantdetail.PlantDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlantsFragment : Fragment(R.layout.fragment_plants) {

    private val binding: FragmentPlantsBinding by viewBinding(FragmentPlantsBinding::bind)
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val plantsAdapter = PlantsAdapter {
        plantsViewModel.onPlantClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle(R.string.title_plants_screen)

        with(plantsViewModel) {
            filteredPlants.observe(viewLifecycleOwner) {
                plantsAdapter.submitList(it)
                setPlantsVisibility(arePlantsVisible = it.isNotEmpty())
            }

            clickedPlant.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { plant ->
                    openFragment(
                        PlantDetailFragment.newInstance(plant.name)
                    )
                }
            }

            toCreation.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    openFragment(PlantCreationFragment())
                }
            }
        }

        with(binding) {
            rvPlants.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = plantsAdapter
            }

            fabAddPlant.setOnClickListener {
                plantsViewModel.onAddPlantClicked()
            }

            searchViewPlants.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String): Boolean {
                    plantsViewModel.onFilterChanged(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    searchViewPlants.clearFocus()
                    return true
                }
            })
        }
    }

    private fun setPlantsVisibility(arePlantsVisible: Boolean) {
        binding.rvPlants.isVisible = arePlantsVisible
        binding.clNoPlants.isVisible = !arePlantsVisible
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }
}
