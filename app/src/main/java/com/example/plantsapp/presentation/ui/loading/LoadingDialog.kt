package com.example.plantsapp.presentation.ui.loading

import android.app.AlertDialog
import android.content.Context
import android.util.TypedValue
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.plantsapp.R

class LoadingDialog(context: Context) {

    private val alertDialog =
        AlertDialog.Builder(context)
            .setView(R.layout.dialog_loading)
            .create()
            .apply { setCanceledOnTouchOutside(false) }

    fun show() {
        alertDialog.apply {
            show()
            window?.setLayout(
                DIALOG_WIDTH_DP.toPx(context),
                DIALOG_HEIGHT_DP.toPx(context)
            )
        }
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    private fun Int.toPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            toFloat(),
            context.resources.displayMetrics
        )
            .toInt()
    }

    companion object {
        private const val DIALOG_WIDTH_DP = 270
        private const val DIALOG_HEIGHT_DP = 110
    }
}

fun LoadingDialog.connectWith(
    isLoadingLiveData: LiveData<Boolean>,
    lifecycleOwner: LifecycleOwner
) {
    isLoadingLiveData.observe(lifecycleOwner) { isLoading ->
        when (isLoading) {
            true -> show()
            false -> dismiss()
        }
    }
}
