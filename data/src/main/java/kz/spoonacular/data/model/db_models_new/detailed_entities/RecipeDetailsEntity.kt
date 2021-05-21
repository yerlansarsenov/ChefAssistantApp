package kz.spoonacular.data.model.db_models_new.detailed_entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.AnalyzedInstructionEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr.ExtendedIngredientEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing.WinePairingEntity

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */

@Entity(tableName = "recipe_details_entity")
data class RecipeDetailsEntity(
    @TypeConverters(AnalyzedInstrTypeConverter::class)
    val analyzedInstructions: List<AnalyzedInstructionEntity>?,
    val cheap: Boolean?,
    val cookingMinutes: Int?,
    val creditsText: String?,
    val cuisines: List<Any>?,
    val dairyFree: Boolean?,   // не содержит молоко
    @TypeConverters(StringsTypeConverter::class)
    val diets: List<String>?,
    @TypeConverters(StringsTypeConverter::class)
    val dishTypes: List<String>?,
    @TypeConverters(ExtendedIngrTypeConverter::class)
    val extendedIngredients: List<ExtendedIngredientEntity>?,
    val glutenFree: Boolean?,  // не содержить глютен
    val healthScore: Int?,
    @PrimaryKey
    val id: Int,
    val image: String?,
    val instructions: String?,  //
    val license: String?,
    val lowFodmap: Boolean?,
    @TypeConverters(AnyTypeConverter::class)
    val original: Any?,
    val preparationMinutes: Int?,
    val pricePerServing: Double?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceName: String?,
    val sourceUrl: String?,
    val spoonacularScore: Int?,
    val spoonacularSourceUrl: String?,
    val summary: String?,
    val sustainable: Boolean?,
    val title: String?,
    val vegan: Boolean?,
    val vegetarian: Boolean?,
    val veryHealthy: Boolean?,
    val veryPopular: Boolean?,
    val weightWatcherSmartPoints: Int?,
    @TypeConverters(WinePairingTypeConverter::class)
    val winePairing: WinePairingEntity?
)

class AnalyzedInstrTypeConverter {
    @TypeConverter
    fun listToJson(value: List<AnalyzedInstructionEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<AnalyzedInstructionEntity>::class.java).toList()
}

class CuisinesTypeConverter {
    @TypeConverter
    fun listToJson(value: List<Any>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Any>::class.java).toList()
}

class StringsTypeConverter {
    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}

class ExtendedIngrTypeConverter {
    @TypeConverter
    fun listToJson(value: List<ExtendedIngredientEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ExtendedIngredientEntity>::class.java).toList()
}

class WinePairingTypeConverter {
    @TypeConverter
    fun listToJson(value: WinePairingEntity?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, WinePairingEntity::class.java)
}

class AnyTypeConverter {
    @TypeConverter
    fun listToJson(value: Any?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Any::class.java)
}
