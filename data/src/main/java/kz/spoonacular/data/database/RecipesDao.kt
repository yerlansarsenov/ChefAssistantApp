package kz.spoonacular.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kz.spoonacular.data.model.db_models_new.RecipesEntity
import kz.spoonacular.data.model.db_models_new.detailed_entities.RecipeDetailsEntity

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
    abstract suspend fun getRecipeDetailsById(id: Int) : RecipeDetailsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecipeDetails(vararg recipes: RecipeDetailsEntity)

    @Query("delete from recipe_details_entity where id = :mId")
    abstract suspend fun deleteRecipeDetails(mId: Int)

}