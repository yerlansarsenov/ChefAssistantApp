package kz.spoonacular.data.model.recipes


import com.google.gson.annotations.SerializedName

data class RecipeData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("imageType")
    val imageType: String,
    @SerializedName("title")
    val title: String
)
