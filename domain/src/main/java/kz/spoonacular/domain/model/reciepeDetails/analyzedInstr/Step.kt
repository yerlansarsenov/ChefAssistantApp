package kz.spoonacular.domain.model.reciepeDetails.analyzedInstr

import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Equipment
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Ingredient

data class Step(
    val equipments: List<Equipment>,
    val ingredients: List<Ingredient>,
    val number: Int,
    val step: String
)