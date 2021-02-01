package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */
class SearchUseCase(private val repository: RecipeRepository) {
    suspend fun getRecipeByQuery(query: String) = repository.getRecipesFromServer(query = query)
    suspend fun getRecipeByCuisine(cuisine: String) = repository.getRecipesFromServer(cuisine = cuisine)
    suspend fun getRecipeByType(type: String) = repository.getRecipesFromServer(type = type)
}