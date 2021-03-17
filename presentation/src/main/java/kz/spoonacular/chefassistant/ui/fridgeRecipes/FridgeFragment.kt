package kz.spoonacular.chefassistant.ui.fridgeRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.showToast
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.chefassistant.ui.adapter.RecipesAdapter
import kz.spoonacular.chefassistant.ui.adapter.RecipesByIngrAdapter
import kz.spoonacular.chefassistant.ui.common.BaseFragment
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
class FridgeFragment: BaseFragment() {

    private val viewModel: FridgeViewModel by viewModel()

    private val recipesAdapter: RecipesByIngrAdapter by lazy {
        RecipesByIngrAdapter()
    }

    private lateinit var recyclerView: RecyclerView

    private lateinit var chipGroup: ChipGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_fridge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recipes_recycler_view_fridge)
        chipGroup = view.findViewById(R.id.chip_group_fridge)
        viewModel.ingredientsLiveData.observe(viewLifecycleOwner) { list ->
            chipGroup.removeAllViews()
            list.forEach {
                val chip = Chip(context)
                chip.apply {
                    text = it
                    isCloseIconVisible = true
                    isCheckable = false
                    isClickable = false
                    setOnCloseIconClickListener {
                        chipGroup.removeView(chip)
                    }
                }
                chipGroup.addView(chip)
            }
        }
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout_fridge)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.searchRecipes()
        }
        val ingredientsImageView = view.findViewById<ImageView>(R.id.ingredients_image_view_fridge)
        ingredientsImageView.setOnClickListener {
            // todo Show IngredientsDialogFragment
            viewModel.searchIngredients("apple")
            val ings = listOf("apple")
            viewModel.setIngredients(ings)
        }
        viewModel.liveDataShowToast.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Either.Success -> {
                    showToast("Success! ${state.response.number}")
                }
                is Either.Error -> {
                    showToast(state.error)
                }
            }
        }
        viewModel.liveDataLoadingState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadingState.ShowLoading -> {
                    showLoading()
                }
                is LoadingState.HideLoading -> {
                    swipeRefreshLayout.isRefreshing = false
                    hideLoading()
                }
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Either.Success -> {
                    hideError()
                    recipesAdapter.submitList(state.response)
                }
                is Either.Error -> {
                    showError(state.error)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        recyclerView.apply {
            adapter = null
            layoutManager = null
        }
    }

}