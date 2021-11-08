package com.example.plantsapp.presentation.ui.utils

import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

fun <T> AutoCompleteTextView.setUpWithAdapter(
    adapter: ArrayAdapter<String>,
    valuesList: List<T>,
    onItemClick: (T) -> Unit
) {
    setAdapter(adapter)

    setOnItemClickListener { _, _, position, _ ->
        onItemClick(valuesList[position])
    }

    isEnabled = true
}
