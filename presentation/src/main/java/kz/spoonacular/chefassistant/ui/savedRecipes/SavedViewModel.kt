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

    companion object {
        private const val TYPES_KEY = "TYPES_KEY"
        private const val CUISINES_KEY = "CUISINES_KEY"
        private const val UPDATE_KEY = "UPDATE_KEY"
    }

    fun setTypes(types: List<String>) {
        savedStateHandle[TYPES_KEY] = types
        setUpdated(false)
    }

    fun setCuisines(cuisines: List<String>) {
        savedStateHandle[CUISINES_KEY] = cuisines
        setUpdated(false)
    }

    private fun setUpdated(value: Boolean) {
        savedStateHandle[UPDATE_KEY] = value
    }

    fun getTypes(): List<String> = savedStateHandle[TYPES_KEY] ?: emptyList()

    fun getCuisines(): List<String> = savedStateHandle[CUISINES_KEY] ?: emptyList()

    private fun isUpdated(): Boolean = savedStateHandle[UPDATE_KEY] ?: false

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
        if (isUpdated()) {
            viewModelScope.launch {
                _liveDataLoadingState.value = LoadingState.HideLoading
            }
            return
        }
        viewModelScope.launch {
            _liveDataLoadingState.value = LoadingState.ShowLoading
            val request = useCase.getSavedRecipes(getTypes(), getCuisines())
            if (request.isNullOrEmpty()) {
                setUpdated(false)
                _liveData.value = Either.Error("Nothing found :(")
            } else {
                setUpdated(true)
                _liveData.value = Either.Success(request)
            }
            _liveDataLoadingState.value = LoadingState.HideLoading
        }
    }

}