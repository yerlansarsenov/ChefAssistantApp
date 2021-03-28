package kz.spoonacular.data.repository

import kz.spoonacular.data.api.IngredientsApi
import kz.spoonacular.data.mapper.IngredientsResponseMapper
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientsResponse
import kz.spoonacular.domain.repository.IngredientsRepository

class IngredientsRepositoryImpl(
    val api: IngredientsApi,
    val mapper: IngredientsResponseMapper
): IngredientsRepository {
    override suspend fun getIngredientsByQuery(name: String): Either<IngredientsResponse> {
        try {
            val response = api.getIngredientsBySearchAsync(name)
            if (response.isSuccessful) {
                response.body().apply {
                    return if (this != null && this.number != null) {
                        Either.Success(mapper.map(this))
                    } else {
                        Either.Error(response.message())
                    }
                }
            } else {
                return Either.Error(response.message())
            }
        } catch (e: Exception) {
            return Either.Error(e.message.toString())
        }
    }
}