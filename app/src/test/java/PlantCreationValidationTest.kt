import com.example.plantsapp.R
import com.example.plantsapp.presentation.ui.plantcreation.PlantCreationValidator
import org.junit.Assert.assertEquals
import org.junit.Test

class PlantCreationValidationTest {

    @Test
    fun emptyFields() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "",
            frequency = null
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun emptyName() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "tjf",
            frequency = 9
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun emptySpeciesName() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "",
            frequency = 9
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_species)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun emptyWatering() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "Cactus",
            frequency = null
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_watering_frequency)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun emptyNameAndSpecies() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "",
            frequency = 5
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun emptySpeciesAndWatering() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "",
            frequency = null
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_species)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun emptyWateringAndName() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "",
            speciesName = "Cactus",
            frequency = null
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Error(R.string.error_invalid_name)

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun allGood() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "Jackob",
            speciesName = "Cactus",
            frequency = 3
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Success

        assertEquals(validatorResult, expectedResult)
    }

    @Test
    fun uglyNames() {
        val validator = PlantCreationValidator()
        val validatorResult = validator.validate(
            plantName = "te4t./.e4t",
            speciesName = ".,;3wwt",
            frequency = 1
        )
        val expectedResult = PlantCreationValidator.ValidatorOutput.Success

        assertEquals(validatorResult, expectedResult)
    }
}
