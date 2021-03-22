package kz.spoonacular.domain.usecase

import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.AnalyzedInstruction
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Step
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.ExtendedIngredient
import kz.spoonacular.domain.model.reciepeDetails.winePairing.WinePairing
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.repository.SavedRecipesRepository

/**
 * Created by Sarsenov Yerlan on 18.02.2021.
 */
class SavedRecipesUseCase (
    private val repository: SavedRecipesRepository
) {
    suspend fun getSavedRecipes(): List<Recipe> = repository.getRecipes()

    suspend fun getSavedRecipeById(id: Int): RecipeDetailed? = repository.getRecipeById(id)

    suspend fun insertRecipe(recipeDetailed: RecipeDetailed) = repository.insertRecipe(recipeDetailed = recipeDetailed)
}