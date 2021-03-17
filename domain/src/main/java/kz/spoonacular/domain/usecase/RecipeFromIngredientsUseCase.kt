package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */
class RecipeFromIngredientsUseCase(private val repository: RecipeRepository) {
    suspend fun getRecipeFromIngredients(vararg ingredients: String) = repository.getRecipesByIngredients(ingredients = ingredients)
}