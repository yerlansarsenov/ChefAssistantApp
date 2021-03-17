package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */
class RecipeDetailedUseCase(private val repository: RecipeRepository) {
    suspend fun getRecipeById(id: Int) = repository.getRecipeById(id = id)
}