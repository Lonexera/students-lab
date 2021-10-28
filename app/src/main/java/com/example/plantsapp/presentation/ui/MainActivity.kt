package com.example.plantsapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.plantsapp.R
import com.example.plantsapp.databinding.ActivityMainBinding
import com.example.plantsapp.presentation.ui.plants.PlantsFragment
import com.example.plantsapp.presentation.ui.tasks.TasksFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_nav_tasks -> openFragment(TasksFragment())
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
}
