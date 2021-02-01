package kz.spoonacular.domain.model.recipeByIngredients

data class RecipeByIngredients(
    val id: Int,
    val image: String,
    val likes: Int,
    val missedIngredientCount: Int,
    val missedIngredients: List<IngredientDetailed>,
    val title: String,
    val unusedIngredients: List<IngredientDetailed>,
    val usedIngredientCount: Int,
    val usedIngredients: List<IngredientDetailed>
)