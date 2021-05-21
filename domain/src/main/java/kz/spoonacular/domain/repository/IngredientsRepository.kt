package kz.spoonacular.domain.repository

import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientsResponse

interface IngredientsRepository {

    suspend fun getIngredientsByQuery(name: String): Either<IngredientsResponse>

}
