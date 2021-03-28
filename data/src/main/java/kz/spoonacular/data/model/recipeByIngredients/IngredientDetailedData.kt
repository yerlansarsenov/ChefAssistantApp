package kz.spoonacular.data.model.recipeByIngredients


import com.google.gson.annotations.SerializedName

data class IngredientDetailedData(
    @SerializedName("aisle")
    val aisle: String?,
    @SerializedName("amount")
    val amount: Float,
    @SerializedName("extendedName")
    val extendedName: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("meta")
    val meta: List<String>,
    @SerializedName("metaInformation")
    val metaInformation: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("originalName")
    val originalName: String,
    @SerializedName("originalString")
    val originalString: String,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("unitLong")
    val unitLong: String,
    @SerializedName("unitShort")
    val unitShort: String
)