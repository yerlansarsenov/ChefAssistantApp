package kz.spoonacular.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.spoonacular.data.model.db_models_new.RecipesEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.*
import kz.spoonacular.data.model.db_models_new.detailed_entities.analyzedInstr.*
import kz.spoonacular.data.model.db_models_new.detailed_entities.extendedIngr.*
import kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing.ProductMatcheEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing.ProductMatchesTypeConverter
import kz.spoonacular.data.model.db_models_new.detailed_entities.winePairing.WinePairingEntity

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
@TypeConverters(value = [StringsTypeConverter::class,
                        AnalyzedInstrTypeConverter::class,
                        CuisinesTypeConverter::class,
                        ExtendedIngrTypeConverter::class,
                        WinePairingTypeConverter::class,
                        StepTypeConverter::class,
                        EquipmentTypeConverter::class,
                        IngredientTypeConverter::class,
                        MeasuresTypeConverter::class,
                        MetricTypeConverter::class,
                        UsTypeConverter::class,
                        ProductMatchesTypeConverter::class,
                        AnyTypeConverter::class])
abstract class ChefAppDataBase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}
