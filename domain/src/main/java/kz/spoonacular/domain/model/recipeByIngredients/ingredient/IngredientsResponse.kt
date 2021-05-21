package kz.spoonacular.domain.model.recipeByIngredients.ingredient

data class IngredientsResponse(
    val number: Int,
    val offset: Int,
    val results: List<IngredientItem>,
    val totalResults: Int
)
