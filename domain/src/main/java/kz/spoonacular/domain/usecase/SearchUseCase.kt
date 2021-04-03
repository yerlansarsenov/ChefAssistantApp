package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */
class SearchUseCase(private val repository: RecipeRepository) {
    suspend fun getRecipeByQueryCuisineAndType(query: String, type: List<String>, cuisine: List<String>)
        = repository.getRecipes(query = query, cuisine = cuisine, type = type)
    suspend fun getRecipeAutocomplete(query: String) = repository.getRecipesAutocomplete(query = query)
}