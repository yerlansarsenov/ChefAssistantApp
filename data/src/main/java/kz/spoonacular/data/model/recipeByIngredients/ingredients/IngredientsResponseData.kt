package kz.spoonacular.data.model.recipeByIngredients.ingredients

import com.google.gson.annotations.SerializedName

data class IngredientsResponseData(
    @SerializedName("number")
    val number: Int?,
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("results")
    val results: List<IngredientItemData>,
    @SerializedName("totalResults")
    val totalResults: Int
)
