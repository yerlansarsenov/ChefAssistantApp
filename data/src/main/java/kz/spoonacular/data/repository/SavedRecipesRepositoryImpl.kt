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
import kz.spoonacular.domain.repository.SavedRecipesRepository

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */
class RecipeRepositoryImplDB(
    private val dao: RecipesDao
): SavedRecipesRepository {

    private val recipeMapperDB = object: RecipeMapperDB() {

    }

    private val wineMapperDB = object : WineMapperDB() {
        override fun getProductMatcheById(id: Int): ProductMatcheEntity {
            TODO("Not yet implemented")
        }
    }

    private val stepsMapperDB = object : AnalyzedInstrMapperDB.StepsMapperDB() {
        override fun getIngredientsById(id: Int): IngredientEntity {
            TODO("Not yet implemented")
        }

        override fun getEquipmentsById(id: Int): EquipmentEntity {
            TODO("Not yet implemented")
        }
    }

    private val analyzedInstrMapperDB = object : AnalyzedInstrMapperDB(
        stepsMapper = stepsMapperDB
    ) {
        override fun getStepById(id: Int): StepEntity {
            TODO("Not yet implemented")
        }
    }

    private val measuresMapperDB = object : ExtendedIngrMapperDB.MeasuresMapperDB() {
        override fun getMetricById(id: Int): MetricEntity {
            TODO("Not yet implemented")
        }

        override fun getUsBuId(id: Int): UsEntity {
            TODO("Not yet implemented")
        }

    }

    private val extendedIngrMapperDB = object : ExtendedIngrMapperDB(
        measuresMapper = measuresMapperDB
    ) {
        override fun getMeasureById(id: Int): MeasuresEntity {
            TODO("Not yet implemented")
        }

    }

    private val recipeDetailedMapperDB = object : RecipeDetailedMapperDB(
        wineMapper = wineMapperDB,
        analyzedMapper = analyzedInstrMapperDB,
        extendedIngrMapper = extendedIngrMapperDB
    ) {
        override fun getWineParingById(id: Int): WinePairingEntity {
            TODO("Not yet implemented")
        }
        override fun getAnalyzedById(id: Int): AnalyzedInstructionEntity {
            TODO("Not yet implemented")
        }
        override fun getExtendedById(id: Int): ExtendedIngredientEntity {
            TODO("Not yet implemented")
        }
    }

    override suspend fun getRecipes(): List<Recipe> {
        return dao.getAllRecipes().map { recipe ->
            recipeMapperDB.map(recipe)
        }
    }

    override suspend fun getRecipeById(id: Int): RecipeDetailed {
        return recipeDetailedMapperDB.map(dao.getRecipeDetailsById(id))
    }

    override suspend fun getWinePairingById(id: Int): WinePairing = wineMapperDB.map(dao.getWinePairingById(id))

    override suspend fun getProductMatcheById(id: Int): ProductMatche {
        TODO("Not yet implemented")
    }

    override suspend fun getUsById(id: Int): Us {
        TODO("Not yet implemented")
    }

    override suspend fun getMetricById(id: Int): Metric {
        TODO("Not yet implemented")
    }

    override suspend fun getExtendedIngr(id: Int): ExtendedIngredient {
        TODO("Not yet implemented")
    }

    override suspend fun getStepById(id: Int): Step {
        TODO("Not yet implemented")
    }

    override suspend fun getIngredientById(id: Int): Ingredient {
        TODO("Not yet implemented")
    }

    override suspend fun getEquipmentById(id: Int): Equipment {
        TODO("Not yet implemented")
    }

    override suspend fun getAnalyzedById(id: Int): AnalyzedInstruction {
        TODO("Not yet implemented")
    }


}