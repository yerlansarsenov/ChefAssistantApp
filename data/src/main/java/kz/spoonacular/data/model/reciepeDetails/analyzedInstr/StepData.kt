package kz.spoonacular.data.model.reciepeDetails.analyzedInstr


import com.google.gson.annotations.SerializedName

data class StepData(
    @SerializedName("equipment")
    val equipment: List<EquipmentData>?,
    @SerializedName("ingredients")
    val ingredients: List<IngredientData>?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("step")
    val step: String?
)
