package com.example.plantsapp.presentation.ui.plantcreation

import com.example.plantsapp.R
import org.junit.Assert.assertEquals
import org.junit.Test

class PlantCreationValidatorTest {

    @Test
    fun `validator return error when all fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "",
            wateringFrequency = null
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when name field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "tjf",
            wateringFrequency = 9
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when species name field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "",
            wateringFrequency = 9
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_species)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when watering field is empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "Cactus",
            wateringFrequency = null
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_watering_frequency)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when name and species name fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "",
            wateringFrequency = 5
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when species name and watering fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "",
            wateringFrequency = null
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_species)
        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun `validator return error when watering and name fields are empty`() {
        val validator = PlantCreationValidator()

        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "Cactus",
            wateringFrequency = null
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
            wateringFrequency = 3
        )

        val expectedResult = PlantCreationValidator.ValidatorOutput.Success
        assertEquals(validatorResult, expectedResult)
    }
}
