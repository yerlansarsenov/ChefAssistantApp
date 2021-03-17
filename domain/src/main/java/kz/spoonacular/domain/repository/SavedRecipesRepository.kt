package kz.spoonacular.domain.repository

import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.AnalyzedInstruction
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Equipment
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Ingredient
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Step
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.ExtendedIngredient
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Metric
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Us
import kz.spoonacular.domain.model.reciepeDetails.winePairing.ProductMatche
import kz.spoonacular.domain.model.reciepeDetails.winePairing.WinePairing
import kz.spoonacular.domain.model.recipes.Recipe

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */
interface SavedRecipesRepository {

    suspend fun getRecipes(): List<Recipe>

    suspend fun getRecipeById(id: Int): RecipeDetailed

//    suspend fun getWinePairingById(id: Int): WinePairing
//
//    suspend fun getExtendedIngr(id: Int): ExtendedIngredient
//
//    suspend fun getStepById(id: Int): Step
//
//    suspend fun getAnalyzedById(id: Int): AnalyzedInstruction

    suspend fun insertRecipe(recipeDetailed: RecipeDetailed)
}