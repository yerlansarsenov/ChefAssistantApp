package kz.spoonacular.chefassistant.ui.fridgeRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.intentFor
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.chefassistant.ui.adapter.RecipesByIngrAdapter
import kz.spoonacular.chefassistant.ui.common.BaseFragment
import kz.spoonacular.chefassistant.ui.detailActivity.DetailActivity
import kz.spoonacular.chefassistant.ui.detailActivity.RECIPE_ID_KEY
import kz.spoonacular.chefassistant.ui.fridgeRecipes.ingredients.IngredientsDialogFragment
import kz.spoonacular.chefassistant.ui.searchRecipes.FilterDialogFragment
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val SEARCH_INGREDIENTS_DIALOG = "SEARCH_INGREDIENTS_DIALOG"

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
class FridgeFragment: BaseFragment() {

    private val viewModel: FridgeViewModel by viewModel()

    private val recipesAdapter: RecipesByIngrAdapter by lazy {
        RecipesByIngrAdapter(::openRecipeDetail)
    }

    private var recyclerView: RecyclerView? = null

    private var chipGroup: ChipGroup? = null

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
            chipGroup?.removeAllViews()
            list.forEach { item ->
                val chip = Chip(context)
                chip.apply {
                    text = item
                    isCloseIconVisible = true
                    isCheckable = false
                    isClickable = false
                    setOnCloseIconClickListener {
                        viewModel.removeIngredient(item)
                        viewModel.searchRecipes()
                        chipGroup?.removeView(chip)
                    }
                }
                chipGroup?.addView(chip)
            }
        }
        recyclerView?.adapter = recipesAdapter
        recyclerView?.layoutManager = LinearLayoutManager(activity)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout_fridge)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.searchRecipes()
        }
        val ingredientsImageView = view.findViewById<ImageView>(R.id.ingredients_image_view_fridge)
        ingredientsImageView.setOnClickListener {
            // todo Show IngredientsDialogFragment
            val dialog = IngredientsDialogFragment()
            dialog.setListener { name ->
                val ings = listOf(name)
                viewModel.setIngredients(ings)
                viewModel.searchRecipes()
                dialog.dismiss()
            }
            dialog.show(parentFragmentManager, SEARCH_INGREDIENTS_DIALOG)
        }
        val filterImageView = view.findViewById<ImageView>(R.id.filter_image_view_fridge)
        filterImageView.setOnClickListener {
            val dialog = FilterDialogFragment.instance(viewModel.getTypes(), viewModel.getCuisines())
            dialog.setListener { types, cuisines ->
                onFilteredListener(types, cuisines)
            }
            dialog.show(parentFragmentManager, FILTER_DIALOG)
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

    override fun onFilteredListener(types: List<String>, cuisines: List<String>) {
        viewModel.setTypes(types)
        viewModel.setCuisines(cuisines)
        viewModel.searchRecipes()
    }

    override fun onDestroyView() {
        recyclerView?.apply {
            adapter = null
            layoutManager = null
        }
        recyclerView = null
        chipGroup?.removeAllViews()
        chipGroup = null
        super.onDestroyView()
    }

}
