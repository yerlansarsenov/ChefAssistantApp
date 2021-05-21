package kz.spoonacular.domain.model.recipes

data class RecipesResponse(
    val number: Int,
    val offset: Int,
    val results: List<Recipe>,
    val totalResults: Int
)
