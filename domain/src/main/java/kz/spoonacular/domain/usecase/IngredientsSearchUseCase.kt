package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.repository.IngredientsRepository

class IngredientsSearchUseCase(
    private val repository: IngredientsRepository
) {
    suspend fun getIngredientsBySearch(name: String) = repository.getIngredientsByQuery(name)
}