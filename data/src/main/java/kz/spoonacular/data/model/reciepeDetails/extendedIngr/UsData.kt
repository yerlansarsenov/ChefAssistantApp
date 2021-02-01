package kz.spoonacular.data.model.reciepeDetails.extendedIngr


import com.google.gson.annotations.SerializedName

data class UsData(
    @SerializedName("amount")
    val amount: Double,
    @SerializedName("unitLong")
    val unitLong: String,
    @SerializedName("unitShort")
    val unitShort: String
)