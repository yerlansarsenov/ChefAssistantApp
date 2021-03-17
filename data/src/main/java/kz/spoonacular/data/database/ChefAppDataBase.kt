package kz.spoonacular.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.spoonacular.data.model.db_models.RecipesEntity
import kz.spoonacular.data.model.db_models.detailed_entities.RecipeDetailsEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.AnalyzedInstructionEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.EquipmentEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.IngredientEntity
import kz.spoonacular.data.model.db_models.detailed_entities.analyzedInstr.StepEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.ExtendedIngredientEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.MetricEntity
import kz.spoonacular.data.model.db_models.detailed_entities.extendedIngr.UsEntity
import kz.spoonacular.data.model.db_models.detailed_entities.winePairing.ProductMatcheEntity
import kz.spoonacular.data.model.db_models.detailed_entities.winePairing.WinePairingEntity

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */

@Database(entities = [RecipesEntity::class,
                     RecipeDetailsEntity::class,
                     WinePairingEntity::class,
                     ProductMatcheEntity::class,
                     UsEntity::class,
                     MetricEntity::class,
                     ExtendedIngredientEntity::class,
                     StepEntity::class,
                     IngredientEntity::class,
                     EquipmentEntity::class,
                     AnalyzedInstructionEntity::class], version = 1)
abstract class ChefAppDataBase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}