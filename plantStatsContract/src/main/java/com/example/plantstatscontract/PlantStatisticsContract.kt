package com.example.plantstatscontract

object PlantStatisticsContract {
    const val AUTHORITY =
        "com.example.plantsapp.PlantsContentProvider"

    object Plants {
        const val PATH = "plants"
        const val CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH"
        const val CONTENT_URI =
            "content://$AUTHORITY/$PATH"

        const val FIELD_NAME = "FIELD_PLANT_NAME"
        const val FIELD_SPECIES_NAME = "FIELD_SPECIES_NAME"
        const val FIELD_PICTURE = "FIELD_PLANT_PICTURE"
    }

    object Tasks {
        const val PATH = "tasks"
        const val CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH"
        const val CONTENT_URI =
            "content://$AUTHORITY/$PATH"

        const val FIELD_TASK_KEY = "FIELD_TASK_KEY"
        const val FIELD_FREQUENCY = "FIELD_TASK_FREQUENCY"
        const val FIELD_LAST_UPDATE_DATE = "FIELD_LAST_UPDATE_DATE"
    }

    object TaskHistory {
        const val PATH = "taskHistory"
        const val CONTENT_TYPE =
            "vnd.android.cursor.dir/vnd.$AUTHORITY.$PATH"
        const val CONTENT_URI =
            "content://$AUTHORITY/$PATH"

        const val FIELD_COMPLETION_DATE = "FIELD_TASK_COMPLETION_DATE"
    }
}
