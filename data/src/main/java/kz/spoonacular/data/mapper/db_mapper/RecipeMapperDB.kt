package kz.spoonacular.data.mapper.db_mapper

import kz.spoonacular.data.model.db_models_new.RecipesEntity
import kz.spoonacular.domain.mapper.DoubleMapper
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.recipes.Recipe


/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class RecipeMapperDB : DoubleMapper<RecipesEntity, Recipe> {
    override fun mapTo(model: RecipesEntity): Recipe {
        return Recipe(
            id = model.id,
            image = model.image ?: "",
            title = model.title ?: ""
        )
    }

    override fun mapFrom(model: Recipe): RecipesEntity {
        return RecipesEntity(
            id = model.id,
            image = model.image,
            title = model.title
        )
    }
}