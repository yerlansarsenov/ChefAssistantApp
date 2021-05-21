package kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_metric")
data class MetricEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val amount: Double?,
    val unitLong: String?,
    val unitShort: String?
)
