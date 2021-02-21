package kz.spoonacular.data.mapper.db_mapper

import kz.spoonacular.data.model.db_models.RecipesEntity
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.recipes.Recipe


/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class RecipeMapperDB : Mapper<RecipesEntity, Recipe> {
    override fun map(model: RecipesEntity): Recipe {
        return Recipe(
            id = model.id,
            image = model.image,
            title = model.title
        )
    }
}