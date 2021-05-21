package kz.spoonacular.data.model.reciepeDetails.winePairing


import com.google.gson.annotations.SerializedName

data class WinePairingData(
    @SerializedName("pairedWines")
    val pairedWines: List<String>?,
    @SerializedName("pairingText")
    val pairingText: String?,
    @SerializedName("productMatches")
    val productMatches: List<ProductMatcheData>?
)
