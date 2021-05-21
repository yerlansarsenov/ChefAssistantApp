package kz.spoonacular.data.mapper.db_mapper

import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.model.recipes.Recipe

class RecipeDetailedToRecipeMapperDB : Mapper<RecipeDetailed, Recipe>{
    override fun map(model: RecipeDetailed): Recipe {
        return Recipe(
            id = model.id,
            title = model.title,
            image = model.image
        )
    }
}
