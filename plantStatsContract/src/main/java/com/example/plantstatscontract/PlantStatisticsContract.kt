package com.example.plantstatscontract

private const val AUTHORITY =
    "com.example.plantsapp.PlantsContentProvider"
private const val PLANTS_PATH = "plants"
const val CONTENT_TYPE =
    "vnd.android.cursor.dir/vnd.$AUTHORITY.$PLANTS_PATH"
const val CONTENT_URI =
    "content://$AUTHORITY/$PLANTS_PATH"

const val FIELD_PLANT_NAME = "FIELD_PLANT_NAME"
const val FIELD_SPECIES_NAME = "FIELD_SPECIES_NAME"
const val FIELD_PLANT_PICTURE = "FIELD_PLANT_PICTURE"
