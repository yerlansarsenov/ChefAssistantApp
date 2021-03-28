package kz.spoonacular.chefassistant.ui.fridgeRecipes.ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.recipeByIngredients.ingredient.IngredientsResponse
import kz.spoonacular.domain.usecase.IngredientsSearchUseCase

class IngredientsViewModel(
    private val ingsUseCase: IngredientsSearchUseCase
): ViewModel() {

    private val _liveData = MutableLiveData<Either<IngredientsResponse>>()
    val liveData: LiveData<Either<IngredientsResponse>>
        get() = _liveData

    fun searchIngredients(name: String) {
        viewModelScope.launch {
            when (val request = ingsUseCase.getIngredientsBySearch(name)) {
                is Either.Success -> {
                    _liveData.value = Either.Success(request.response)
                }
                is Either.Error -> {
                    _liveData.value = Either.Error(request.error)
                }
            }
        }
    }
}