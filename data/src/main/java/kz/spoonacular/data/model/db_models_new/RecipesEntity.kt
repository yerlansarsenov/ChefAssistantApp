package kz.spoonacular.data.model.db_models_new

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sarsenov Yerlan on 17.02.2021.
 */

@Entity(tableName = "recipes_saved")
data class RecipesEntity(
    @PrimaryKey
    val id: Int,
    val image: String?,
    val title: String?
    )