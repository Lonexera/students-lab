package com.example.plantsapp.presentation.ui.plantcreation

import com.example.plantsapp.R
import org.junit.Assert.assertEquals
import org.junit.Test

class PlantCreationValidatorTest {

    private val defaultFrequencies = PlantCreationViewModel.PlantTaskFrequencies(null, null, null, null)

    private fun PlantCreationValidator.validateDefault(
        plantName: String = "",
        speciesName: String = "",
        frequencies: PlantCreationViewModel.PlantTaskFrequencies = defaultFrequencies
    ) = validate(plantName, speciesName, frequencies)

    @Test
    fun `validator return error when all fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault()

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when name field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            speciesName = "tjf",
            frequencies = defaultFrequencies.copy(
                wateringFrequency = 1,
                sprayingFrequency = 3,
                looseningFrequency = 5,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when species name field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            plantName = "Jackob",
            frequencies = defaultFrequencies.copy(
                wateringFrequency = 3,
                sprayingFrequency = 4,
                looseningFrequency = 1,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_species)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when watering field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            plantName = "Jackob",
            speciesName = "Cactus",
            frequencies = defaultFrequencies.copy(
                sprayingFrequency = 3,
                looseningFrequency = 5,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_watering_frequency)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when spraying field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            plantName = "Jackob",
            speciesName = "Cactus",
            frequencies = defaultFrequencies.copy(
                wateringFrequency = 4,
                looseningFrequency = 5,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_spraying_frequency)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when loosening field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            plantName = "Jackob",
            speciesName = "Cactus",
            frequencies = defaultFrequencies.copy(
                wateringFrequency = 3,
                sprayingFrequency = 3,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_loosening_frequency)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when name and species name fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            frequencies = defaultFrequencies.copy(
                wateringFrequency = 2,
                sprayingFrequency = 4,
                looseningFrequency = 22,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when species name and frequency fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            plantName = "Jackob"
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_species)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when watering and name fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validateDefault(
            speciesName = "Cactus",
            frequencies = defaultFrequencies.copy(
                sprayingFrequency = 3,
                looseningFrequency = 4,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return success when all fields are not empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "Cactus",
            frequencies = defaultFrequencies.copy(
                wateringFrequency = 3,
                sprayingFrequency = 4,
                looseningFrequency = 12,
                takingPhotoFrequency = 2
            )
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Success
        assertEquals(validatorResult, expectedResult)
    }
}
