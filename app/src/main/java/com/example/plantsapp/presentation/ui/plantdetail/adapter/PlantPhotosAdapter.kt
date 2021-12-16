package com.example.plantsapp.presentation.ui.plantdetail.adapter

import android.net.Uri
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.plantsapp.presentation.model.PlantPhotoWithDateItemCallback
import java.util.Date

class PlantPhotosAdapter
    : ListAdapter<Pair<Uri, Date>, PlantPhotoViewHolder>(PlantPhotoWithDateItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantPhotoViewHolder {
        return PlantPhotoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PlantPhotoViewHolder, position: Int) {
        val (photoUri, creationDate) = getItem(position)
        holder.bind(
            photoUri = photoUri,
            creationDate = creationDate
        )
    }
}
