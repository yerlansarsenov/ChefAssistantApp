package kz.spoonacular.data.model.recipeByIngredients.ingredients

import com.google.gson.annotations.SerializedName

data class IngredientItemData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
)
