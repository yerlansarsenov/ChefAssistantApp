package kz.spoonacular.chefassistant.ui.detailActivity

import android.util.Log
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

    private val _livaDataMovie = MutableLiveData<Pair<Either<RecipeDetailed>, Boolean>>()
    val liveDataMovie: LiveData<Pair<Either<RecipeDetailed>, Boolean>>
        get() = _livaDataMovie

    private val _liveDataLoadingState = MutableLiveData<LoadingState>()
    val liveDataLoadingState: LiveData<LoadingState>
        get() = _liveDataLoadingState

    private val _liveDataShowToast = MutableLiveData<String?>()
    val liveDataShowToast: LiveData<String?>
        get() = _liveDataShowToast

    fun searchRecipeById(id: Int) {
        if (_livaDataMovie.value != null && _livaDataMovie.value!!.first !is Either.Error) {
            _liveDataLoadingState.value = LoadingState.HideLoading
            return
        }
        viewModelScope.launch {
            _liveDataLoadingState.value = LoadingState.ShowLoading
            val dbRequest = saveUseCase.getSavedRecipeById(id)
            if (dbRequest != null && dbRequest.title.isNotEmpty()) {
                _livaDataMovie.value = Either.Success(dbRequest) to true
                Log.e(this.javaClass.simpleName, "searchRecipeById: DataBase")
            } else {
                when (val serverRequest = useCase.getRecipeById(id)) {
                    is Either.Success -> {
                        _livaDataMovie.value = Either.Success(serverRequest.response) to false
                        Log.e(this.javaClass.simpleName, "searchRecipeById: Network")
                    }
                    is Either.Error -> {
                        _livaDataMovie.value = Either.Error(serverRequest.error) to false
                    }
                }
            }
            _liveDataLoadingState.value = LoadingState.HideLoading
        }
    }

    fun insertRecipe(recipeDetailed: RecipeDetailed) {
        viewModelScope.launch {
            saveUseCase.insertRecipe(recipeDetailed)
            _liveDataShowToast.value = "Successfully added to local memory"
            _liveDataShowToast.value = null
            _livaDataMovie.value = null
            searchRecipeById(recipeDetailed.id)
        }
    }

}