package com.example.plantsapp.presentation.ui.loading

import android.app.AlertDialog
import android.content.Context
import com.example.plantsapp.R

class LoadingDialog(context: Context) {

    private val alertDialog by lazy {
        AlertDialog.Builder(context)
            .setView(R.layout.dialog_loading)
            .create()
    }

    fun show() {
        alertDialog
            .apply {
                show()
                window?.setLayout(DIALOG_WIDTH, DIALOG_HEIGHT)
            }
    }

    fun dismiss() {
        alertDialog.dismiss()
    }

    companion object {
        private const val DIALOG_WIDTH = 600
        private const val DIALOG_HEIGHT = 300
    }
}
