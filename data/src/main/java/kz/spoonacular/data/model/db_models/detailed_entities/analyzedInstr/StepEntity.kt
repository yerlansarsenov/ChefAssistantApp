package kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_step")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val equipmentId: List<Int>?,
    val ingredientsId: List<Int>?,
    val number: Int?,
    val step: String?
)