package kz.spoonacular.chefassistant.ui.searchRecipes

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.recipes.Recipe
import kz.spoonacular.domain.usecase.SearchUseCase

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
class SearchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    companion object {
        private const val QUERY_KEY = "QUERY_KEY"
        private const val TYPES_KEY = "TYPES_KEY"
        private const val CUISINES_KEY = "CUISINES_KEY"
        private const val UPDATE_KEY = "UPDATE_KEY"
    }

    fun setQuery(text: String) {
        savedStateHandle[QUERY_KEY] = text
        setUpdated(false)
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

    fun getQuery(): String = savedStateHandle[QUERY_KEY] ?: ""

    fun getTypes(): List<String> = savedStateHandle[TYPES_KEY] ?: emptyList()

    fun getCuisines(): List<String> = savedStateHandle[CUISINES_KEY] ?: emptyList()

    private fun isUpdated(): Boolean = savedStateHandle[UPDATE_KEY] ?: false

    private val _liveData = MutableLiveData<Either<List<Recipe>>>()
    val liveData: LiveData<Either<List<Recipe>>>
        get() = _liveData

    private val _liveDataSuggestions = MutableLiveData<Either<List<Recipe>>>()
    val liveDataSuggestions: LiveData<Either<List<Recipe>>>
        get() = _liveDataSuggestions

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
            when (val request =
                searchUseCase.getRecipeByQueryCuisineAndType(getQuery(), getTypes(), getCuisines())) {
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

    fun getAutocompleteRecipes(query: String) {
//        viewModelScope.launch {
//            when(val request = searchUseCase.getRecipeAutocomplete(query = query)) {
//                // 500 server error
//                is Either.Success -> {
//                    _liveDataSuggestions.value = Either.Success(request.response)
//                }
//                is Either.Error -> {
//                    _liveDataSuggestions.value = Either.Error(request.error)
//                }
//            }
//        }
        viewModelScope.launch {
            when(val request = searchUseCase.getRecipeByQueryCuisineAndType(query = query, emptyList(), emptyList())) {
                // 500 server error
                is Either.Success -> {
                    _liveDataSuggestions.value = Either.Success(request.response)
                }
                is Either.Error -> {
                    _liveDataSuggestions.value = Either.Error(request.error)
                }
            }
        }
    }

}
