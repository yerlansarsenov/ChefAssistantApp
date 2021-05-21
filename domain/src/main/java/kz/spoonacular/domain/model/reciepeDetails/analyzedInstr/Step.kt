package kz.spoonacular.domain.model.reciepeDetails.analyzedInstr

data class Step(
    val equipments: List<Equipment>,
    val ingredients: List<Ingredient>,
    val number: Int,
    val step: String
)
