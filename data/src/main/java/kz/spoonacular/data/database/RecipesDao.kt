package kz.spoonacular.data.database

import androidx.room.*
import kz.spoonacular.data.model.db_models.RecipesEntity
import kz.spoonacular.data.model.db_models.detailed_entities.RecipeDetailsEntity
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

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */

@Dao
abstract class RecipesDao {

    /**
     * recipe
     */

    @Query("select * from recipes_saved")
    abstract suspend fun getAllRecipes(): List<RecipesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipes(vararg recipes: RecipesEntity)

    @Query("delete from recipes_saved where id = :mId")
    abstract suspend fun deleteRecipe(mId: Int)

    /**
     * recipe details
     */

    @Query("select * from recipe_details_entity where id = :id")
    abstract suspend fun getRecipeDetailsById(id: Int) : RecipeDetailsEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipeDetails(vararg recipes: RecipeDetailsEntity)

    @Query("delete from recipe_details_entity where id = :mId")
    abstract suspend fun deleteRecipeDetails(mId: Int)

    /**
     * r d winepairing
     */

    @Query("select * from recipe_details_winepairing where pairingText = :name")
    abstract fun getWinePairingById(name: String) : WinePairingEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertWinePairing(vararg winePairingEntity: WinePairingEntity)

    @Query("delete from recipe_details_winepairing where pairingText = :name")
    abstract suspend fun deleteWinePairing(name: String)

    /**
     * r d product matche
     */

    @Query("select * from recipe_details_productmatche where id = :id")
    abstract fun getProductMatcheById(id: Int) : ProductMatcheEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertProductMatche(vararg productMatcheEntity: ProductMatcheEntity)

    @Query("delete from recipe_details_productmatche where id = :id")
    abstract suspend fun deleteProductMatcheById(id: Int)

    /**
     * r d us
     */

    @Query("select * from recipe_details_us where id = :id")
    abstract fun getUsById(id: Int) : UsEntity

    @Insert
    abstract fun insertUs(vararg usEntity: UsEntity): List<Int>

    @Query("delete from recipe_details_us where id = :id")
    abstract suspend fun deleteUsById(id: Int)

    /**
     * r d metric
     */

    @Query("select * from recipe_details_metric where id = :id")
    abstract fun getMetricById(id: Int) : MetricEntity

    @Insert
    abstract fun insertMetric(vararg metricEntity: MetricEntity) : List<Int>

    @Query("delete from recipe_details_metric where id = :id")
    abstract suspend fun deleteMetricById(id: Int)

    /**
     * r d measure
     */

    @Query("select * from recipe_details_measures where id = :id")
    abstract fun getMeasureById(id: Int) : MeasuresEntity

    @Insert
    abstract fun insertMeasure(vararg measuresEntity: MeasuresEntity): List<Int>

    @Query("delete from recipe_details_measures where id = :id")
    abstract suspend fun deleteMeasureById(id: Int)

    /**
     * r d extended ingr
     */

    @Query("select * from recipe_details_extendingr where id = :id")
    abstract fun getExtendedIngrById(id: Int) : ExtendedIngredientEntity

    @Insert
    abstract suspend fun insertExtendedIngr(vararg extendedIngredientEntity: ExtendedIngredientEntity)

    @Query("delete from recipe_details_extendingr where id = :id")
    abstract suspend fun deleteExtendedIngrById(id: Int)

    /**
     * r d step
     */

    @Query("select * from recipe_details_step where id = :id")
    abstract fun getStepById(id: Int) : StepEntity

    @Insert
    abstract fun insertStepIngr(vararg stepEntity: StepEntity): List<Int>

    @Query("delete from recipe_details_step where id = :id")
    abstract suspend fun deleteStepIngrById(id: Int)

    /**
     * r d ingredient
     */

    @Query("select * from recipe_details_ingredient where id = :id")
    abstract fun getIngredientById(id: Int) : IngredientEntity

    @Insert
    abstract fun insertIngredientIngr(vararg ingredientEntity: IngredientEntity)

    @Query("delete from recipe_details_ingredient where id = :id")
    abstract suspend fun deleteIngredientById(id: Int)

    /**
     * r d equipment
     */

    @Query("select * from recipe_details_equipment where id = :id")
    abstract fun getEquipmentById(id: Int) : EquipmentEntity

    @Insert
    abstract fun insertEquipment(vararg equipmentEntity: EquipmentEntity)

    @Query("delete from recipe_details_equipment where id = :id")
    abstract suspend fun deleteEquipmentById(id: Int)

    /**
     * r d analyzed
     */

    @Query("select * from recipe_details_analinstr where name = :name")
    abstract fun getAnalyzedById(name: String) : AnalyzedInstructionEntity

    @Insert
    abstract suspend fun insertAnalyzed(vararg analyzedInstructionEntity: AnalyzedInstructionEntity)

    @Query("delete from recipe_details_analinstr where id = :id")
    abstract suspend fun deleteAnalyzedById(id: Int)

}