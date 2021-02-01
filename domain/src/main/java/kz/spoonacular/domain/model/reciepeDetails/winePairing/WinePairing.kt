package kz.spoonacular.domain.model.reciepeDetails.winePairing

import kz.spoonacular.domain.model.reciepeDetails.winePairing.ProductMatche

data class WinePairing(
    val pairedWines: List<String>,
    val pairingText: String,
    val productMatches: List<ProductMatche>
)