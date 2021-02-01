package kz.spoonacular.data.model.reciepeDetails.analyzedInstr


import com.google.gson.annotations.SerializedName

data class IngredientData(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("localizedName")
    val localizedName: String,
    @SerializedName("name")
    val name: String
)