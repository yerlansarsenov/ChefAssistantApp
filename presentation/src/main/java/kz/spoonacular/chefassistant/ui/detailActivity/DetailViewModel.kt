package kz.spoonacular.chefassistant.ui.detailActivity

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import kz.spoonacular.domain.usecase.RecipeDetailedUseCase
import kz.spoonacular.domain.usecase.SavedRecipesUseCase

/**
 * Created by Sarsenov Yerlan on 14.02.2021.
 */
class DetailViewModel(
    val savedStateHandle: SavedStateHandle,
    val useCase: RecipeDetailedUseCase,
    private val saveUseCase: SavedRecipesUseCase
): ViewModel() {

    private val _livaDataMovie = MutableLiveData<Either<RecipeDetailed>>()
    val liveDataMovie: LiveData<Either<RecipeDetailed>>
        get() = _livaDataMovie

    private val _liveDataLoadingState = MutableLiveData<LoadingState>()
    val liveDataLoadingState: LiveData<LoadingState>
        get() = _liveDataLoadingState

    fun searchRecipeById(id: Int) {
        if (_livaDataMovie.value != null && _livaDataMovie.value !is Either.Error)
            return
        viewModelScope.launch {
            _liveDataLoadingState.value = LoadingState.ShowLoading
            val dbRequest = saveUseCase.getSavedRecipeById(id)
            if (dbRequest != null && dbRequest.title.isNotEmpty()) {
                _livaDataMovie.value = Either.Success(dbRequest)
            } else {
                when (val serverRequest = useCase.getRecipeById(id)) {
                    is Either.Success -> {
                        _livaDataMovie.value = Either.Success(serverRequest.response)
                    }
                    is Either.Error -> {
                        _livaDataMovie.value = Either.Error(serverRequest.error)
                    }
                }
            }
            _liveDataLoadingState.value = LoadingState.HideLoading
        }
    }

    fun insertRecipe(recipeDetailed: RecipeDetailed) {
        viewModelScope.launch {
            saveUseCase.insertRecipe(recipeDetailed)
        }
    }

}