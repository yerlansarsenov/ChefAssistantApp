package kz.spoonacular.data.model.db_models.detailed_entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */

@Entity(tableName = "recipe_details_entity")
data class RecipeDetailsEntity(
    val analyzedInstructionsIds: List<String>?,
    val cheap: Boolean?,
    val cookingMinutes: Int?,
    val creditsText: String?,
    val cuisines: List<Any>?,
    val dairyFree: Boolean?,   // не содержит молоко
    val diets: List<String>?,
    val dishTypes: List<String>?,
    val extendedIngredientsIds: List<Int>?,
    val glutenFree: Boolean?,  // не содержить глютен
    val healthScore: Int?,
    @PrimaryKey
    val id: Int,
    val image: String?,
    val instructions: String?,  //
    val license: String?,
    val lowFodmap: Boolean?,
    val originalId: Any?,
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
    val winePairingId: String?
)