package kz.spoonacular.chefassistant.ui.savedRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.intentFor
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.chefassistant.ui.adapter.RecipesAdapter
import kz.spoonacular.chefassistant.ui.common.BaseFragment
import kz.spoonacular.chefassistant.ui.detailActivity.DetailActivity
import kz.spoonacular.chefassistant.ui.detailActivity.RECIPE_ID_KEY
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
class SavedFragment: BaseFragment() {

    private val viewModel: SavedViewModel by viewModel()

    private val recipesAdapter: RecipesAdapter by lazy {
        RecipesAdapter(::openRecipeDetail)
    }

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorLayout = view.findViewById(R.id.error_layout)
        recyclerView = view.findViewById(R.id.recipes_saved_recycler_view)
        recyclerView.adapter = recipesAdapter
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.getSavedRecipes()
        }
        viewModel.liveDataLoadingState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LoadingState.ShowLoading -> {
                    showLoading()
                }
                LoadingState.HideLoading -> {
                    hideLoading()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) { list ->
            when (list) {
                is Either.Success -> {
                    hideError()
                    recipesAdapter.submitList(list.response)
                }
                is Either.Error -> {
                    showError(list.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        recyclerView.apply {
            adapter = null
            layoutManager = null
        }
        super.onDestroyView()
    }

    private fun openRecipeDetail(id: Int) {
        val intent = intentFor<DetailActivity>(
            RECIPE_ID_KEY to id
        )
        startActivity(intent)
    }

}