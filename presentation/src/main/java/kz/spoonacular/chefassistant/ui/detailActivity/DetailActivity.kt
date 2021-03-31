package kz.spoonacular.chefassistant.ui.detailActivity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.lazyArg
import kz.spoonacular.chefassistant.extensions.setImageWithUrl
import kz.spoonacular.chefassistant.extensions.showToast
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.domain.model.Either
import kz.spoonacular.domain.model.reciepeDetails.RecipeDetailed
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 14.02.2021.
 */

const val RECIPE_ID_KEY = "RECIPE_ID_KEY"

class DetailActivity : AppCompatActivity() {

    private val ingredientsAdapter = ExtendedIngredientsAdapter()

    private val viewModel: DetailViewModel by viewModel()

    private val recipeId: Int by lazyArg(RECIPE_ID_KEY)

    private val progressBar: ContentLoadingProgressBar by lazy { findViewById(R.id.progress_bar) }
    private val errorLayout: LinearLayout by lazy { findViewById(R.id.error_layout) }
    private val toolbarTitle: Toolbar by lazy { findViewById(R.id.details_toolbar) }
    private val recyclerView: RecyclerView by lazy { findViewById(R.id.details_ingredients_recycler) }
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy { findViewById(R.id.details_swipe_refresh_layout) }
    private val detailsTimeText: AppCompatTextView by lazy { findViewById(R.id.details_time_text) }
    private val detailsHealthText: AppCompatTextView by lazy { findViewById(R.id.details_health_text) }
    private val detailsScoreText: AppCompatTextView by lazy { findViewById(R.id.details_score_text) }
    private val addToSavedButton: MaterialButton by lazy { findViewById(R.id.details_add_to_saved) }
    private val posterImageView: AppCompatImageView by lazy { findViewById(R.id.details_poster) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        viewModel.searchRecipeById(recipeId)
        recyclerView.adapter = ingredientsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.searchRecipeById(recipeId)
        }
        viewModel.liveDataLoadingState.observe(this) { state ->
            when (state) {
                is LoadingState.ShowLoading -> {
                    showLoading()
                }
                is LoadingState.HideLoading -> {
                    hideLoading()
                }
            }
        }
        viewModel.liveDataMovie.observe(this) { state ->
            when (val first = state.first) {
                is Either.Success -> {
                    first.response.apply {
                        if (!state.second) {
                            addToSavedButton.visibility = View.VISIBLE
                            addToSavedButton.setOnClickListener {
                                viewModel.insertRecipe(this)
                                addToSavedButton.visibility = View.GONE
                            }
                        }
                        toolbarTitle.title = this.title
                        posterImageView.setImageWithUrl(this.image)
                        ingredientsAdapter.submitList(this.extendedIngredients)
                        ingredientsAdapter.notifyDataSetChanged()
                        detailsTimeText.text = "${this.cookingMinutes} min"
                        detailsHealthText.text = this.healthScore.toString()
                        detailsScoreText.text = this.spoonacularScore.toString()
                    }
                }
                is Either.Error -> {
                    showError(first.error)
                }
                else -> { }
            }
        }
        viewModel.liveDataShowToast.observe(this) { text ->
            if (text != null) {
                showToast(text)
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showError(error: String) {
        errorLayout.visibility = View.VISIBLE
        val textView = errorLayout.findViewById<TextView>(R.id.error_textview)
        textView.text = error
    }

    private fun hideError() {
        errorLayout.visibility = View.GONE
    }

    override fun onDestroy() {
        recyclerView.apply {
            adapter = null
            layoutManager = null
        }
        super.onDestroy()
    }

}