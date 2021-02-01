package kz.spoonacular.data.model.reciepeDetails.extendedIngr


import com.google.gson.annotations.SerializedName

data class MeasuresData(
    @SerializedName("metric")
    val metric: MetricData,
    @SerializedName("us")
    val us: UsData
)