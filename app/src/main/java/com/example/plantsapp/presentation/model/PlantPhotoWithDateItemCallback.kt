package com.example.plantsapp.presentation.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.Date

// TODO look through those comparing functions
class PlantPhotoWithDateItemCallback : DiffUtil.ItemCallback<Pair<Uri, Date>>() {

    override fun areItemsTheSame(oldItem: Pair<Uri, Date>, newItem: Pair<Uri, Date>): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Pair<Uri, Date>, newItem: Pair<Uri, Date>): Boolean {
        return oldItem == newItem
    }
}
