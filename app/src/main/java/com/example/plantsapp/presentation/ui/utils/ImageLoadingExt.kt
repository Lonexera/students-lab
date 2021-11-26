package com.example.plantsapp.presentation.ui.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.plantsapp.R

fun <T> ImageView.loadPicture(
    picture: T,
    @DrawableRes placeholder: Int = DEFAULT_PLACEHOLDER
) {
    Glide.with(context)
        .load(picture)
        .disableCache()
        .placeholder(placeholder)
        .into(this)
}

fun Uri?.getBitmapWithPlaceholder(
    context: Context,
    @DrawableRes placeholder: Int = DEFAULT_PLACEHOLDER
): Bitmap {
    return Glide.with(context)
        .asBitmap()
        .disableCache()
        .load(this ?: placeholder)
        .submit()
        .run {
            val bitmap = this.get()
            Glide.with(context).clear(this)

            bitmap
        }
}

private fun <T> RequestBuilder<T>.disableCache(): RequestBuilder<T> {
    return this
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
}

@DrawableRes
private const val DEFAULT_PLACEHOLDER = R.drawable.ic_baseline_image_24
