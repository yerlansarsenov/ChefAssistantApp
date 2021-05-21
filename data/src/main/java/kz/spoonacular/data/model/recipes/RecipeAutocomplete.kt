package kz.spoonacular.data.model.recipes


import com.google.gson.annotations.SerializedName

data class RecipeAutocomplete(
    @SerializedName("id")
    val id: Int,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("title")
    val title: String
)
