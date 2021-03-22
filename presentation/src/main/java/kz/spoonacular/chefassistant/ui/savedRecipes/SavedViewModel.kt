package kz.spoonacular.chefassistant.ui.savedRecipes

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.usecase.SavedRecipesUseCase

/**
 * Created by Sarsenov Yerlan on 13.02.2021.
 */
class SavedViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: SavedRecipesUseCase
): ViewModel() {

    private val _liveData = MutableLiveData<Either<List<Recipe>>>()
    val liveData: LiveData<Either<List<Recipe>>>
        get() = _liveData

    private val _liveDataLoadingState = MutableLiveData<LoadingState>()
    val liveDataLoadingState: LiveData<LoadingState>
        get() = _liveDataLoadingState

    init {
        getSavedRecipes()
    }

    fun getSavedRecipes() {
        viewModelScope.launch {
            _liveDataLoadingState.value = LoadingState.ShowLoading
            val request = useCase.getSavedRecipes()
            _liveData.value = Either.Success(request)
            _liveDataLoadingState.value = LoadingState.HideLoading
        }
    }

}