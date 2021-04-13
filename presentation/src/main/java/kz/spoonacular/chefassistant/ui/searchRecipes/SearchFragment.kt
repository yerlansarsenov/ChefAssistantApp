package kz.spoonacular.chefassistant.ui.searchRecipes

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.intentFor
import kz.spoonacular.chefassistant.extensions.showToast
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.chefassistant.ui.MainActivity
import kz.spoonacular.chefassistant.ui.adapter.RecipesAdapter
import kz.spoonacular.chefassistant.ui.adapter.RecipesSearchableAdapter
import kz.spoonacular.chefassistant.ui.common.BaseFragment
import kz.spoonacular.chefassistant.ui.detailActivity.DetailActivity
import kz.spoonacular.chefassistant.ui.detailActivity.RECIPE_ID_KEY
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 01.02.2021.
 */

// todo https://habr.com/ru/company/badoo/blog/429728/

private const val TAG = "SearchFragment"

class SearchFragment: BaseFragment() {

    private val viewModel: SearchViewModel by viewModel()

    private val recipesAdapter: RecipesAdapter by lazy {
        RecipesAdapter(::openRecipeDetail)
    }

    private val suggestionsAdapter: RecipesSearchableAdapter by lazy {
        RecipesSearchableAdapter(::setQueryAndDismiss)
    }

    private var recyclerView: RecyclerView? = null

    private var recyclerViewSuggestions: RecyclerView? = null

    private var suggestionsLinearLayout: LinearLayout? = null

    private var searchView: SearchView? = null

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

        recyclerView = view.findViewById(R.id.recipes_search_recycler_view)
        recyclerView?.adapter = recipesAdapter
        recyclerView?.layoutManager = GridLayoutManager(activity, 2)

        recyclerViewSuggestions = view.findViewById(R.id.search_suggestions_rec_view)
        recyclerViewSuggestions?.adapter = suggestionsAdapter
        recyclerViewSuggestions?.layoutManager = LinearLayoutManager(activity)
        recyclerViewSuggestions?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

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

        suggestionsLinearLayout = view.findViewById(R.id.search_suggestions_linear_layout)
        searchView = view.findViewById(R.id.searchview_search_frag)
        searchView?.setOnSearchClickListener {
            suggestionsLinearLayout?.visibility = View.VISIBLE
        }
        searchView?.setOnCloseListener {
            viewModel.apply {
                setQuery("")
                searchRecipes()
            }
            suggestionsLinearLayout?.visibility = View.GONE
            false
        }
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    setQueryAndDismiss(query)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.isNotEmpty()) {
                    if (suggestionsLinearLayout?.visibility == View.GONE)
                        return false
                    viewModel.getAutocompleteRecipes(newText)
                }
                return false
            }
        })

        viewModel.liveDataLoadingState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LoadingState.ShowLoading -> {
                    showLoading()
                }
                is LoadingState.HideLoading -> {
                    hideLoading()
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
        viewModel.liveData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Either.Success -> {
                    hideError()
                    recipesAdapter.submitList(data.response)
                }
                is Either.Error -> {
                    showError(data.error)
                }
            }
        }
        viewModel.liveDataSuggestions.observe(viewLifecycleOwner) { data ->
            when (data) {
                is Either.Success -> {
                    suggestionsAdapter.submitList(data.response)
                }
                is Either.Error -> {
                    showToast(data.error)
                    Log.e(TAG, "onViewCreated: ${data.error}")
                }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
//
//    }

    override fun onFilteredListener(types: List<String>, cuisines: List<String>) {
        viewModel.setTypes(types)
        viewModel.setCuisines(cuisines)
        viewModel.searchRecipes()
    }

    private fun setQueryAndDismiss(query: String) {
        viewModel.setQuery(query)
        viewModel.searchRecipes()
        suggestionsLinearLayout?.visibility = View.GONE
        searchView?.setQuery(query, false)
        searchView?.clearFocus()
    }

    override fun onDestroyView() {
        recyclerView?.apply {
            adapter = null
            layoutManager = null
        }
        recyclerView = null
        recyclerViewSuggestions?.apply {
            adapter = null
            layoutManager = null
        }
        recyclerViewSuggestions = null
        suggestionsLinearLayout = null
        searchView = null
        super.onDestroyView()
    }

}