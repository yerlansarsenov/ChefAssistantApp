package kz.spoonacular.chefassistant.ui.detailActivity

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ContentLoadingProgressBar
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.lazyArg
import kz.spoonacular.chefassistant.extensions.showProcessLoading
import kz.spoonacular.chefassistant.extensions.showToast
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 14.02.2021.
 */

const val RECIPE_ID_KEY = "RECIPE_ID_KEY"

class DetailActivity: AppCompatActivity() {

    private val recipeId: Int by lazyArg(RECIPE_ID_KEY)

    private val viewModel: DetailViewModel by viewModel()

    private val progressBar: ContentLoadingProgressBar by lazy { findViewById(R.id.progress_bar) }

    private val errorLayout: LinearLayout by lazy { findViewById(R.id.error_layout) }

    private val toolbarTitle: TextView by lazy { findViewById(R.id.toolbar_details_textview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        viewModel.searchRecipeById(recipeId)
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
            when (state) {
                is Either.Success -> {
                    state.response.apply {
                        // showToast(this.title)
                        // viewModel.insertRecipe(this)
                        toolbarTitle.text = this.title

                    }
                }
                is Either.Error -> {
                    showError(state.error)
                }
                else -> {}
            }
        }
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showError(error: String) {
        errorLayout.visibility = View.VISIBLE
        val textView = errorLayout.findViewById<TextView>(R.id.error_textview)
        textView.text = error
    }

    private fun hideError() {
        errorLayout.visibility = View.GONE
    }

}