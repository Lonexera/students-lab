package com.example.plantsapp.presentation.ui.utils

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.plantsapp.databinding.LayoutNoItemsBinding

fun LayoutNoItemsBinding.setViews(
    @DrawableRes imageRes: Int,
    @StringRes titleRes: Int,
    @StringRes messageRes: Int
) {
    ivNoItemsImage.setImageResource(imageRes)
    tvNoItemsTitle.setText(titleRes)
    tvNoItemsDetail.setText(messageRes)
}
