package kz.spoonacular.data.mapper

import kz.spoonacular.data.model.recipes.RecipeData
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.recipes.Recipe

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class RecipeMapper : Mapper<RecipeData, Recipe> {
    override fun map(model: RecipeData): Recipe {
        return Recipe(
            id = model.id,
            image = model.image,
            title = model.title
        )
    }
}
