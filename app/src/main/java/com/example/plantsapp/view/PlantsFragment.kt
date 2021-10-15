package com.example.plantsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.plantsapp.R
import com.example.plantsapp.adapter.PlantsAdapter
import com.example.plantsapp.databinding.FragmentPlantsBinding
import com.example.plantsapp.viewModels.PlantsViewModel

class PlantsFragment : Fragment() {

    private var _binding: FragmentPlantsBinding? = null
    private val binding get() = _binding!!
    private val plantsViewModel: PlantsViewModel by viewModels()
    private val plantsAdapter = PlantsAdapter {
        plantsViewModel.onPlantClicked(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(plantsViewModel) {
            plants.observe(viewLifecycleOwner) {
                plantsAdapter.submitList(it)
            }

            clickedPlant.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    openFragment(PlantDetailFragment())
                }
            }

            isAddPlantClicked.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { isAddBtnClicked ->
                    if (isAddBtnClicked) {
                        openFragment(PlantCreationFragment())
                    }
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(fragment.tag)
            .commit()
    }
}
