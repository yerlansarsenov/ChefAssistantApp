package kz.spoonacular.chefassistant.model

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
sealed class LoadingState {
    object ShowLoading: LoadingState()
    object HideLoading: LoadingState()
}
