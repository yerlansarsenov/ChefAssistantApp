package kz.spoonacular.domain.model.reciepeDetails.winePairing

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatche>
)
