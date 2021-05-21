package kz.spoonacular.data.api

import kotlinx.coroutines.Deferred
import kz.spoonacular.data.model.recipeByIngredients.ingredients.IngredientsResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientsApi {
    @GET("food/ingredients/search")
    suspend fun getIngredientsBySearchAsync(@Query("query") name: String) : Response<IngredientsResponseData>
}
