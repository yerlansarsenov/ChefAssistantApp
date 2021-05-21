package kz.spoonacular.chefassistant.ui.savedRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.showToast
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.chefassistant.ui.adapter.RecipesAdapter
import kz.spoonacular.chefassistant.ui.common.BaseFragment
import kz.spoonacular.chefassistant.ui.searchRecipes.FilterDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */

class SavedFragment: BaseFragment() {

    private val viewModel: SavedViewModel by viewModel()

    private val recipesAdapter: RecipesAdapter by lazy {
        RecipesAdapter(::openRecipeDetail)
    }

    private var recyclerView: RecyclerView? = null

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
        recyclerView?.adapter = recipesAdapter
        recyclerView?.layoutManager = GridLayoutManager(activity, 2)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false // FIXME: 14.04.2021 correct swiperefreshing
        }
        val filterImageView = view.findViewById<ImageView>(R.id.filter_image_view_saved)
        filterImageView.setOnClickListener {
            val dialog = FilterDialogFragment.instance(
                types = viewModel.getTypes(),
                cuisines =  viewModel.getCuisines()
            )
            dialog.setListener { types, cuisines ->
                onFilteredListener(types, cuisines)
            }
            dialog.show(parentFragmentManager, FILTER_DIALOG)
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
            if (list.isEmpty()) {
                showError("Nothing found :(")
            } else {
                hideError()
                recipesAdapter.submitList(list)
            }
        }
    }

    override fun onFilteredListener(types: List<String>, cuisines: List<String>) {
        viewModel.setTypes(types)
        viewModel.setCuisines(cuisines)
    }

    override fun onDestroyView() {
        recyclerView?.apply {
            adapter = null
            layoutManager = null
        }
        recyclerView = null
        super.onDestroyView()
    }

}
