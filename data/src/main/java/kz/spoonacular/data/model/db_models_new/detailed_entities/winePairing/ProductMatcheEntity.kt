package kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_details_productmatche")
data class ProductMatcheEntity(
    val averageRating: Double?,
    val description: String?,
    @PrimaryKey
    val id: Int,
    val imageUrl: String?,
    val link: String?,
    val price: String?,
    val ratingCount: Double?,
    val score: Double?,
    val title: String?
)