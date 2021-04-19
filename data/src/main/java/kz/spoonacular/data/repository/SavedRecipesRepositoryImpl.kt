package kz.spoonacular.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kz.spoonacular.data.database.RecipesDao
import kz.spoonacular.data.mapper.db_mapper.*
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.repository.SavedRecipesRepository

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */
class SavedRecipesRepositoryImpl(
    private val dao: RecipesDao,
    private val recipeDetailedToRecipeMapperDB: RecipeDetailedToRecipeMapperDB
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

    override fun getRecipesFlow(): Flow<List<Recipe>> {
        return dao.getAllRecipesFlow().map { list ->
            list.map { recipe ->
                recipeMapperDB.mapTo(recipe)
            }
        }
    }

    override suspend fun getRecipes(types: List<String>, cuisines: List<String>): List<Recipe> {
        if (types.isEmpty() && cuisines.isEmpty())
            return getRecipes()
        return dao.getAllRecipesDetailed().map { recipe ->
            recipeDetailedMapperDB.mapTo(recipe)
        }.filter { recipe ->
            types.intersect(recipe.dishTypes).isNotEmpty() || cuisines.intersect(recipe.cuisines).isNotEmpty()
        }.map { recipe ->
            recipeDetailedToRecipeMapperDB.map(recipe)
        }
    }

    override fun getRecipesFlow(types: List<String>, cuisines: List<String>): Flow<List<Recipe>> {
        if (types.isEmpty() && cuisines.isEmpty())
            return getRecipesFlow()
        return dao.getAllRecipesDetailedFlow().map { list ->
            list.map { recipe ->
                recipeDetailedMapperDB.mapTo(recipe)
            }.filter { recipe ->
                types.intersect(recipe.dishTypes).isNotEmpty() || cuisines.intersect(recipe.cuisines).isNotEmpty()
            }.map { recipe ->
                recipeDetailedToRecipeMapperDB.map(recipe)
            }
        }
    }

    /**
     * implementing mappers using DAO
     */

    private val wineMapperDB = object : WineMapperDB() { }

    private val stepsMapperDB = object : AnalyzedInstrMapperDB.StepsMapperDB() { }

    private val analyzedInstrMapperDB = object : AnalyzedInstrMapperDB(
        stepsMapper = stepsMapperDB
    ) { }

    private val measuresMapperDB = object : ExtendedIngrMapperDB.MeasuresMapperDB() { }

    private val extendedIngrMapperDB = object : ExtendedIngrMapperDB(
        measuresMapper = measuresMapperDB
    ) { }

    private val recipeDetailedMapperDB = object : RecipeDetailedMapperDB(
        wineMapper = wineMapperDB,
        analyzedMapper = analyzedInstrMapperDB,
        extendedIngrMapper = extendedIngrMapperDB
    ) { }

    /**
     * implementing SavedRecipesRepository
     */

    override suspend fun getRecipeById(id: Int): RecipeDetailed? {
        val recipeEntity = dao.getRecipeDetailsById(id) ?: return null
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

/*        recipeDetailed.apply {
            analyzedInstructions.forEach { analyzedInstruction ->
                dao.insertAnalyzed(analyzedInstrMapperDB.mapFrom(analyzedInstruction))
                analyzedInstruction.apply {
                    steps.forEach { step ->
                        analyzedInstrMapperDB.insertSteps(step)
                        step.apply {
                            equipments.forEach { equipment ->
                                stepsMapperDB.insertEquipment(equipment)
                            }
                            ingredients.forEach { ingredient ->
                                stepsMapperDB.insertIngredient(ingredient)
                            }
                        }
                    }
                }
            }
            extendedIngredients.forEach { extended ->
                dao.insertExtendedIngr(extendedIngrMapperDB.mapFrom(extended))
                extended.apply {
                    extendedIngrMapperDB.insertMeasuredId(measures)
                    measures.apply {
                        measuresMapperDB.insertMetric(this.metric)
                        measuresMapperDB.insertUs(this.us)
                    }
                }
            }
            winePairing.apply {
                dao.insertWinePairing(wineMapperDB.mapFrom(this))
            }
        }*/
    }

    override suspend fun deleteRecipe(recipeDetailed: RecipeDetailed) {
        dao.deleteRecipeDetails(recipeDetailed.id)
        dao.deleteRecipe(recipeDetailed.id)
    }

}