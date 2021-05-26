package kz.spoonacular.chefassistant.ui.detailActivity

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.*
import kz.spoonacular.chefassistant.model.LoadingState
import kz.spoonacular.chefassistant.ui.detailActivity.adapters.AnalyzedInstructionsAdapter
import kz.spoonacular.chefassistant.ui.detailActivity.adapters.ExtendedIngredientsAdapter
import kz.spoonacular.domain.model.Either
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Sarsenov Yerlan on 14.02.2021.
 */

const val RECIPE_ID_KEY = "RECIPE_ID_KEY"

class DetailActivity : AppCompatActivity() {

    private val ingredientsAdapter = ExtendedIngredientsAdapter()

    private val analyzedInstructionsAdapter = AnalyzedInstructionsAdapter(this)

    private val viewModel: DetailViewModel by viewModel()

    private val recipeId: Int by lazyArg(RECIPE_ID_KEY)

    private val progressBar: ContentLoadingProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }
    private val errorLayout: LinearLayout by lazy {
        findViewById(R.id.error_layout)
    }
    private val toolbarTitle: Toolbar by lazy {
        findViewById(R.id.details_toolbar)
    }
    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.details_ingredients_recycler)
    }
    private val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        findViewById(R.id.details_swipe_refresh_layout)
    }
    private val detailsTimeText: AppCompatTextView by lazy {
        findViewById(R.id.details_time_text)
    }
    private val detailsHealthText: AppCompatTextView by lazy {
        findViewById(R.id.details_health_text)
    }
    private val detailsScoreText: AppCompatTextView by lazy {
        findViewById(R.id.details_score_text)
    }
    private val addToSavedButton: ExtendedFloatingActionButton by lazy {
        findViewById(R.id.details_add_to_saved)
    }
    private val posterImageView: AppCompatImageView by lazy {
        findViewById(R.id.details_poster)
    }
    private val nestedScroller: NestedScrollView by lazy {
        findViewById(R.id.main_nested_detail)
    }
    private val appBarLayout: AppBarLayout by lazy {
        findViewById(R.id.app_bar_detail_layout)
    }
    private val instructionsTextView: AppCompatTextView by lazy {
        findViewById(R.id.details_instructions_text)
    }
    private val analyzedRecyclerView: RecyclerView by lazy {
        findViewById(R.id.details_analyzed_recycler)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        viewModel.searchRecipeById(recipeId)
        recyclerView.adapter = ingredientsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )
        analyzedRecyclerView.adapter = analyzedInstructionsAdapter
        analyzedRecyclerView.layoutManager = LinearLayoutManager(this)
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.searchRecipeById(recipeId)
        }
        // todo: Ne rabotaet
//        nestedScroller.setOnScrollChangeListener(object : NestedScrollView.OnScrollChangeListener
//        {
//            override fun onScrollChange(
//                v: NestedScrollView?,
//                scrollX: Int,
//                scrollY: Int,
//                oldScrollX: Int,
//                oldScrollY: Int
//            ) {
//                if (scrollY > oldScrollY + 12 && addToSavedButton.isExtended)
//                    addToSavedButton.shrink()
//                if (scrollY < oldScrollY - 12 && !addToSavedButton.isExtended)
//                    addToSavedButton.extend()
//                if (scrollY == 0)
//                    addToSavedButton.extend()
//            }
//        })
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
            if (state == null)
                return@observe
            when (val first = state.first) {
                is Either.Success -> {
                    hideError()
                    first.response.apply {
                        if (!state.second) {
                            addToSavedButton.text = getString(R.string.add_to_my_recipes)
                            addToSavedButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    this@DetailActivity, R.color.colorPrimary
                                )
                            )
                            addToSavedButton.setOnClickListener { view ->
                                viewModel.insertRecipe(this)
                                addToSavedButton.text = getString(R.string.remove_from_my_recipes)
                                addToSavedButton.setBackgroundColor(
                                    ContextCompat.getColor(view.context, R.color.gray)
                                )
                            }
                        } else {
                            addToSavedButton.text = getString(R.string.remove_from_my_recipes)
                            addToSavedButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    this@DetailActivity, R.color.gray
                                )
                            )
                            addToSavedButton.setOnClickListener { view ->
                                viewModel.deleteRecipe(this)
                                addToSavedButton.text = getString(R.string.add_to_my_recipes)
                                addToSavedButton.setBackgroundColor(
                                    ContextCompat.getColor(
                                        view.context, R.color.colorPrimary
                                    )
                                )
                            }
                        }
                        toolbarTitle.title = title
                        if (this.image.isNotEmpty())
                            posterImageView.setImageWithUrl(image)
                        ingredientsAdapter.submitList(extendedIngredients)
                        analyzedInstructionsAdapter.submitList(analyzedInstructions)
                        detailsTimeText.text = getMinutes()
                        detailsHealthText.text = healthScore.toString()
                        detailsScoreText.text = spoonacularScore.toString()
                        instructionsTextView.text = instructions
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
        progressBar.showMe()
    }

    private fun hideLoading() {
        progressBar.hideMe()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showError(error: String) {
        errorLayout.showMe()
        val textView = errorLayout.findViewById<TextView>(R.id.error_textview)
        textView.text = error
        appBarLayout.setExpanded(false)
    }

    private fun hideError() {
        errorLayout.hideMe()
        appBarLayout.setExpanded(true)
    }

    override fun onDestroy() {
        recyclerView.apply {
            adapter = null
            layoutManager = null
        }
        analyzedRecyclerView.apply {
            adapter = null
            layoutManager = null
        }
        super.onDestroy()
    }

}
