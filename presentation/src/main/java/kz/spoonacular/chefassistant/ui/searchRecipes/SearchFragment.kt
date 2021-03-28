package kz.spoonacular.chefassistant.ui.searchRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
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
 * Created by Sarsenov Yerlan on 01.02.2021.
 */

private const val TAG = "SearchFragment"

private const val FILTER_DIALOG = "FILTER_DIALOG"

class SearchFragment: BaseFragment() {

    private val viewModel: SearchViewModel by viewModel()

    private val recipesAdapter: RecipesAdapter by lazy {
        RecipesAdapter(::openRecipeDetail)
    }

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorLayout = view.findViewById(R.id.error_layout)
        recyclerView = view.findViewById<RecyclerView>(R.id.recipes_search_recycler_view)
        recyclerView?.adapter = recipesAdapter
        recyclerView?.layoutManager = GridLayoutManager(activity, 2)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.searchRecipes()
        }
        val filterImageView = view.findViewById<ImageView>(R.id.filter_image_view)
        filterImageView.setOnClickListener {
            val dialog = FilterDialogFragment.instance(viewModel.getTypes(), viewModel.getCuisines())
            dialog.setListener { types, cuisines ->
                onFilteredListener(types, cuisines)
            }
            dialog.show(parentFragmentManager, FILTER_DIALOG)
        }
        val searchView = view.findViewById<SearchView>(R.id.searchview_search_frag)
        searchView.setOnCloseListener {
            viewModel.apply {
                setQuery("")
                searchRecipes()
            }
            false
        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.setQuery(query)
                    viewModel.searchRecipes()
                    searchView.clearFocus()
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO create suggestions
                return false
            }
        })
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

    private fun onFilteredListener(types: List<String>, cuisines: List<String>) {
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
        super.onDestroyView()
    }

    private fun openRecipeDetail(id: Int) {
        val intent = intentFor<DetailActivity>(
            RECIPE_ID_KEY to id
        )
        startActivity(intent)
    }

}