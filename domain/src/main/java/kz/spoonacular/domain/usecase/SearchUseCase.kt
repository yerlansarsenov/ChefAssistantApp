package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */
class SearchUseCase(private val repository: RecipeRepository) {
    suspend fun getRecipeByQuery(query: String) = repository.getRecipes(query = query)
    suspend fun getRecipeByCuisine(cuisine: List<String>) = repository.getRecipes(cuisine = cuisine)
    suspend fun getRecipeByType(type: List<String>) = repository.getRecipes(type = type)
    suspend fun getRecipeByQueryCuisineAndType(query: String, type: List<String>, cuisine: List<String>)
        = repository.getRecipes(query = query, cuisine = cuisine, type = type)
}