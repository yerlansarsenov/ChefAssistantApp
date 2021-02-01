package kz.spoonacular.chefassistant.di

import kz.spoonacular.domain.usecase.RecipeDetailedUseCase
import kz.spoonacular.domain.usecase.RecipeFromIngredientsUseCase
import kz.spoonacular.domain.usecase.SearchUseCase
import org.koin.dsl.module

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */

val useCaseModule = module {
    factory { SearchUseCase(get()) }
    factory { RecipeDetailedUseCase(get()) }
    factory { RecipeFromIngredientsUseCase(get()) }
}