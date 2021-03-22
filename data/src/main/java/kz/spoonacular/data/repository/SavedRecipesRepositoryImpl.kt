package kz.spoonacular.data.repository

import kz.spoonacular.data.database.RecipesDao
import kz.spoonacular.data.mapper.db_mapper.*
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.repository.SavedRecipesRepository

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */
class SavedRecipesRepositoryImpl(
    private val dao: RecipesDao
): SavedRecipesRepository {

    /**
     * implementing mappers using DAO
     */

    private val recipeMapperDB = RecipeMapperDB()

    /**
     * implementing SavedRecipesRepository
     */

    override suspend fun getRecipes(): List<Recipe> {
        return dao.getAllRecipes().map { recipe ->
            recipeMapperDB.mapTo(recipe)
        }
    }

    /**
     * implementing mappers using DAO
     */

    private val wineMapperDB = object : WineMapperDB() {

    }

    private val stepsMapperDB = object : AnalyzedInstrMapperDB.StepsMapperDB() {

    }

    private val analyzedInstrMapperDB = object : AnalyzedInstrMapperDB(
        stepsMapper = stepsMapperDB
    ) {

    }

    private val measuresMapperDB = object : ExtendedIngrMapperDB.MeasuresMapperDB() {

    }

    private val extendedIngrMapperDB = object : ExtendedIngrMapperDB(
        measuresMapper = measuresMapperDB
    ) {

    }

    private val recipeDetailedMapperDB = object : RecipeDetailedMapperDB(
        wineMapper = wineMapperDB,
        analyzedMapper = analyzedInstrMapperDB,
        extendedIngrMapper = extendedIngrMapperDB
    ) {

    }

    /**
     * implementing SavedRecipesRepository
     */

    override suspend fun getRecipeById(id: Int): RecipeDetailed {
        val recipeEntity = dao.getRecipeDetailsById(id)

        return recipeDetailedMapperDB.mapTo(recipeEntity)
    }

    override suspend fun insertRecipe(recipeDetailed: RecipeDetailed) {
        val recipe = Recipe(
            id = recipeDetailed.id,
            image = recipeDetailed.image,
            title = recipeDetailed.title
        )
        dao.insertRecipes(recipeMapperDB.mapFrom(recipe))

        dao.insertRecipeDetails(recipeDetailedMapperDB.mapFrom(recipeDetailed))

//        recipeDetailed.apply {
//            analyzedInstructions.forEach { analyzedInstruction ->
//                dao.insertAnalyzed(analyzedInstrMapperDB.mapFrom(analyzedInstruction))
//                analyzedInstruction.apply {
//                    steps.forEach { step ->
//                        analyzedInstrMapperDB.insertSteps(step)
//                        step.apply {
//                            equipments.forEach { equipment ->
//                                stepsMapperDB.insertEquipment(equipment)
//                            }
//                            ingredients.forEach { ingredient ->
//                                stepsMapperDB.insertIngredient(ingredient)
//                            }
//                        }
//                    }
//                }
//            }
//            extendedIngredients.forEach { extended ->
//                dao.insertExtendedIngr(extendedIngrMapperDB.mapFrom(extended))
//                extended.apply {
//                    extendedIngrMapperDB.insertMeasuredId(measures)
//                    measures.apply {
//                        measuresMapperDB.insertMetric(this.metric)
//                        measuresMapperDB.insertUs(this.us)
//                    }
//                }
//            }
//            winePairing.apply {
//                dao.insertWinePairing(wineMapperDB.mapFrom(this))
//            }
//        }
    }

//    override suspend fun getWinePairingById(id: Int): WinePairing = wineMapperDB.map(dao.getWinePairingById(id))
//
//    override suspend fun getExtendedIngr(id: Int): ExtendedIngredient = extendedIngrMapperDB.map(dao.getExtendedIngrById(id))
//
//    override suspend fun getStepById(id: Int): Step = stepsMapperDB.map(dao.getStepById(id))
//
//    override suspend fun getAnalyzedById(id: Int): AnalyzedInstruction = analyzedInstrMapperDB.map(dao.getAnalyzedById(id))


}