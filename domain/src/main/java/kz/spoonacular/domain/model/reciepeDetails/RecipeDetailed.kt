package kz.spoonacular.domain.model.reciepeDetails

import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.AnalyzedInstruction
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.ExtendedIngredient
import kz.spoonacular.domain.model.reciepeDetails.winePairing.WinePairing

data class RecipeDetailed(
    val analyzedInstructions: List<AnalyzedInstruction>,
    val cheap: Boolean,
    val cookingMinutes: Int,
    val creditsText: String,
    val cuisines: List<Any>,
    val dairyFree: Boolean,   // не содержит молоко
    val diets: List<String>,
    val dishTypes: List<String>,
    val extendedIngredients: List<ExtendedIngredient>,
    val glutenFree: Boolean,  // не содержить глютен
    val healthScore: Int,
    val id: Int,
    val image: String,
    val instructions: String,  //
    val license: String,
    val lowFodmap: Boolean,
    val originalId: Any?,
    val preparationMinutes: Int,
    val pricePerServing: Double,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceName: String,
    val sourceUrl: String,
    val spoonacularScore: Int,
    val spoonacularSourceUrl: String,
    val summary: String,
    val sustainable: Boolean,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
    val veryPopular: Boolean,
    val weightWatcherSmartPoints: Int,
    val winePairing: WinePairing
)