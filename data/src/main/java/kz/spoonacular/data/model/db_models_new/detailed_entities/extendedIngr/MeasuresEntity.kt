package kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson

@Entity(tableName = "recipe_details_measures")
data class MeasuresEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @TypeConverters(MetricTypeConverter::class)
    val metric: MetricEntity?,
    @TypeConverters(UsTypeConverter::class)
    val us: UsEntity?
)

class MetricTypeConverter {
    @TypeConverter
    fun listToJson(value: MetricEntity?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, MetricEntity::class.java)
}

class UsTypeConverter {
    @TypeConverter
    fun listToJson(value: UsEntity?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, UsEntity::class.java)
}
