package kz.spoonacular.data.model.reciepeDetails.analyzedInstr


import com.google.gson.annotations.SerializedName

data class AnalyzedInstructionData(
    @SerializedName("name")
    val name: String?,
    @SerializedName("steps")
    val steps: List<StepData>?
)