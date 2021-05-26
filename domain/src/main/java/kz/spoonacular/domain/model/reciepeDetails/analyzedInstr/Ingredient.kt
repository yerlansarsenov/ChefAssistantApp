package kz.spoonacular.domain.model.reciepeDetails.analyzedInstr

data class Ingredient(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String
) : Inquipment
