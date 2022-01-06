package com.example.plantsapp.presentation.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksBinding
import com.example.plantsapp.presentation.ui.loading.LoadingDialog
import com.example.plantsapp.presentation.ui.loading.hideOnLifecycle
import com.example.plantsapp.presentation.ui.plantcreation.CameraContract
import com.example.plantsapp.presentation.ui.tasks.adapter.PlantWithTasksAdapter
import com.example.plantsapp.presentation.ui.utils.getCameraImageOutputUri
import com.example.plantsapp.presentation.ui.utils.setViews
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

// TODO - open camera on creation of task screen
@AndroidEntryPoint
class TasksFragment : Fragment(R.layout.fragment_tasks) {
    private val binding: FragmentTasksBinding by viewBinding(FragmentTasksBinding::bind)
    @Inject
    lateinit var assistedFactory: TasksViewModelAssistedFactory
    private val tasksViewModel: TasksViewModel by viewModels {
        TasksViewModelFactory(
            assistedFactory = assistedFactory,
            date = requireArguments().date
        )
    }
    private val loadingDialog by lazy { LoadingDialog(requireContext()) }
    private val plantsWithTasksAdapter = PlantWithTasksAdapter { (plant, task) ->
        tasksViewModel.onCompleteTaskClicked(plant, task)
    }
    private val cameraLauncher =
        registerForActivityResult(
            CameraContract()
        ) { uri ->
            uri?.let {
                tasksViewModel.onImageCaptured(uri)
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog.hideOnLifecycle(viewLifecycleOwner)

        with(tasksViewModel) {
            tasksUiState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is TasksViewModel.TasksUiState.Loading -> {
                        loadingDialog.show()
                    }
                    is TasksViewModel.TasksUiState.InitialState -> {
                        binding.pbLoading.isVisible = true
                    }
                    is TasksViewModel.TasksUiState.DataIsLoaded -> {
                        binding.pbLoading.isVisible = false
                        loadingDialog.dismiss()
                        plantsWithTasksAdapter.submitList(state.plantsWithTasks)

                        setTasksVisibility(areTasksVisible = state.plantsWithTasks.isNotEmpty())
                    }
                }
            }

            launchCamera.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    cameraLauncher.launch(
                        requireContext().getCameraImageOutputUri()
                    )
                }
            }
        }

        with(binding) {
            rvTasks.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = plantsWithTasksAdapter
            }

            layoutNoTasks.setViews(
                imageRes = R.drawable.ic_baseline_calendar_24,
                titleRes = R.string.title_no_tasks,
                messageRes = R.string.msg_no_tasks
            )
        }
    }

    private fun setTasksVisibility(areTasksVisible: Boolean) {
        binding.rvTasks.isVisible = areTasksVisible
        binding.layoutNoTasks.clNoItems.isVisible = !areTasksVisible
    }

    companion object {
        private const val ARGUMENT_DATE = "ARGUMENT_DATE"
        private var Bundle.date: Date
            get() = Date(
                getLong(ARGUMENT_DATE)
            )
            set(date) {
                putLong(ARGUMENT_DATE, date.time)
            }

        fun newInstance(date: Date): TasksFragment {
            val fragment = TasksFragment()
            fragment.arguments = bundleOf().apply {
                this.date = date
            }
            return fragment
        }
    }
}
