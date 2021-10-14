package com.example.plantsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.plantsapp.databinding.ActivityMainBinding
import com.example.plantsapp.view.PlantsFragment
import com.example.plantsapp.view.TasksFragment

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
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
