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
    private val useCase: RecipeFromIngredientsUseCase,
    private val ingsUseCase: IngredientsSearchUseCase
) : ViewModel() {

    companion object {
        private const val INGREDIENTS_KEY = "INGREDIENTS_KEY"
        private const val UPDATE_KEY = "UPDATE_KEY"
    }

    fun getIngredients(): List<String> = savedStateHandle[INGREDIENTS_KEY] ?: emptyList()

    val ingredientsLiveData: LiveData<List<String>> = savedStateHandle.getLiveData(INGREDIENTS_KEY)

    private fun isUpdated(): Boolean = savedStateHandle[UPDATE_KEY] ?: false

    fun setIngredients(ingredients: List<String>) {
        savedStateHandle[INGREDIENTS_KEY] = ingredients
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

    private val _liveDataShowToast = MutableLiveData<Either<IngredientsResponse>>()
    val liveDataShowToast: LiveData<Either<IngredientsResponse>>
        get() = _liveDataShowToast

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
            _liveDataLoadingState.value = LoadingState.HideLoading
        }
    }

    fun searchIngredients(name: String) {
        viewModelScope.launch {
            when (val request = ingsUseCase.getIngredientsBySearch(name)) {
                is Either.Success -> {
                    _liveDataShowToast.value = Either.Success(request.response)
                }
                is Either.Error -> {
                    _liveDataShowToast.value = Either.Error(request.error)
                }
            }
        }
    }

}