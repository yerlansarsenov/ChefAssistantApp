package kz.spoonacular.data.di

import kz.spoonacular.data.repository.RecipeRepositoryImpl
import kz.spoonacular.domain.repository.RecipeRepository
import org.koin.dsl.module

/**
 * Created by Sarsenov Yerlan on 30.01.2021.
 */

val repositoryModule = module {
    single<RecipeRepository> {
        RecipeRepositoryImpl(
            api = get(),
            recipeDetailedMapper = get(),
            recipeMapper = get(),
            recipeByIngrMapper = get()
        )
    }
}