package com.example.plantsapp.presentation.ui.plantcreation

import androidx.annotation.StringRes
import com.example.plantsapp.R

class PlantCreationValidator {

    fun validate(
        plantName: String,
        speciesName: String,
        wateringFrequency: Int?,
        sprayingFrequency: Int?,
        looseningFrequency: Int?
    ): ValidatorOutput {
        return when {
            plantName.isBlank() -> ValidatorOutput.Error(R.string.error_invalid_name)
            speciesName.isBlank() -> ValidatorOutput.Error(R.string.error_invalid_species)
            wateringFrequency == null -> ValidatorOutput.Error(R.string.error_invalid_watering_frequency)
            sprayingFrequency == null -> ValidatorOutput.Error(R.string.error_invalid_spraying_frequency)
            looseningFrequency == null -> ValidatorOutput.Error(R.string.error_invalid_loosening_frequency)
            else -> ValidatorOutput.Success
        }
    }

    sealed class ValidatorOutput {
        object Success : ValidatorOutput()
        data class Error(
            @StringRes val errorMessageRes: Int
        ) : ValidatorOutput()
    }
}
