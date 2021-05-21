package kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

@Entity(tableName = "recipe_details_step")
data class StepEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @TypeConverters(EquipmentTypeConverter::class)
    val equipments: List<EquipmentEntity>?,
    @TypeConverters(IngredientTypeConverter::class)
    val ingredients: List<IngredientEntity>?,
    val number: Int?,
    val step: String?
)

class EquipmentTypeConverter {
    @TypeConverter
    fun listToJson(value: List<EquipmentEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<EquipmentEntity>::class.java).toList()
}

class IngredientTypeConverter {
    @TypeConverter
    fun listToJson(value: List<IngredientEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<IngredientEntity>::class.java).toList()
}
