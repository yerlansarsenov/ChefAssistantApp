package kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_extendingr")
data class ExtendedIngredient(
    val aisle: String,
    val amount: Double,
    val consistency: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val measuresId: Int,
    val meta: List<String>,
    val metaInformation: List<String>,
    val name: String,
    val original: String,
    val originalName: String,
    val originalString: String,
    val unit: String
)