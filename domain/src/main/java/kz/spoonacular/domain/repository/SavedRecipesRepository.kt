package kz.spoonacular.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipes.Recipe

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */
interface SavedRecipesRepository {

    suspend fun getRecipes(): List<Recipe>

    fun getRecipesFlow(): Flow<List<Recipe>>

    suspend fun getRecipes(types: List<String>, cuisines: List<String>): List<Recipe>

    fun getRecipesFlow(types: List<String>, cuisines: List<String>): Flow<List<Recipe>>

    suspend fun getRecipeById(id: Int): RecipeDetailed?

    suspend fun insertRecipe(recipeDetailed: RecipeDetailed)

    suspend fun deleteRecipe(recipeDetailed: RecipeDetailed)
}