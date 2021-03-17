package kz.spoonacular.data.model.reciepeDetails


import com.google.gson.annotations.SerializedName
import kz.spoonacular.data.model.reciepeDetails.analyzedInstr.AnalyzedInstructionData
import kz.spoonacular.data.model.reciepeDetails.extendedIngr.ExtendedIngredientData
import kz.spoonacular.data.model.reciepeDetails.winePairing.WinePairingData

/**
 * https://api.spoonacular.com/recipes/715497/information?apiKey=4bcf985834f14e98bcf8a6cfe64d6988
 */

data class RecipeDetailedData(
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int?,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstructionData>?,
    @SerializedName("cheap")
    val cheap: Boolean?,
    @SerializedName("cookingMinutes")
    val cookingMinutes: Int?,
    @SerializedName("creditsText")
    val creditsText: String?,
    @SerializedName("cuisines")
    val cuisines: List<Any>?,
    @SerializedName("dairyFree")
    val dairyFree: Boolean?,
    @SerializedName("diets")
    val diets: List<String>?,
    @SerializedName("dishTypes")
    val dishTypes: List<String>?,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredientData>?,
    @SerializedName("gaps")
    val gaps: String?,
    @SerializedName("glutenFree")
    val glutenFree: Boolean?,
    @SerializedName("healthScore")
    val healthScore: Int?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("imageType")
    val imageType: String?,
    @SerializedName("instructions")
    val instructions: String?,                      // todo: text is in html format with tag <ol>list</ol> and <li>list item</li>
    @SerializedName("license")
    val license: String?,
    @SerializedName("lowFodmap")
    val lowFodmap: Boolean?,
    @SerializedName("occasions")
    val occasions: List<Any>?,
    @SerializedName("originalId")
    val originalId: Any?,
    @SerializedName("preparationMinutes")
    val preparationMinutes: Int?,
    @SerializedName("pricePerServing")
    val pricePerServing: Double?,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?,
    @SerializedName("servings")
    val servings: Int?,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String?,
    @SerializedName("spoonacularScore")
    val spoonacularScore: Int?,
    @SerializedName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String?,
    @SerializedName("summary")
    val summary: String?,                     // todo: text is in html format
    @SerializedName("sustainable")
    val sustainable: Boolean?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vegan")
    val vegan: Boolean?,
    @SerializedName("vegetarian")
    val vegetarian: Boolean?,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean?,
    @SerializedName("veryPopular")
    val veryPopular: Boolean?,
    @SerializedName("weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?,
    @SerializedName("winePairing")
    val winePairing: WinePairingData?
)