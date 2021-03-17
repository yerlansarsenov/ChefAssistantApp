package kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_measures")
data class MeasuresEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val metricId: Int?,
    val usId: Int?
)