package com.example.plantsapp.presentation.ui.utils

import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.plantsapp.domain.model.Task

fun ImageView.setColorAndIconFor(task: Task) {
    setImageResource(task.getIconRes())
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            task.getColorRes()
        )
    )
}
