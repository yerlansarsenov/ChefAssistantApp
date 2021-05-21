package kz.spoonacular.domain.repository

import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients
import kz.spoonacular.domain.model.recipes.Recipe

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
interface RecipeRepository {

    suspend fun getRecipes(
        query: String = "",
        cuisine: List<String> = emptyList(),
        type: List<String> = emptyList()
    ): Either<List<Recipe>>

    suspend fun getRecipeById(id: Int): Either<RecipeDetailed>

    suspend fun getRecipesByIngredients(
        cuisine: List<String>,
        type: List<String>,
        vararg ingredients: String
    ): Either<List<RecipeByIngredients>>

    suspend fun getRecipesAutocomplete(query: String): Either<List<Recipe>>

}
