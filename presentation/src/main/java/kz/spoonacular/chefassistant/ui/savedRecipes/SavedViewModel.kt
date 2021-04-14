package kz.spoonacular.chefassistant.ui.savedRecipes

import androidx.lifecycle.*
import kz.spoonacular.chefassistant.model.LoadingState
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
    }

    fun setCuisines(cuisines: List<String>) {
        savedStateHandle[CUISINES_KEY] = cuisines
    }

    init {
        setTypes(emptyList())
        setCuisines(emptyList())
    }

    fun getTypes(): List<String> = savedStateHandle[TYPES_KEY] ?: emptyList()

    val typesLiveData: LiveData<List<String>> =
        savedStateHandle.getLiveData(TYPES_KEY)

    fun getCuisines(): List<String> = savedStateHandle[CUISINES_KEY] ?: emptyList()

    val cuisinesLiveData: LiveData<List<String>> =
        savedStateHandle.getLiveData(CUISINES_KEY)

    private val _liveData =
        typesLiveData.switchMap { types ->
            cuisinesLiveData.switchMap { cuisines ->
                useCase.getSavedRecipesFlow(types, cuisines).asLiveData()
            }
        }
    val liveData: LiveData<List<Recipe>>
        get() = _liveData

    private val _liveDataLoadingState = MutableLiveData<LoadingState>()
    val liveDataLoadingState: LiveData<LoadingState>
        get() = _liveDataLoadingState

}