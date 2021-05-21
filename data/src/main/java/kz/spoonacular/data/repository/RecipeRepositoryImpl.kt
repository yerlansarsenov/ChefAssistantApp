package kz.spoonacular.data.repository

import android.util.Log
import kz.spoonacular.data.api.RecipeApi
import kz.spoonacular.data.mapper.RecipeAutocompleteMapper
import kz.spoonacular.data.mapper.RecipeByIngrMapper
import kz.spoonacular.data.mapper.RecipeDetailedMapper
import kz.spoonacular.data.mapper.RecipeMapper
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.repository.RecipeRepository

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */

private const val TAG = "RecipeRepositoryImpl"

class RecipeRepositoryImpl(
    private val api: RecipeApi,
    private val recipeMapper: RecipeMapper,
    private val recipeDetailedMapper: RecipeDetailedMapper,
    private val recipeByIngrMapper: RecipeByIngrMapper,
    private val recipeAutocompleteMapper: RecipeAutocompleteMapper
): RecipeRepository {


    override suspend fun getRecipes(
        query: String,
        cuisine: List<String>,
        type: List<String>
    ): Either<List<Recipe>> {
        try {
            val response = api.getRecipesBySearch(query, cuisine.toStringArgs(), type.toStringArgs())
            if (response.isSuccessful) {
                if (response.body() == null || response.body()!!.number == null)
                    return Either.Error(response.message())
                if (response.body()!!.results.isEmpty())
                    return Either.Error("Nothing found :(")
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

    override suspend fun getRecipeById(id: Int): Either<RecipeDetailed> {
        try {
            val response = api.getInfoRecipeById(id)
            if (response.isSuccessful) {
                if (response.body() == null || response.body()!!.title == null)
                    return Either.Error(response.message())
                return Either.Success(recipeDetailedMapper.map(response.body()!!))
            }
            return Either.Error(response.message())
        } catch (e: Exception) {
            return Either.Error(e.message.toString())
        }
    }

    override suspend fun getRecipesByIngredients(
        cuisine: List<String>,
        type: List<String>,
        vararg ingredients: String
    ): Either<List<RecipeByIngredients>> {
        try {
            val response = api.getRecipesByIngredients(
                getArgsSequence(args = ingredients),
                type = type.toStringArgs(),
                cuisine = cuisine.toStringArgs()
            )
            if (response.isSuccessful) {
                if (response.body() == null)
                    return Either.Error(response.message())
                if (response.body()!!.isEmpty())
                    return Either.Error("Nothing found :(")
                return Either.Success(response.body()!!.map { recipeByIngrMapper.map(it) })
            }
            return Either.Error(response.message())
        } catch (e: Exception) {
            Log.e(TAG, "getRecipesByIngredients: ${e.message.toString()}")
            return Either.Error(e.message.toString())
        }
    }

    override suspend fun getRecipesAutocomplete(query: String): Either<List<Recipe>> {
        try {
            val response = api.getRecipesAutocomplete(query = query)
            if (response.isSuccessful) {
                if (response.body() == null)
                    return Either.Error(response.message())
                if (response.body()!!.isEmpty())
                    return Either.Error("Nothing found :(")
                return Either.Success(response.body()!!.map { recipeAutocompleteMapper.map(it) })
            }
            return Either.Error(response.message())
        } catch (e: Exception) {
            return Either.Error(e.message.toString())
        }
    }

    private fun List<String>.toStringArgs(): String {
        var answer = ""
        for (s in this) {
            answer = "$answer,$s"
        }
        return answer
    }

    private fun getArgsSequence(vararg args: String) : String{
        var answer = ""
        for (s in args) {
            answer = "$answer,$s"
        }
        return answer
    }
}
