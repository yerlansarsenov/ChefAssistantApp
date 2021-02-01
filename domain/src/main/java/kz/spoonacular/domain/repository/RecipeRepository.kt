package kz.spoonacular.domain.repository

import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients
import kz.spoonacular.domain.model.recipes.Recipe

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
interface RecipeRepository {

    suspend fun getRecipesFromServer(query: String = "", cuisine: String = "", type: String = ""): Either<List<Recipe>>

    suspend fun getRecipeByIdFromServer(id: Int): Either<RecipeDetailed>

    suspend fun getRecipesByIngredientsFromServer(vararg ingredients: String): Either<List<RecipeByIngredients>>

}