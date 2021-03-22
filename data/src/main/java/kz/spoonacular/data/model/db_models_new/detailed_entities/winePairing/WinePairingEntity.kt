package kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import kz.spoonacular.data.model.db_models_new.detailed_entities.StringsTypeConverter
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.AnalyzedInstructionEntity


@Entity(tableName = "recipe_details_winepairing")
data class WinePairingEntity(
    @TypeConverters(StringsTypeConverter::class)
    val pairedWines: List<String>?,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val pairingText: String?,
    @TypeConverters(ProductMatchesTypeConverter::class)
    val productMatches: List<ProductMatcheEntity>?
)

class ProductMatchesTypeConverter {
    @TypeConverter
    fun listToJson(value: List<ProductMatcheEntity>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<ProductMatcheEntity>::class.java).toList()
}