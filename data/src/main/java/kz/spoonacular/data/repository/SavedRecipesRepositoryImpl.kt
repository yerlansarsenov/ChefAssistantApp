package kz.spoonacular.data.repository

import kz.spoonacular.data.database.RecipesDao
import kz.spoonacular.data.mapper.db_mapper.*
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.AnalyzedInstructionEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.EquipmentEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.IngredientEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.StepEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.ExtendedIngredientEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.MeasuresEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.MetricEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.UsEntity
import kz.spoonacular.data.model.db_models.detailed_entities.winePairing.ProductMatcheEntity
import kz.spoonacular.data.model.db_models.detailed_entities.winePairing.WinePairingEntity
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Equipment
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Ingredient
import kz.spoonacular.domain.model.reciepeDetails.analyzedInstr.Step
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Measures
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Metric
import kz.spoonacular.domain.model.reciepeDetails.extendedIngr.Us
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
        override fun getProductMatcheById(id: Int): ProductMatcheEntity = dao.getProductMatcheById(id)
    }

    private val stepsMapperDB = object : AnalyzedInstrMapperDB.StepsMapperDB() {
        override fun getIngredientsById(id: Int): IngredientEntity = dao.getIngredientById(id)
        override fun getEquipmentsById(id: Int): EquipmentEntity = dao.getEquipmentById(id)
        override fun insertEquipment(equipment: Equipment) {
            dao.insertEquipment(equipmentsMapper.mapFrom(equipment))
        }

        override fun insertIngredient(ingredient: Ingredient) {
            dao.insertIngredientIngr(ingredientsMapper.mapFrom(ingredient))
        }
    }

    private val analyzedInstrMapperDB = object : AnalyzedInstrMapperDB(
        stepsMapper = stepsMapperDB
    ) {
        override fun insertSteps(step: Step): Int {
            return dao.insertStepIngr(stepsMapperDB.mapFrom(step))[0]
        }

        override fun getStepById(id: Int): StepEntity = dao.getStepById(id)
    }

    private val measuresMapperDB = object : ExtendedIngrMapperDB.MeasuresMapperDB() {
        override fun getMetricById(id: Int): MetricEntity = dao.getMetricById(id)
        override fun getUsBuId(id: Int): UsEntity = dao.getUsById(id)
        override fun insertMetric(metric: Metric): Int {
            return dao.insertMetric(metricMapperDB.mapFrom(metric))[0]
        }

        override fun insertUs(us: Us): Int {
            return dao.insertUs(usMapperDB.mapFrom(us))[0]
        }
    }

    private val extendedIngrMapperDB = object : ExtendedIngrMapperDB(
        measuresMapper = measuresMapperDB
    ) {
        override fun getMeasureById(id: Int): MeasuresEntity = dao.getMeasureById(id)

        override fun insertMeasuredId(measures: Measures): Int {
            return dao.insertMeasure(measuresMapperDB.mapFrom(measures))[0]
        }
    }

    private val recipeDetailedMapperDB = object : RecipeDetailedMapperDB(
        wineMapper = wineMapperDB,
        analyzedMapper = analyzedInstrMapperDB,
        extendedIngrMapper = extendedIngrMapperDB
    ) {
        override fun getWineParingById(name: String): WinePairingEntity = dao.getWinePairingById(name)

        override fun getAnalyzedById(id: String): AnalyzedInstructionEntity = dao.getAnalyzedById(id)

        override fun getExtendedById(id: Int): ExtendedIngredientEntity = dao.getExtendedIngrById(id)
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

        recipeDetailed.apply {
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
        }
    }

//    override suspend fun getWinePairingById(id: Int): WinePairing = wineMapperDB.map(dao.getWinePairingById(id))
//
//    override suspend fun getExtendedIngr(id: Int): ExtendedIngredient = extendedIngrMapperDB.map(dao.getExtendedIngrById(id))
//
//    override suspend fun getStepById(id: Int): Step = stepsMapperDB.map(dao.getStepById(id))
//
//    override suspend fun getAnalyzedById(id: Int): AnalyzedInstruction = analyzedInstrMapperDB.map(dao.getAnalyzedById(id))


}