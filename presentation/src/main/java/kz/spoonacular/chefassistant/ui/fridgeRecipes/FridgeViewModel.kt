package kz.spoonacular.chefassistant.ui.fridgeRecipes

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.recipeByIngredients.RecipeByIngredients
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientsResponse
import kz.spoonacular.domain.usecase.IngredientsSearchUseCase
import kz.spoonacular.domain.usecase.RecipeFromIngredientsUseCase

/**
 * Created by Sarsenov Yerlan on 05.02.2021.
 */
class FridgeViewModel(
    val savedStateHandle: SavedStateHandle,
    private val useCase: RecipeFromIngredientsUseCase
) : ViewModel() {

    companion object {
        private const val INGREDIENTS_KEY = "INGREDIENTS_KEY"
        private const val UPDATE_KEY = "UPDATE_KEY"
    }

    fun getIngredients(): MutableList<String> = savedStateHandle[INGREDIENTS_KEY] ?: mutableListOf()

    val ingredientsLiveData: LiveData<MutableList<String>> = savedStateHandle.getLiveData(INGREDIENTS_KEY)

    private fun isUpdated(): Boolean = savedStateHandle[UPDATE_KEY] ?: false

    fun setIngredients(ingredients: List<String>) {
        val actualList = getIngredients()
        ingredients.forEach { ingredient ->
            if (!actualList.contains(ingredient)) {
                actualList.add(ingredient)
            }
        }
        savedStateHandle[INGREDIENTS_KEY] = actualList
        setUpdated(false)
    }

    fun removeIngredient(ingredient: String) {
        val actualList = getIngredients()
        actualList.remove(ingredient)
        savedStateHandle[INGREDIENTS_KEY] = actualList
        setUpdated(false)
    }

    private fun setUpdated(value: Boolean) {
        savedStateHandle[UPDATE_KEY] = value
    }

    private val _liveData = MutableLiveData<Either<List<RecipeByIngredients>>>()
    val liveData: LiveData<Either<List<RecipeByIngredients>>>
        get() = _liveData

    private val _liveDataLoadingState = MutableLiveData<LoadingState>()
    val liveDataLoadingState: LiveData<LoadingState>
        get() = _liveDataLoadingState

    init {
        searchRecipes()
    }

    fun searchRecipes() {
        if (isUpdated()) {
            viewModelScope.launch {
                _liveDataLoadingState.value = LoadingState.HideLoading
            }
            return
        }
        viewModelScope.launch {
            _liveDataLoadingState.value = LoadingState.ShowLoading
            if (getIngredients().isEmpty()) {
                _liveData.value = Either.Error("Please, choose some ingredients")
            } else {
                when (val request = useCase.getRecipeFromIngredients(*getIngredients().toTypedArray())) {
                    is Either.Success -> {
                        setUpdated(true)
                        _liveData.value = Either.Success(request.response)
                    }
                    is Either.Error -> {
                        setUpdated(false)
                        _liveData.value = Either.Error(request.error)
                    }
                }
            }
            _liveDataLoadingState.value = LoadingState.HideLoading
        }
    }

}