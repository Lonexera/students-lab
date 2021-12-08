package com.example.plantsapp.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.plantsapp.R
import com.example.plantsapp.presentation.ui.authentication.AuthFragment
import com.example.plantsapp.presentation.ui.tasksfordays.TasksForDaysFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(viewModel) {
            navigateToAuth.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    openFragment(AuthFragment())
                }
            }

            navigateToTasks.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let {
                    openFragment(TasksForDaysFragment())
                }
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
        }
    }
}
