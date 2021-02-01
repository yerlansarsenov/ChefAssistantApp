package kz.spoonacular.data.mapper

import kz.spoonacular.data.model.recipeByIngredients.IngredientDetailedData
import kz.spoonacular.data.model.recipeByIngredients.RecipeByIngredientsData
import kz.spoonacular.domain.mapper.Mapper
import kz.spoonacular.domain.model.recipeByIngredients.IngredientDetailed
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */
class RecipeByIngrMapper: Mapper<RecipeByIngredientsData, RecipeByIngredients> {
    override fun map(model: RecipeByIngredientsData): RecipeByIngredients {
        val ingredientsDetailedMapper = IngredientsDetailedMapper()
        return RecipeByIngredients(
            id = model.id,
            image = model.image,
            likes = model.likes,
            missedIngredientCount = model.missedIngredientCount,
            missedIngredients = model.missedIngredients.map { ingredientsDetailedMapper.map(it) },
            title = model.title,
            unusedIngredients = model.unusedIngredients.map { ingredientsDetailedMapper.map(it) },
            usedIngredientCount = model.usedIngredientCount,
            usedIngredients = model.usedIngredients.map { ingredientsDetailedMapper.map(it) }
        )
    }

    inner class IngredientsDetailedMapper: Mapper<IngredientDetailedData, IngredientDetailed> {
        override fun map(model: IngredientDetailedData): IngredientDetailed {
            return IngredientDetailed(
                aisle = model.aisle,
                amount = model.amount,
                extendedName = model.extendedName,
                id = model.id,
                image = model.image,
                meta = model.meta,
                metaInformation = model.metaInformation,
                name = model.name,
                original = model.original,
                originalName = model.originalName,
                originalString = model.originalString,
                unit = model.unit,
                unitLong = model.unitLong,
                unitShort = model.unitShort
            )
        }
    }
}