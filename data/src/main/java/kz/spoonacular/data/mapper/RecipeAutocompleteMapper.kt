package kz.spoonacular.data.mapper

import kz.spoonacular.data.model.recipes.RecipeAutocomplete
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.recipes.Recipe

class RecipeAutocompleteMapper: Mapper<RecipeAutocomplete, Recipe> {
    override fun map(model: RecipeAutocomplete): Recipe {
        return Recipe(
            id = model.id,
            image = "",
            title = model.title
        )
    }
}