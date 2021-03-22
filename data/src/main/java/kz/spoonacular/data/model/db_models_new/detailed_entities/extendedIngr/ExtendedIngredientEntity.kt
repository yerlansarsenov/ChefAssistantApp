package kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import kz.spoonacular.data.model.db_models_new.detailed_entities.StringsTypeConverter
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.AnalyzedInstructionEntity

@Entity(tableName = "recipe_details_extendingr")
data class ExtendedIngredientEntity(
    val aisle: String?,
    val amount: Double?,
    val consistency: String?,
    @PrimaryKey
    val id: Int,
    val image: String?,
    @TypeConverters(MeasuresTypeConverter::class)
    val measures: MeasuresEntity?,
    @TypeConverters(StringsTypeConverter::class)
    val meta: List<String>?,
    @TypeConverters(StringsTypeConverter::class)
    val metaInformation: List<String>?,
    val name: String?,
    val original: String?,
    val originalName: String?,
    val originalString: String?,
    val unit: String?
)

class MeasuresTypeConverter {
    @TypeConverter
    fun listToJson(value: MeasuresEntity?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, MeasuresEntity::class.java)
}

