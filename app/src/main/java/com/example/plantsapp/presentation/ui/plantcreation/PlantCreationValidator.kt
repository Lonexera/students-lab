package com.example.plantsapp.presentation.ui.plantcreation

import androidx.annotation.StringRes
import com.example.plantsapp.R

class PlantCreationValidator {

    fun validate(
        plantName: String,
        speciesName: String,
        frequency: Int?
    ): ValidatorOutput {
        return when {
            plantName.isBlank() -> ValidatorOutput.Error(R.string.error_invalid_name)
            speciesName.isBlank() -> ValidatorOutput.Error(R.string.error_invalid_species)
            frequency == null -> ValidatorOutput.Error(R.string.error_invalid_watering_frequency)
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
