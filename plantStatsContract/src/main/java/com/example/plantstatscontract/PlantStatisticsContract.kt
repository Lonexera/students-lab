package com.example.plantstatscontract

import com.example.plantsapp.domain.model.Plant
import com.example.plantsapp.domain.model.Task
import com.example.plantsapp.domain.model.TaskKeys
import java.util.Date

object PlantStatisticsContract {
    const val AUTHORITY =
        "com.example.plantsapp.PlantsContentProvider"

    object Plants {
        const val PATH = "plants"
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH"
        const val CONTENT_URI = "content://$AUTHORITY/$PATH"

        const val FIELD_NAME = "FIELD_PLANT_NAME"
        const val FIELD_SPECIES_NAME = "FIELD_SPECIES_NAME"
        const val FIELD_PICTURE = "FIELD_PLANT_PICTURE"
    }

    object Tasks {
        const val PATH = "tasks"
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH"
        const val CONTENT_URI = "content://$AUTHORITY/$PATH"

        const val FIELD_TASK_KEY = "FIELD_TASK_KEY"
        const val FIELD_FREQUENCY = "FIELD_TASK_FREQUENCY"
        const val FIELD_LAST_UPDATE_DATE = "FIELD_LAST_UPDATE_DATE"
    }

    object TaskHistory {
        const val PATH = "taskHistory"
        const val CONTENT_TYPE = "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH"
        const val CONTENT_URI = "content://$AUTHORITY/$PATH"

        const val FIELD_COMPLETION_DATE = "FIELD_TASK_COMPLETION_DATE"
    }

    object SelectionArgs {
        private const val ARGUMENT_PLANT_NAME = 0
        private const val ARGUMENT_PLANT_SPECIES_NAME = 1
        private const val ARGUMENT_PLANT_PICTURE = 2

        private const val ARGUMENT_TASK_KEY = 3
        private const val ARGUMENT_TASK_FREQUENCY = 4
        private const val ARGUMENT_TASK_LAST_UPDATE_DATE = 5

        fun putPlantInArgs(plant: Plant): Array<String?> {
            return arrayOf(
                plant.name.value,
                plant.speciesName,
                plant.plantPicture
            )
        }

        fun putPlantAndTaskInArgs(plant: Plant, task: Task): Array<String?> {
            return arrayOf(
                plant.name.value,
                plant.speciesName,
                plant.plantPicture,
                TaskKeys.from(task).key,
                task.frequency.toString(),
                task.lastUpdateDate.time.toString()
            )
        }

        fun Array<out String>.getPlant(): Plant {
            return Plant(
                name = Plant.Name(
                    get(ARGUMENT_PLANT_NAME)
                ),
                speciesName = get(ARGUMENT_PLANT_SPECIES_NAME),
                plantPicture = get(ARGUMENT_PLANT_PICTURE)
            )
        }

        fun Array<out String>.getTask(): Task {
            val taskKey = get(ARGUMENT_TASK_KEY)

            return TaskKeys
                .getFromKey(taskKey)
                .toTask(
                    taskId = 0, // TODO remove id from Task model
                    frequency = get(ARGUMENT_TASK_FREQUENCY).toInt(),
                    lastUpdateDate = Date(
                        get(ARGUMENT_TASK_LAST_UPDATE_DATE).toLong()
                    )
                )
        }
    }
}
