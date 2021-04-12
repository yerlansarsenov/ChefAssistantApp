package kz.spoonacular.chefassistant.ui.common

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.intentFor
import kz.spoonacular.chefassistant.extensions.showProcessLoading
import kz.spoonacular.chefassistant.ui.detailActivity.DetailActivity
import kz.spoonacular.chefassistant.ui.detailActivity.RECIPE_ID_KEY

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
abstract class BaseFragment: Fragment() {

    companion object {
        const val FILTER_DIALOG = "FILTER_DIALOG"
    }

    var progressBar: ContentLoadingProgressBar? = null

    var errorLayout: LinearLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorLayout = view.findViewById(R.id.error_layout)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onDestroyView() {
        progressBar = null
        errorLayout = null
        super.onDestroyView()
    }

    protected fun showLoading() {
        progressBar?.visibility = View.VISIBLE
        hideError()
    }

    protected fun hideLoading() {
        progressBar?.visibility = View.GONE
    }

    protected fun showError(error: String) {
        errorLayout?.visibility = View.VISIBLE
        val textView = errorLayout?.findViewById<TextView>(R.id.error_textview)
        textView?.text = error
    }

    protected fun hideError() {
        errorLayout?.visibility = View.GONE
    }

    protected fun openRecipeDetail(id: Int) {
        val intent = intentFor<DetailActivity>(
            RECIPE_ID_KEY to id
        )
        startActivity(intent)
    }

    abstract fun onFilteredListener(types: List<String>, cuisines: List<String>)

}