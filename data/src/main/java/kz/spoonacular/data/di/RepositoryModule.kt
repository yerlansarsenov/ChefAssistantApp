package kz.spoonacular.data.di

import kz.spoonacular.data.repository.IngredientsRepositoryImpl
import kz.spoonacular.data.repository.RecipeRepositoryImpl
import kz.spoonacular.data.repository.SavedRecipesRepositoryImpl
import kz.spoonacular.domain.repository.IngredientsRepository
import kz.spoonacular.domain.repository.RecipeRepository
import kz.spoonacular.domain.repository.SavedRecipesRepository
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
            recipeByIngrMapper = get(),
            recipeAutocompleteMapper = get()
        )
    }

    single<SavedRecipesRepository> {
        SavedRecipesRepositoryImpl(
            dao = get()
        )
    }

    single<IngredientsRepository> {
        IngredientsRepositoryImpl(
            api = get(),
            mapper = get()
        )
    }
}