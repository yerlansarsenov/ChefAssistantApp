package kz.spoonacular.data.repository

import kz.spoonacular.data.api.RecipeApi
import kz.spoonacular.data.mapper.RecipeByIngrMapper
import kz.spoonacular.data.mapper.RecipeDetailedMapper
import kz.spoonacular.data.mapper.RecipeMapper
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.model.recipes.RecipesResponse
import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val recipeMapper: RecipeMapper,
    private val recipeDetailedMapper: RecipeDetailedMapper,
    private val recipeByIngrMapper: RecipeByIngrMapper
): RecipeRepository {
    override suspend fun getRecipesFromServer(
        query: String,
        cuisine: String,
        type: String
    ): Either<List<Recipe>> {
        try {
            val response = api.getRecipesBySearch(query, cuisine, type)
            if (response.isSuccessful) {
                if (response.body() == null || response.body()!!.number == null)
                    return Either.Error(response.message())
                val list = response.body()!!.results.map {
                    recipeMapper.map(it)
                }
                return Either.Success(list)
            }
            return Either.Error(response.message())
        } catch (e: Exception) {
            return Either.Error(e.message.toString())
        }
    }

    override suspend fun getRecipeByIdFromServer(id: Int): Either<RecipeDetailed> {
        try {
            val response = api.getInfoRecipeById(id)
            if (response.isSuccessful) {
                if (response.body() == null || response.body()!!.id == null)
                    return Either.Error(response.message())
                return Either.Success(recipeDetailedMapper.map(response.body()!!))
            }
            return Either.Error(response.message())
        } catch (e: Exception) {
            return Either.Error(e.message.toString())
        }
    }

    override suspend fun getRecipesByIngredientsFromServer(vararg ingredients: String): Either<List<RecipeByIngredients>> {
        try {
            val response = api.getRecipesByIngredients(getIngredientsSequence(args = ingredients))
            if (response.isSuccessful) {
                if (response.body()!!.isNullOrEmpty())
                    return Either.Error(response.message())
                return Either.Success(response.body()!!.map { recipeByIngrMapper.map(it) })
            }
            return Either.Error(response.message())
        } catch (e: Exception) {
            return Either.Error(e.message.toString())
        }
    }

    private fun getIngredientsSequence(vararg args: String) : String{
        var answer = ""
        for (s in args) {
            answer = "$answer,$s"
        }
        return answer
    }
}