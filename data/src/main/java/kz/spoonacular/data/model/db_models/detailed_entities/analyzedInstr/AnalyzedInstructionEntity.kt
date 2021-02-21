package kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_analinstr")
data class AnalyzedInstruction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val stepsIds: List<Int>
)