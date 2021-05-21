package kz.spoonacular.data.model.recipeByIngredients


import com.google.gson.annotations.SerializedName

data class RecipeByIngredientsData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("missedIngredientCount")
    val missedIngredientCount: Int,
    @SerializedName("missedIngredients")
    val missedIngredients: List<IngredientDetailedData>,
    @SerializedName("title")
    val title: String,
    @SerializedName("unusedIngredients")
    val unusedIngredients: List<IngredientDetailedData>,
    @SerializedName("usedIngredientCount")
    val usedIngredientCount: Int,
    @SerializedName("usedIngredients")
    val usedIngredients: List<IngredientDetailedData>
)
