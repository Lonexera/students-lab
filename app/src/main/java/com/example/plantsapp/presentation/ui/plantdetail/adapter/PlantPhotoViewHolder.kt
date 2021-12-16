package com.example.plantsapp.presentation.ui.plantdetail.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.databinding.ItemPlantPhotoBinding
import com.example.plantsapp.presentation.ui.utils.loadPicture
import java.util.Date

class PlantPhotoViewHolder(
    private val binding: ItemPlantPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(photoUri: Uri, creationDate: Date) {
        with(binding) {
            ivPlantPhoto.loadPicture(photoUri)

            tvPhotoCreationDate.text = creationDate.toString() // TODO format date
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): PlantPhotoViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                ItemPlantPhotoBinding.inflate(inflater, parent, false)

            return PlantPhotoViewHolder(binding)
        }
    }
}
