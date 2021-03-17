package kz.spoonacular.chefassistant.di

import kz.spoonacular.chefassistant.ui.detailActivity.DetailViewModel
import kz.spoonacular.chefassistant.ui.fridgeRecipes.FridgeViewModel
import kz.spoonacular.chefassistant.ui.savedRecipes.SavedViewModel
import kz.spoonacular.chefassistant.ui.searchRecipes.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by Sarsenov Yerlan on 31.01.2021.
 */

val viewModelModule = module {
    viewModel {
        SearchViewModel(
            savedStateHandle = get(),
            searchUseCase = get()
        )
    }

    viewModel {
        FridgeViewModel(
            savedStateHandle = get(),
            useCase = get(),
            ingsUseCase = get()
        )
    }

    viewModel {
        SavedViewModel(
            savedStateHandle = get(),
            useCase = get()
        )
    }

    viewModel {
        DetailViewModel(
            savedStateHandle = get(),
            useCase = get(),
            saveUseCase = get()
        )
    }

    viewModel {
        SavedViewModel(
            savedStateHandle = get(),
            useCase = get()
        )
    }
}