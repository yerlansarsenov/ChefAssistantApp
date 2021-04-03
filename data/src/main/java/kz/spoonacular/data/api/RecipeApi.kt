package kz.spoonacular.data.api

import kz.spoonacular.data.model.reciepeDetails.RecipeDetailedData
import kz.spoonacular.data.model.recipeByIngredients.RecipeByIngredientsData
import kz.spoonacular.data.model.recipes.RecipeAutocomplete
import kz.spoonacular.data.model.recipes.RecipesResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
interface RecipeApi {
    // https://api.spoonacular.com/recipes/complexSearch?apiKey=
    @GET("recipes/complexSearch")
    suspend fun getRecipesBySearch(
        @Query("query") query: String,
        @Query("cuisine") cuisine: String,
        @Query("type") type: String) : Response<RecipesResponseData>

    /**
     * --- TYPES:
     * main course
     * side dish
     * dessert
     * appetizer
     * salad
     * bread
     * breakfast
     * soup
     * beverage
     * sauce
     * marinade
     * fingerfood
     * snack
     * drink
     */

    /**
     *
     * ---CUISINES:
     * African
     * American
     * British
     * Cajun
     * Caribbean
     * Chinese
     * Eastern European
     * European
     * French
     * German
     * Greek
     * Indian
     * Irish
     * Italian
     * Japanese
     * Jewish
     * Korean
     * Latin American
     * Mediterranean
     * Mexican
     * Middle Eastern
     * Nordic
     * Southern
     * Spanish
     * Thai
     * Vietnamese
     */

    // https://api.spoonacular.com/recipes/715497/information?apiKey=
    @GET("recipes/{id}/information")
    suspend fun getInfoRecipeById(@Path("id") id: Int) : Response<RecipeDetailedData>

    // https://api.spoonacular.com/recipes/findByIngredients?apiKey=&ingredients=apples,flour,sugar
    @GET("recipes/findByIngredients")
    suspend fun getRecipesByIngredients(@Query("ingredients") args: String) : Response<List<RecipeByIngredientsData>>

    // https://api.spoonacular.com/recipes/autocomplete?query=chick&apiKey=
    @GET("recipes/autocomplete")
    suspend fun getRecipesAutocomplete(@Query("autocomplete") query: String) : Response<List<RecipeAutocomplete>>
}