package kz.spoonacular.chefassistant.ui.common

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import kz.spoonacular.chefassistant.R
import kz.spoonacular.chefassistant.extensions.showProcessLoading

/**
 * Created by Sarsenov Yerlan on 02.02.2021.
 */
abstract class BaseFragment: Fragment() {

    var progressBar: ContentLoadingProgressBar? = null

    var errorLayout: LinearLayout? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorLayout = view.findViewById(R.id.error_layout)
        progressBar = view.findViewById(R.id.progress_bar)
    }

    override fun onDestroy() {
        progressBar?.let {
            return@let null
        }
        errorLayout?.let {
            return@let null
        }
        super.onDestroy()
    }

    protected fun showLoading() {
        progressBar?.visibility = View.VISIBLE
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

}