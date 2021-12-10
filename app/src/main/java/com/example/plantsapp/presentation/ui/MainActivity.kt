package com.example.plantsapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ActivityMainBinding
import com.example.plantsapp.presentation.ui.plants.PlantsFragment
import com.example.plantsapp.presentation.ui.tasksfordays.TasksForDaysFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // TODO maybe find a more proper way to handle bottom navigation visibility
    private val bottomBarVisibilityCallback =
        BottomBarVisibilityCallback { isBottomBarVisible ->
            binding.bottomNavigation.isVisible = isBottomBarVisible
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        supportFragmentManager.registerFragmentLifecycleCallbacks(
            bottomBarVisibilityCallback,
            false
        )

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_nav_tasks -> openFragment(TasksForDaysFragment())
                R.id.bottom_nav_plants -> openFragment(PlantsFragment())
            }
            true
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        supportFragmentManager.unregisterFragmentLifecycleCallbacks(
            bottomBarVisibilityCallback
        )
    }
}
