package kz.spoonacular.data.model.recipes


import com.google.gson.annotations.SerializedName

/**
 * https://api.spoonacular.com/recipes/complexSearch?apiKey=4bcf985834f14e98bcf8a6cfe64d6988
 */

data class RecipesResponseData(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<RecipeData>,
    @SerializedName("totalResults")
    val totalResults: Int,
)