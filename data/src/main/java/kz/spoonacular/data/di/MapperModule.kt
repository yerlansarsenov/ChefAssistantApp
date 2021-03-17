package kz.spoonacular.data.di

import kz.spoonacular.data.mapper.IngredientsResponseMapper
import kz.spoonacular.data.mapper.RecipeByIngrMapper
import kz.spoonacular.data.mapper.RecipeDetailedMapper
import kz.spoonacular.data.mapper.RecipeMapper
import org.koin.dsl.module

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */

val mapperModule = module {
    factory { RecipeDetailedMapper() }
    factory { RecipeMapper() }
    factory { RecipeByIngrMapper() }
    factory { IngredientsResponseMapper() }
}