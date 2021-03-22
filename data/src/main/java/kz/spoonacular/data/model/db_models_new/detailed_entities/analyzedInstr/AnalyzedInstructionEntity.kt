package kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

@Entity(tableName = "recipe_details_analinstr")
data class AnalyzedInstructionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String?,
    @TypeConverters(StepTypeConverter::class)
    val steps: List<StepEntity>?
)

class StepTypeConverter {
    @TypeConverter
    fun listToJson(value: List<StepEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<StepEntity>::class.java).toList()
}