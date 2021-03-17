package kz.spoonacular.data.mapper

import kz.spoonacular.data.model.recipeByIngredients.ingredients.IngredientItemData
import kz.spoonacular.data.model.recipeByIngredients.ingredients.IngredientsResponseData
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientItem
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientsResponse

class IngredientsResponseMapper: Mapper<IngredientsResponseData, IngredientsResponse> {
    override fun map(model: IngredientsResponseData): IngredientsResponse {
        val mapper = IngredientItemMapper()
        return IngredientsResponse(
            number = model.number ?: 0,
            offset = model.offset,
            results = model.results.map { mapper.map(it) },
            totalResults = model.totalResults
        )
    }
}

class IngredientItemMapper: Mapper<IngredientItemData, IngredientItem> {
    override fun map(model: IngredientItemData): IngredientItem {
        return IngredientItem(
            id = model.id,
            name = model.name,
            image = model.image
        )
    }

}