package com.example.plantsapp.presentation.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.plantsapp.R
import com.example.plantsapp.databinding.FragmentTasksBinding
import com.example.plantsapp.presentation.ui.tasks.adapter.PlantWithTasksAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date
import javax.inject.Inject

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
    private val plantsWithTasksAdapter = PlantWithTasksAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(tasksViewModel) {
            plantsWithTasks.observe(viewLifecycleOwner) {
                plantsWithTasksAdapter.submitList(it)
            }
        }

        with(binding) {
            rvTasks.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = plantsWithTasksAdapter
            }
        }
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
